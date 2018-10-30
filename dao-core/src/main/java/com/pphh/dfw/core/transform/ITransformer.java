package com.pphh.dfw.core.transform;


/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public interface ITransformer {

    <T> TaskResult<T> run(Task task) throws Exception;

}
