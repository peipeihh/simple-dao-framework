package com.pphh.dfw.core.table;

import com.pphh.dfw.core.sqlb.ISqlSegement;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface ITableField extends ISqlSegement {

    String getFieldName();

    Expression equal(Object value);

    Expression notEqual(Object value);

    Expression greaterThan(Object value);

    Expression greaterThanOrEqual(Object value);

    Expression lessThan(Object value);

    Expression between(Object firstValue, Object secondValue);

    Expression notBetween(Object firstValue, Object secondValue);

    Expression in(Object value);

    Expression notIn(Object value);

    Expression like(Object value);

    Expression notLike(Object value);

    Expression isNull(Object value);

    Expression isNotNull(Object value);

}
