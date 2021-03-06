package com.pphh.dfw.core.table;

import com.pphh.dfw.core.sqlb.ISqlSegement;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface ITableField extends ISqlSegement {

    String getFieldName();

    String getFieldDefinition();

    Type getFieldType();

    Object getFieldValue();

    Expression equal(Object value);

    Expression notEqual(Object value);

    Expression greaterThan(Object value);

    Expression greaterThanOrEqual(Object value);

    Expression lessThan(Object value);

    Expression between(Object firstValue, Object secondValue);

    Expression notBetween(Object firstValue, Object secondValue);

    Expression in(List values);

    Expression notIn(List values);

    Expression in(Object...value);

    Expression notIn(Object...value);

    Expression like(Object value);

    Expression notLike(Object value);

    Expression isNull();

    Expression isNotNull();

    Expression asc();

    Expression desc();

}
