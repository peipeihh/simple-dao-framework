package com.pphh.dfw.jdbc;


import com.pphh.dfw.GlobalDataSourceConfig;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.jdbc.IDataSource;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;
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

    public TomcatJdbcDataSource() throws Exception {
        IDataSourceConfig instance = GlobalDataSourceConfig.getInstance().load();

        PhysicalDBConfig physicalDBConfig = instance.getPhysicalDBConfigMap("db0");
        PoolProperties p = new PoolProperties();
        p.setUrl(physicalDBConfig.getConnectionUrl());
        p.setUsername(physicalDBConfig.getUserName());
        p.setPassword(physicalDBConfig.getUserPwd());
        p.setConnectionProperties(physicalDBConfig.getConnectionProperties());

        this.dataSource = new org.apache.tomcat.jdbc.pool.DataSource(p);
    }

    public TomcatJdbcDataSource(PhysicalDBConfig physicalDBConfig) throws Exception {
        PoolProperties p = new PoolProperties();
        p.setUrl(physicalDBConfig.getConnectionUrl());
        p.setUsername(physicalDBConfig.getUserName());
        p.setPassword(physicalDBConfig.getUserPwd());
        p.setConnectionProperties(physicalDBConfig.getConnectionProperties());

        this.dataSource = new org.apache.tomcat.jdbc.pool.DataSource(p);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}
