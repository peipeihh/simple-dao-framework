package com.pphh.dfw.core.ds;

import com.pphh.dfw.core.ShardStrategy;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class LogicDBConfig {

    private String id;
    private List<String> dbEntries;
    private String defaultDriverContext;
    private ShardStrategy shardStrategy;
    private String dbShardColumn;
    private String tableShardColumn;
    private String tableName;
    private String tableSeparator;
    private Map<String, PhysicalDBConfig> physicalDBConfigMap = new ConcurrentHashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getDbEntries() {
        return dbEntries;
    }

    public void setDbEntries(List<String> dbEntries) {
        this.dbEntries = dbEntries;
    }

    public String getDefaultDriverContext() {
        return defaultDriverContext;
    }

    public void setDefaultDriverContext(String defaultDriverContext) {
        this.defaultDriverContext = defaultDriverContext;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSeparator() {
        return tableSeparator;
    }

    public void setTableSeparator(String tableSeparator) {
        this.tableSeparator = tableSeparator;
    }

    public Map<String, PhysicalDBConfig> getPhysicalDBConfigMap() {
        return physicalDBConfigMap;
    }

    public void setPhysicalDBConfigMap(Map<String, PhysicalDBConfig> physicalDBConfigMap) {
        this.physicalDBConfigMap = physicalDBConfigMap;
    }

    public ShardStrategy getShardStrategy() {
        return shardStrategy;
    }

    public void setShardStrategy(ShardStrategy shardStrategy) {
        this.shardStrategy = shardStrategy;
    }

    public String getDbShardColumn() {
        return dbShardColumn;
    }

    public void setDbShardColumn(String dbShardColumn) {
        this.dbShardColumn = dbShardColumn;
    }

    public String getTableShardColumn() {
        return tableShardColumn;
    }

    public void setTableShardColumn(String tableShardColumn) {
        this.tableShardColumn = tableShardColumn;
    }

    public Set<String> getPhysicalDBIdList() {
        return physicalDBConfigMap.keySet();
    }

    public PhysicalDBConfig getPhysicalDBConfig(String id) {
        return physicalDBConfigMap.get(id);
    }

    public void setPhysicalDBConfig(String id, PhysicalDBConfig dbConfig) {
        if (id == null || dbConfig == null) {
            throw new NullPointerException("id/dbConfig couldn't be null.");
        }

        this.physicalDBConfigMap.put(id, dbConfig);
    }

}
