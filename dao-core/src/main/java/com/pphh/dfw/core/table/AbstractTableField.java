package com.pphh.dfw.core.table;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public abstract class AbstractTableField implements ITableField {

    protected String fieldName;

    public Expression equal(Object value) {
        return new Expression(String.format("%s = %s", fieldName, value));
    }

    public Expression notEqual(Object value){
        return new Expression(String.format("%s <> %s", fieldName, value));
    }

    public Expression greaterThan(Object value){
        return new Expression(String.format("%s > %s", fieldName, value));
    }

    public Expression greaterThanOrEqual(Object value){
        return new Expression(String.format("%s >= %s", fieldName, value));
    }

    public Expression lessThan(Object value){
        return new Expression(String.format("%s < %s", fieldName, value));
    }

    public Expression between(Object firstValue, Object secondValue){
        return new Expression(String.format("%s BETWEEN %s AND %s", fieldName, firstValue, secondValue));
    }

    public Expression notBetween(Object firstValue, Object secondValue){
        return new Expression(String.format("%s NOT BETWEEN %s AND %s", fieldName, firstValue, secondValue));
    }

    public Expression in(Object value){
        return new Expression(String.format("%s in %s", fieldName, value));
    }

    public Expression notIn(Object value){
        return new Expression(String.format("%s <= %s", fieldName, value));
    }

    public Expression like(Object value){
        return new Expression(String.format("%s LIKE %s", fieldName, value));
    }

    public Expression notLike(Object value){
        return new Expression(String.format("%s NOT LIKE %s", fieldName, value));
    }

    public Expression isNull(Object value){
        return new Expression(String.format("%s IS NULL", fieldName));
    }

    public Expression isNotNull(Object value){
        return new Expression(String.format("%s IS NOT NULL", fieldName));
    }

    @Override
    public abstract String buildSql();

}
