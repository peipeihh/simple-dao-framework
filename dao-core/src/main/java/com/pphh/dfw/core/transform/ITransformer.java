package com.pphh.dfw.core.transform;


import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public interface ITransformer {

    int run(String sql, String dbName) throws Exception;

    <T> List<T> run(String sql, String dbName, Class<? extends T> resultClz) throws Exception;

    <T> TaskResult<T> run(Task task) throws Exception;

}
