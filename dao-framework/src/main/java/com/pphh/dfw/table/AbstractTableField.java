package com.pphh.dfw.table;

import com.pphh.dfw.core.table.Expression;
import com.pphh.dfw.core.table.ITableField;

import java.lang.reflect.Type;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public abstract class AbstractTableField implements ITableField {

    protected String fieldName;
    protected String fieldDefinition;
    protected Type fieldType;
    protected Object fieldValue;

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getFieldDefinition() {
        return fieldDefinition;
    }

    @Override
    public Type getFieldType() {
        return fieldType;
    }

    @Override
    public Object getFieldValue() {
        return fieldValue;
    }

    @Override
    public Expression equal(Object value) {
        return new Expression(String.format("`%s` = '%s'", fieldName, value));
    }

    @Override
    public Expression notEqual(Object value) {
        return new Expression(String.format("`%s` <> '%s'", fieldName, value));
    }

    @Override
    public Expression greaterThan(Object value) {
        return new Expression(String.format("`%s` > '%s'", fieldName, value));
    }

    @Override
    public Expression greaterThanOrEqual(Object value) {
        return new Expression(String.format("`%s` >= '%s'", fieldName, value));
    }

    @Override
    public Expression lessThan(Object value) {
        return new Expression(String.format("`%s` < '%s'", fieldName, value));
    }

    @Override
    public Expression between(Object firstValue, Object secondValue) {
        return new Expression(String.format("`%s` BETWEEN '%s' AND '%s'", fieldName, firstValue, secondValue));
    }

    @Override
    public Expression notBetween(Object firstValue, Object secondValue) {
        return new Expression(String.format("`%s` NOT BETWEEN '%s' AND '%s'", fieldName, firstValue, secondValue));
    }

    @Override
    public Expression in(Object value) {
        return new Expression(String.format("`%s` in %s", fieldName, value));
    }

    @Override
    public Expression notIn(Object value) {
        return new Expression(String.format("`%s` <= %s", fieldName, value));
    }

    @Override
    public Expression like(Object value) {
        return new Expression(String.format("`%s` LIKE %s", fieldName, value));
    }

    @Override
    public Expression notLike(Object value) {
        return new Expression(String.format("`%s` NOT LIKE %s", fieldName, value));
    }

    @Override
    public Expression isNull(Object value) {
        return new Expression(String.format("`%s` IS NULL", fieldName));
    }

    @Override
    public Expression isNotNull(Object value) {
        return new Expression(String.format("`%s` IS NOT NULL", fieldName));
    }

    public abstract String buildSql();

}
