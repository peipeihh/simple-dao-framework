package com.pphh.dfw;

import com.pphh.dfw.core.ds.PhysicalDBConfig;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class ConfigLoaderTest {

    @Test
    public void testConfigLoader() throws Exception {
        GlobalDataSourceConfig instance = GlobalDataSourceConfig.getInstance().load();

        PhysicalDBConfig physicalDBConfig = instance.getPhysicalDBConfigMap("db0");

        PoolProperties p = new PoolProperties();
        p.setUrl(physicalDBConfig.getConnectionUrl());
        p.setUsername(physicalDBConfig.getUserName());
        p.setPassword(physicalDBConfig.getUserPwd());
        p.setConnectionProperties(physicalDBConfig.getConnectionProperties());

        DataSource dataSource = new DataSource(p);
        TestConnection.testConnection(dataSource);
    }

}
