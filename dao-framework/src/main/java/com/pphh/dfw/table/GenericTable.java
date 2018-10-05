package com.pphh.dfw.table;

import com.pphh.dfw.core.table.ITable;
import com.pphh.dfw.core.table.ITableField;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by mh on 2018/10/5.
 */
public class GenericTable implements ITable {

    private final String name;
    private AbstractTableField primaryField;
    private Map<AbstractTableField, Object> fields = new ConcurrentHashMap<>();

    public GenericTable(String name) {
        this.name = name;
    }

    @Override
    public List<ITableField> getFields() {
        return this.fields.entrySet().stream().map(Map.Entry<AbstractTableField, Object>::getKey).collect(Collectors.toList());
    }

    @Override
    public ITableField getPrimaryField() {
        return primaryField;
    }

    @Override
    public String buildSql() {
        return String.format("`%s`", this.name);
    }

    public ITableField insertFields(String field, Object value) {
        return insertFields(field, value, Boolean.FALSE);
    }

    public ITableField insertFields(String field, Object value, Boolean isPrimary) {
        TableField f = new TableField(field);
        fields.put(f, value);
        if (isPrimary) {
            this.primaryField = f;
        }
        return f;
    }

    public Object getFieldValue(ITableField field) {
        return fields.get(field);
    }

}
