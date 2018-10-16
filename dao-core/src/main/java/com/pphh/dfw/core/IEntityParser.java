package com.pphh.dfw.core;

import com.pphh.dfw.core.table.ITable;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface IEntityParser<T extends ITable> {

    T parse(IEntity iEntity);

    T parse(Class<? extends IEntity> clazz);

}
