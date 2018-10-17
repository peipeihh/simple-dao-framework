package com.pphh.dfw.jdbc;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class SimpleTest {

    @Test
    public void test2() throws SQLException {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/test");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("root");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(p);
        TestConnection.testConnection(dataSource, "select * from `database0`.order");
    }

}
