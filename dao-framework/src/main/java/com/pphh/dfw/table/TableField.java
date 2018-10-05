package com.pphh.dfw.table;


/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class TableField extends AbstractTableField {

    public TableField(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String buildSql() {
        return String.format("`%s`", fieldName);
    }

}
