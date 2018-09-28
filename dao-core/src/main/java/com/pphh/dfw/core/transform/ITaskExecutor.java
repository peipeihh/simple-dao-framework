package com.pphh.dfw.core.transform;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public interface ITaskExecutor {

    public ShardTaskResult run(ShardTask task);

    public ShardTaskResult execute(ShardTask task);

}
