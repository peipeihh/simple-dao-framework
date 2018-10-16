package com.pphh.dfw.core.transform;

import com.pphh.dfw.core.IEntity;

import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public interface ITransformer {

    <T> List<T> run(String sql, String dbName, Class<? extends T> resultClz) throws Exception;

    <T> ShardTaskResult<T> run(ShardTask<T> task, Class<? extends IEntity> resultClz);

}
