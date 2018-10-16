package com.pphh.dfw.table;

import com.pphh.dfw.core.table.ITable;
import com.pphh.dfw.core.table.ITableField;

import java.lang.reflect.Type;
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
    public String getName() {
        return name;
    }

    @Override
    public List<ITableField> getFields() {
        return this.fields.entrySet().stream().map(Map.Entry<AbstractTableField, Object>::getKey).collect(Collectors.toList());
    }

    @Override
    public ITableField getPkField() {
        return primaryField;
    }

    @Override
    public String buildSql() {
        return String.format("`%s`", this.name);
    }

    public ITableField insertFields(String fieldName, String fieldDef, Type fieldType, Object filedValue) {
        return insertFields(fieldName, fieldDef, fieldType, filedValue, Boolean.FALSE);
    }

    public ITableField insertFields(String fieldName, String fieldDef, Type fieldType, Object filedValue, Boolean isPrimaryKey) {
        TableField f = new TableField(fieldName, fieldDef, fieldType, filedValue);
        fields.put(f, "");
        if (isPrimaryKey) {
            this.primaryField = f;
        }
        return f;
    }

    public Object getFieldValue(ITableField field) {
        //return fields.get(field);
        return field.getFieldValue();
    }

    public Object getFieldValue(String field) {
        Object value = null;
        for (Map.Entry<AbstractTableField, Object> entry : this.fields.entrySet()) {
            AbstractTableField f = entry.getKey();
            if (f.getFieldName().equals(field)) {
                value = f.getFieldValue();
                break;
            }
        }
        return value;
    }

}
