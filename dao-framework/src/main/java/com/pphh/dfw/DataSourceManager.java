package com.pphh.dfw;

import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.core.jdbc.IDataSource;
import com.pphh.dfw.jdbc.TomcatJdbcDataSource;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 3/15/2019
 */
public class DataSourceManager {

    private static Map<String, IDataSource> dataSourcePoolMap = new ConcurrentHashMap<>();


    private static DataSourceManager ourInstance = new DataSourceManager();

    private DataSourceManager() {
    }

    public static DataSourceManager getInstance() {
        return ourInstance;
    }

    public Connection getConnection(String dbName) throws Exception {
        PhysicalDBConfig shardPhysicalDBConfig = GlobalDataSourceConfig.getInstance().getPhysicalDBConfigMap(dbName);
        IDataSource dataSource = dataSourcePoolMap.get(dbName);
        if (dataSource == null) {
            dataSource = new TomcatJdbcDataSource(shardPhysicalDBConfig);
            dataSourcePoolMap.put(dbName, dataSource);
        }
        return dataSource.getConnection();
    }
}
