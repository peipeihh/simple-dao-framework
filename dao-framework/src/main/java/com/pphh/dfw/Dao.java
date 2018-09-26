package com.pphh.dfw;

import com.pphh.dfw.core.*;

import java.util.List;
import java.util.function.Function;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class Dao implements IDao {


    @Override
    public IEntity queryByPk(IEntity entity) {
        return null;
    }

    @Override
    public List<IEntity> queryBySample(IEntity entity) {
        return null;
    }

    @Override
    public List<IEntity> queryAll(Long max) {
        return null;
    }

    @Override
    public int insert(IEntity entity) {
        return 0;
    }

    @Override
    public int[] insert(List<IEntity> entities) {
        return new int[0];
    }

    @Override
    public int delete(IEntity entity) {
        return 0;
    }

    @Override
    public int[] delete(List<IEntity> entities) {
        return new int[0];
    }

    @Override
    public int update(IEntity entity) {
        return 0;
    }

    @Override
    public int[] update(List<IEntity> entities) {
        return new int[0];
    }

    @Override
    public int execute(Function function) {
        return 0;
    }

    @Override
    public IEntity queryForObject(ISqlBuilder sqlBuilder) {
        return null;
    }

    @Override
    public List<IEntity> queryForList(ISqlBuilder sqlBuilder) {
        return null;
    }

    @Override
    public int run(ISqlBuilder sqlBuilder) {
        return 0;
    }

    @Override
    public int[] run(IBatchSqlBuilder sqlBuilder) {
        return new int[0];
    }

    @Override
    public IHints getHints() {
        return null;
    }
}
