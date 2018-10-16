package com.pphh.dfw.core.dao;

import com.pphh.dfw.core.IEntity;
import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.sqlb.ISqlBuilder;

import java.util.List;
import java.util.function.Function;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface IDao {

    <T> T queryByPk(T entity);

    <T extends IEntity> List<T> queryBySample(T entity);

    <T extends IEntity> List<T> queryAll(Long limit);

    <T extends IEntity> long countBySample(T entity);

    <T extends IEntity> int insert(T entity);

    <T extends IEntity> int[] insert(List<T> entities);

    <T extends IEntity> int delete(T entity);

    <T extends IEntity> int deleteBySample(T entity);

    <T extends IEntity> int[] delete(List<T> entities);

    <T extends IEntity> int update(T entity);

    <T extends IEntity> int[] update(List<T> entities);

    <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder);

    <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder);

    //<T extends IEntity> Page<T> queryByPage(T entity, Object pageable);

    int execute(Function function);

    int run(ISqlBuilder sqlBuilder);

    int[] run(IBatchSqlBuilder sqlBuilder);

    IHints getHints();

}
