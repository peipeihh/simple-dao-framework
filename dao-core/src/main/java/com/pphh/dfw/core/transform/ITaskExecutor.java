package com.pphh.dfw.core.transform;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public interface ITaskExecutor {

    public TaskResult run(Task task);

    public TaskResult execute(Task task);

}
