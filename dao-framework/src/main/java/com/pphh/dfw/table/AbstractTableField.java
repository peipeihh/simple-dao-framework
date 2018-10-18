package com.pphh.dfw.table;

import com.pphh.dfw.core.sqlb.ISqlSegement;
import com.pphh.dfw.core.table.Expression;
import com.pphh.dfw.core.table.ITableField;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

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
        Expression expression;
        if (value == null) {
            expression = new Expression(String.format("`%s` IS NULL", fieldName));
        } else {
            expression = new Expression(String.format("`%s` = '%s'", fieldName, value));
        }
        return expression;
    }

    @Override
    public Expression notEqual(Object value) {
        Expression expression;
        if (value == null) {
            expression = new Expression(String.format("`%s` IS NOT NULL", fieldName));
        } else {
            expression = new Expression(String.format("`%s` <> '%s'", fieldName, value));
        }
        return expression;
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
    public Expression in(List values) {
        StringBuilder inConditionBuilder = new StringBuilder();
        for (Object value : values) {
            inConditionBuilder.append(String.format("'%s', ", value));
        }
        String inCondition = inConditionBuilder.toString().trim();
        if (inCondition.endsWith(",")) {
            inCondition = inCondition.substring(0, inCondition.length() - 1);
        }
        return new Expression(String.format("`%s` IN ( %s )", fieldName, inCondition));
    }

    @Override
    public Expression notIn(List values) {
        StringBuilder inConditionBuilder = new StringBuilder();
        for (Object value : values) {
            inConditionBuilder.append(String.format("'%s', ", value));
        }
        String inCondition = inConditionBuilder.toString().trim();
        if (inCondition.endsWith(",")) {
            inCondition = inCondition.substring(0, inCondition.length() - 1);
        }
        return new Expression(String.format("`%s` NOT IN ( %s )", fieldName, inCondition));
    }

    @Override
    public Expression in(Object... values) {
        return in(Arrays.asList(values));
    }

    @Override
    public Expression notIn(Object... values) {
        return notIn(Arrays.asList(values));
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
    public Expression isNull() {
        return new Expression(String.format("`%s` IS NULL", fieldName)).nullable();
    }

    @Override
    public Expression isNotNull() {
        return new Expression(String.format("`%s` IS NOT NULL", fieldName)).nullable();
    }

    @Override
    public Expression asc() {
        return new Expression(String.format("`%s` ASC", fieldName));
    }

    @Override
    public Expression desc() {
        return new Expression(String.format("`%s` DESC", fieldName));
    }

    @Override
    public abstract String buildSql();

}
