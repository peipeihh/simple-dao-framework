package com.pphh.dfw;

import com.pphh.dfw.core.exception.DfwException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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

    public Connection getConnection(String dbName) throws Exception {
        Boolean trancOn = Transactioner.trancOn.get();

        Connection connection = null;
        if (trancOn != null && trancOn) {

            connection = Transactioner.connLocal.get();
            if (connection != null) {
                String existingDbName = Transactioner.trancDbName.get();
                if (existingDbName == null) {
                    throw new DfwException("The db name is not saved for existing connection");
                }

                if (existingDbName != null && !existingDbName.equals(dbName)) {
                    String msg = String.format("The transaction could not be applied on cross database, [%s] conflicts with [%s]", this.trancDbName.get(), dbName);
                    throw new DfwException(msg);
                }
            } else {
                Transactioner.trancDbName.set(dbName);
                connection = DataSourceManager.getInstance().getConnection(dbName);
                connection.setAutoCommit(false);
                Transactioner.connLocal.set(connection);
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


    public int execute(Consumer consumer) throws Exception {
        Boolean trancOn = Transactioner.trancOn.get();
        if (trancOn == null) {
            Transactioner.trancOn.set(Boolean.TRUE);
            Transactioner.trancDepth.set(new AtomicInteger(0));
        }

        int rt = 0;
        try {
            this.incrementDepth();

            consumer.accept(null);
            Connection connection = Transactioner.connLocal.get();
            if (connection != null) {
                connection.commit();
            }
        } catch (Exception e) {
            rt = 1;
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
