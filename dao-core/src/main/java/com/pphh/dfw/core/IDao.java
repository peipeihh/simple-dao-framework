package com.pphh.dfw.core;

import java.util.List;
import java.util.function.Function;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface IDao {

    IEntity queryByPk(IEntity entity);

    List<IEntity> queryBySample(IEntity entity);

    List<IEntity> queryAll(Long max);

    int insert(IEntity entity);

    int[] insert(List<IEntity> entities);

    int delete(IEntity entity);

    int[] delete(List<IEntity> entities);

    int update(IEntity entity);

    int[] update(List<IEntity> entities);

    int execute(Function function);

    IEntity queryForObject(ISqlBuilder sqlBuilder);

    List<IEntity> queryForList(ISqlBuilder sqlBuilder);

    int run(ISqlBuilder sqlBuilder);

    int[] run(IBatchSqlBuilder sqlBuilder);

    IHints getHints();

}
