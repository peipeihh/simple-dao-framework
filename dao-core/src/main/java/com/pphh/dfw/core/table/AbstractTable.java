package com.pphh.dfw.core.table;

import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public abstract class AbstractTable implements ITable {

    private final String name;

    public AbstractTable(String name) {
        this.name = name;
    }

    @Override
    public List<ITableField> getFields() {
        return null;
    }

    @Override
    public String buildSql() {
        return String.format("`%s`", this.name);
    }
}
