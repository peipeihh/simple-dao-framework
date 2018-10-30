package com.pphh.dfw.config;

import com.pphh.dfw.GlobalDataSourceConfig;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.jdbc.ConnectionTest;
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
public class ConfigLoaderTest {

    @Test
    public void testConfigLoader() throws Exception {
        IDataSourceConfig instance = GlobalDataSourceConfig.getInstance().load();
        PhysicalDBConfig physicalDBConfig0 = instance.getPhysicalDBConfigMap("db0");
        PhysicalDBConfig physicalDBConfig1 = instance.getPhysicalDBConfigMap("db1");
        testPhysicalDbConfig(physicalDBConfig0, "select * from `database0`.order");
        testPhysicalDbConfig(physicalDBConfig1, "select * from `database1`.order");
    }

    private void testPhysicalDbConfig(PhysicalDBConfig physicalDBConfig, String testSql) throws SQLException {
        PoolProperties p = new PoolProperties();
        p.setUrl(physicalDBConfig.getConnectionUrl());
        p.setUsername(physicalDBConfig.getUserName());
        p.setPassword(physicalDBConfig.getUserPwd());
        p.setConnectionProperties(physicalDBConfig.getConnectionProperties());

        DataSource dataSource = new DataSource(p);
        ConnectionTest.checkConnection(dataSource, testSql);
    }

}
