package com.pphh.dfw.core.transform;

import com.pphh.dfw.core.ds.PhysicalDBConfig;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class ShardTask<T> {

    private String sql;
    private PhysicalDBConfig shardPhysicalDBConfig;
    private ShardTaskResult<T> result;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public PhysicalDBConfig getShardPhysicalDBConfig() {
        return shardPhysicalDBConfig;
    }

    public void setShardPhysicalDBConfig(PhysicalDBConfig shardPhysicalDBConfig) {
        this.shardPhysicalDBConfig = shardPhysicalDBConfig;
    }

    public ShardTaskResult<T> getResult() {
        return result;
    }

    public void setResult(ShardTaskResult<T> result) {
        this.result = result;
    }

}
