package com.pphh.dfw.core.table;

import com.pphh.dfw.core.sqlb.ISqlSegement;

import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface ITable extends ISqlSegement {

    String getName();

    ITableField getPkField();

    List<ITableField> getFields();

}
