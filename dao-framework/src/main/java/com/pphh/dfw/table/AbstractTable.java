package com.pphh.dfw.table;

import com.pphh.dfw.core.table.ITable;
import com.pphh.dfw.core.table.ITableField;

import java.util.ArrayList;
import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public abstract class AbstractTable implements ITable {

    private final String name;
    private List<AbstractTableField> fields = new ArrayList<>();
    private AbstractTableField primaryField;

    public AbstractTable(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ITableField> getFields() {
        List<ITableField> list = new ArrayList<>();
        for (AbstractTableField field : this.fields) {
            list.add(field);
        }
        return list;
    }

    @Override
    public String buildSql() {
        return String.format("`%s`", this.name);
    }

    @Override
    public ITableField getPkField() {
        return primaryField;
    }

    protected void insertFields(AbstractTableField field) {
        fields.add(field);
    }

    protected void setPkField(AbstractTableField field) {
        primaryField = field;
    }

}
