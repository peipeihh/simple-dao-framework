package com.pphh.dfw.core.dao;

import com.pphh.dfw.core.IEntity;
import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.function.DfwFunction;
import com.pphh.dfw.core.sqlb.ISqlBuilder;

import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface IDao {

    <T> T query(T entityWithPk) throws Exception;

    <T> T query(T entityWithPk, IHints hints) throws Exception;

    <T extends IEntity> List<T> queryBySample(T entity) throws Exception;

    <T extends IEntity> List<T> queryBySample(T entity, IHints hints) throws Exception;

    <T extends IEntity> List<T> queryAll(Long limit) throws Exception;

    <T extends IEntity> List<T> queryAll(Long limit, IHints hints) throws Exception;

    <T extends IEntity> long countBySample(T entity) throws Exception;

    <T extends IEntity> long countBySample(T entity, IHints hints) throws Exception;

    <T extends IEntity> int insert(T entity) throws Exception;

    <T extends IEntity> int insert(T entity, IHints hints) throws Exception;

    <T extends IEntity> int[] insert(List<T> entities) throws Exception;

    <T extends IEntity> int[] insert(List<T> entities, IHints hints) throws Exception;

    <T extends IEntity> int delete(T entityWithPk) throws Exception;

    <T extends IEntity> int delete(T entityWithPk, IHints hints) throws Exception;

    <T extends IEntity> int deleteBySample(T sample) throws Exception;

    <T extends IEntity> int deleteBySample(T sample, IHints hints) throws Exception;

    <T extends IEntity> int[] delete(List<T> entitiesWithPk) throws Exception;

    <T extends IEntity> int[] delete(List<T> entitiesWithPk, IHints hints) throws Exception;

    <T extends IEntity> int[] deleteBySample(List<T> samples) throws Exception;

    <T extends IEntity> int[] deleteBySample(List<T> samples, IHints hints) throws Exception;

    <T extends IEntity> int update(T entityWithPk) throws Exception;

    <T extends IEntity> int update(T entityWithPk, IHints hints) throws Exception;

    <T extends IEntity> int[] update(List<T> entitiesWithPk) throws Exception;

    <T extends IEntity> int[] update(List<T> entitiesWithPk, IHints hints) throws Exception;

    <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder) throws Exception;

    <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder, IHints hints) throws Exception;

    <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder) throws Exception;

    <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder, IHints hints) throws Exception;

    int count(ISqlBuilder sqlBuilder) throws Exception;

    int count(ISqlBuilder sqlBuilder, IHints hints) throws Exception;

    //<T extends IEntity> Page<T> queryByPage(T entity, Object pageable);

    int execute(DfwFunction function) throws Exception;

    int execute(DfwFunction function, IHints hints) throws Exception;

    int run(ISqlBuilder updateSqlBuilder) throws Exception;

    int run(ISqlBuilder updateSqlBuilder, IHints hints) throws Exception;

    int[] run(IBatchSqlBuilder updateSqlBuilder) throws Exception;

    int[] run(IBatchSqlBuilder updateSqlBuilder, IHints hints) throws Exception;

    String getLogicName();

}
