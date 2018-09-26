package com.pphh.dfw;

import com.pphh.dfw.core.ShardStrategy;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/26/2018
 */
public class ModShardStrategy implements ShardStrategy {

    @Override
    public String calcDbShard(String value) {
        return value;
    }

    @Override
    public String calcTableShard(String value) {
        return value;
    }

}
