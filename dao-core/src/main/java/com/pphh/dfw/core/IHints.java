package com.pphh.dfw.core;

import com.pphh.dfw.core.constant.HintEnum;
import com.pphh.dfw.core.constant.SqlProviderEnum;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface IHints {

    IHints debug();

    IHints insertId();

    IHints setIdBack();

    IHints sqlDialect(SqlProviderEnum dialect);

    IHints inDbShard(Object value);

    IHints inTableShard(Object value);

    IHints dbShardValue(Object value);

    IHints tableShardValue(Object value);

    IHints into(Class clazz);

    Object getHintValue(HintEnum hintEnum);

    void setHintValue(HintEnum hintEnum, Object value);

    Boolean hasHint(HintEnum hintEnum);

}
