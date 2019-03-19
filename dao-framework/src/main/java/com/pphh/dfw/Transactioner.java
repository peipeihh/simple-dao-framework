package com.pphh.dfw;

import com.pphh.dfw.core.exception.DfwException;
import com.pphh.dfw.core.function.DfwFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 3/13/2019
 */
public class Transactioner {

    private final static Logger log = LoggerFactory.getLogger(Transactioner.class);

    private static ThreadLocal<Boolean> trancOn = new ThreadLocal<>();
    private static ThreadLocal<AtomicInteger> trancDepth = new ThreadLocal<>();
    private static ThreadLocal<String> trancDbName = new ThreadLocal<>();
    private static ThreadLocal<Connection> connLocal = new ThreadLocal<>();

    private static Transactioner ourInstance = new Transactioner();

    private Transactioner() {
    }

    public static Transactioner getInstance() {
        return ourInstance;
    }

    public Boolean isTransactionOn() {
        Boolean trancOn = Transactioner.trancOn.get();
        return trancOn != null && trancOn;
    }

    public Connection initConnection(String dbName) throws Exception {
        log.info("try to initialize a connection for current transaction, dbName = " + dbName);
        if (dbName == null) {
            throw new DfwException("The db name could not be null.");
        }

        Connection connection = Transactioner.connLocal.get();
        if (connection == null) {
            Transactioner.trancDbName.set(dbName);
            connection = DataSourceManager.getInstance().getConnection(dbName);
            connection.setAutoCommit(false);
            Transactioner.connLocal.set(connection);
            log.info("the connection has been initialized for current transaction, dbName = " + dbName);
        } else {
            throw new DfwException("An existing db connection has been set ready, dbName = " + Transactioner.trancDbName.get());
        }

        return connection;
    }

    public Connection getConnection(String dbName) throws Exception {
        Boolean trancOn = Transactioner.trancOn.get();

        Connection connection = null;
        if (trancOn != null && trancOn) {
            String existingDbName = Transactioner.trancDbName.get();
            if (existingDbName != null) {
                if (!existingDbName.equals(dbName)) {
                    String msg = String.format("The transaction could not be applied on cross database, [%s] conflicts with [%s]", existingDbName, dbName);
                    log.error(msg);
                    throw new DfwException(msg);
                }

                connection = Transactioner.connLocal.get();
                if (connection == null) {
                    log.error("The db connection wasn't initialized correctly, dbName = " + dbName);
                }
            } else {
                log.info("The db connection has not been initialized for current transaction, dbName = " + dbName);
            }
        }

        return connection;
    }

    private void incrementDepth() {
        Transactioner.trancDepth.get().incrementAndGet();
    }

    private void decrementDepth() {
        Transactioner.trancDepth.get().decrementAndGet();
    }

    public int execute(DfwFunction function, String dbName) throws Exception {
        Boolean trancOn = Transactioner.trancOn.get();
        if (trancOn == null) {
            Transactioner.trancOn.set(Boolean.TRUE);
            Transactioner.trancDepth.set(new AtomicInteger(0));
        }

        if (dbName != null) {
            initConnection(dbName);
        }

        return execute(function);
    }

    public int execute(DfwFunction function) throws Exception {
        Boolean trancOn = Transactioner.trancOn.get();
        if (trancOn == null) {
            Transactioner.trancOn.set(Boolean.TRUE);
            Transactioner.trancDepth.set(new AtomicInteger(0));
        }

        int rt = 0;
        try {
            this.incrementDepth();

            function.accept();

            Connection connection = Transactioner.connLocal.get();
            if (connection != null) {
                connection.commit();
            }
            rt = 1;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("received an exception when executing the transaction, msg = ", e.getMessage());
            Connection connection = Transactioner.connLocal.get();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    log.info("received an exception when trying rollback the transaction, msg = ", sqlException.getMessage());
                }
            }
            throw e;
        } finally {
            this.decrementDepth();

            Connection connection = Transactioner.connLocal.get();
            if (connection != null && Transactioner.trancDepth.get().get() <= 0) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    log.info("received an exception when trying close the transaction, msg = ", sqlException.getMessage());
                }
                Transactioner.trancOn.set(null);
                Transactioner.trancDbName.set(null);
                Transactioner.connLocal.set(null);
                Transactioner.trancDepth.set(null);
            }
        }

        return rt;
    }

}
