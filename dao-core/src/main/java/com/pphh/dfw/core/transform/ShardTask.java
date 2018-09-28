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
    private PhysicalDBConfig physicalDBConfig;
    private ShardTaskResult<T> result;

}
