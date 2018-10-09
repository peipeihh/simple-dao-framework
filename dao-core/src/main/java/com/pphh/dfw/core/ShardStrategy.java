package com.pphh.dfw.core;

import java.util.Properties;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface ShardStrategy {

    public void initialize(Properties settings);

    public String locateDbShard(String value, Boolean requireCalc);

    public String locateTableShard(String value, Boolean requireCalc);

}
