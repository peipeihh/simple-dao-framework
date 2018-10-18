package com.pphh.dfw.table;

import java.lang.reflect.Type;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class TableField extends AbstractTableField {

    public TableField(String fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    public TableField(String fieldName, String fieldDefinication, Type fieldType, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldDefinition = fieldDefinication;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
    }

    @Override
    public String buildSql() {
        return String.format("`%s`", this.fieldDefinition);
    }
}
