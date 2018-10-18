package com.pphh.dfw.core.table;

import com.pphh.dfw.core.sqlb.ISqlSegement;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class Expression implements ISqlSegement {

    private String expression;
    private Boolean isSkipByNullValue = Boolean.TRUE;

    public Expression(String expression) {
        this.expression = expression;
    }

    public Expression nullable() {
        isSkipByNullValue = Boolean.FALSE;
        return this;
    }

    public Boolean isNullable() {
        return isSkipByNullValue;
    }

    @Override
    public String buildSql() {
        return expression;
    }

}
