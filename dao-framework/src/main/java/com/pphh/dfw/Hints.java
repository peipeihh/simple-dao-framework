package com.pphh.dfw;

import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.constant.HintEnum;
import com.pphh.dfw.core.constant.SqlProviderEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class Hints implements IHints {

    private Map<HintEnum, Object> hints = new ConcurrentHashMap<>();

    @Override
    public IHints debug() {
        hints.put(HintEnum.DEBUG_MODE, Boolean.TRUE);
        return this;
    }

    @Override
    public IHints insertId() {
        hints.put(HintEnum.INSERT_ID, Boolean.TRUE);
        return this;
    }

    @Override
    public IHints setIdBack() {
        hints.put(HintEnum.SET_ID_BACK, Boolean.TRUE);
        return this;
    }

    @Override
    public IHints sqlDialect(SqlProviderEnum dialect) {
        hints.put(HintEnum.SQL_DIALECT, dialect);
        return this;
    }

    @Override
    public IHints inDbShard(Object value) {
        hints.put(HintEnum.DB_SHARD, value);
        return this;
    }

    @Override
    public IHints inTableShard(Object value) {
        hints.put(HintEnum.TABLE_SHARD, value);
        return this;
    }

    @Override
    public IHints dbShardValue(Object value) {
        hints.put(HintEnum.DB_SHARD_VALUE, value);
        return this;
    }

    @Override
    public IHints tableShardValue(Object value) {
        hints.put(HintEnum.TABLE_SHARD_VALUE, value);
        return this;
    }

    @Override
    public Object getHintValue(HintEnum hintEnum) {
        return hints.get(hintEnum);
    }

    @Override
    public Boolean hasHint(HintEnum hintEnum) {
        return hints.containsKey(hintEnum);
    }


}
