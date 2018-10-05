package com.pphh.dfw;

import com.pphh.dfw.core.ShardStrategy;

import java.util.Properties;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/26/2018
 */
public class ModShardStrategy implements ShardStrategy {

    private String tableName;
    private String tableSeparator;

    @Override
    public void initialize(Properties settings) {
        tableName = settings.getProperty("tableName");
        tableSeparator = settings.getProperty("tableSeparator");
    }

    public String calcDbShard(String value) {
        Long shard = Long.parseLong(value) % 2;
        return shard.toString();
    }

    @Override
    public String calcTableShard(String value) {
        Long shard = Long.parseLong(value) % 2;
        return String.format("%s%s%s", tableName, tableSeparator, shard.toString());
    }

}
