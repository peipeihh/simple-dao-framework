package com.pphh.dfw.core;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface ShardStrategy {

    public String calcDbShard(String value);

    public String calcTableShard(String value);

}
