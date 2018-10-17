package com.pphh.dfw.config;

import com.pphh.dfw.GlobalDataSourceConfig;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.jdbc.TestConnection;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.Test;


/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class ConfigLoaderTest {

    @Test
    public void testConfigLoader() throws Exception {
        IDataSourceConfig instance = GlobalDataSourceConfig.getInstance().load();

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
