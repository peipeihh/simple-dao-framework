package com.pphh.dfw.shard;

import com.pphh.dfw.core.ShardStrategy;

import java.util.Properties;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/26/2018
 */
public class ModShardStrategy implements ShardStrategy {

    private Integer dbMod = 2;
    private Integer tableMod = 3;
    private String tableName;
    private String tableSeparator;

    @Override
    public void initialize(Properties settings) {
        tableName = settings.getProperty("tableName");
        tableSeparator = settings.getProperty("tableSeparator");
    }

    @Override
    public String locateDbShard(String value, Boolean requireCalc) {
        Long shard = Long.parseLong(value);
        if (requireCalc) {
            shard = shard % dbMod;
        }
        return shard.toString();
    }

    @Override
    public String locateTableShard(String value, Boolean requireCalc) {
        Long shard = Long.parseLong(value);
        if (requireCalc) {
            shard = shard % tableMod;
        }
        return String.format("%s%s%s", tableName, tableSeparator, shard.toString());
    }

}
