package com.pphh.dfw.jdbc;


import com.pphh.dfw.GlobalDataSourceConfig;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.jdbc.IDataSource;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/26/2018
 */
public class TomcatJdbcDataSource implements IDataSource {

    private static AtomicInteger count = new AtomicInteger();
    private DataSource dataSource;

    public TomcatJdbcDataSource(String dbName) throws Exception {
        IDataSourceConfig instance = GlobalDataSourceConfig.getInstance().load();
        PhysicalDBConfig physicalDBConfig = instance.getPhysicalDBConfigMap(dbName);
        this.init(physicalDBConfig);
    }

    public TomcatJdbcDataSource(PhysicalDBConfig physicalDBConfig) throws Exception {
        this.init(physicalDBConfig);
    }

    public void init(PhysicalDBConfig physicalDBConfig){
        PoolProperties p = new PoolProperties();
        p.setUrl(physicalDBConfig.getConnectionUrl());
        p.setUsername(physicalDBConfig.getUserName());
        p.setPassword(physicalDBConfig.getUserPwd());
        p.setConnectionProperties(physicalDBConfig.getConnectionProperties());

        Properties properties = physicalDBConfig.getPoolProperties();
        p.setTestOnConnect(properties.getProperty("testOnConnect").equalsIgnoreCase("true"));
        p.setTestOnBorrow(properties.getProperty("testOnBorrow").equalsIgnoreCase("true"));
        p.setTestOnReturn(properties.getProperty("testOnReturn").equalsIgnoreCase("true"));
        p.setTestWhileIdle(properties.getProperty("testWhileIdle").equalsIgnoreCase("true"));
        p.setValidationQuery(properties.getProperty("validationQuery"));
        p.setValidationInterval(Integer.parseInt(properties.getProperty("validationInterval")));
        p.setTimeBetweenEvictionRunsMillis(Integer.parseInt(properties.getProperty("timeBetweenEvictionRunsMillis")));
        p.setMinEvictableIdleTimeMillis(Integer.parseInt(properties.getProperty("minEvictableIdleTimeMillis")));
        p.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
        p.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));
        p.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
        p.setMaxActive(Integer.parseInt(properties.getProperty("maxActive")));
        p.setMaxWait(Integer.parseInt(properties.getProperty("maxWait")));
        p.setRemoveAbandoned(properties.getProperty("removeAbandoned").equalsIgnoreCase("true"));
        p.setRemoveAbandonedTimeout(Integer.parseInt(properties.getProperty("removeAbandonedTimeout")));
        p.setLogAbandoned(properties.getProperty("logAbandoned").equalsIgnoreCase("true"));
        p.setJmxEnabled(properties.getProperty("jmxEnabled").equalsIgnoreCase("true"));

        // TODO: config options by sql driver type
        //p.set(properties.getProperty("mysqlOption"));
        //p.set(properties.getProperty("sqlServeOption"));

        this.dataSource = new org.apache.tomcat.jdbc.pool.DataSource(p);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}
