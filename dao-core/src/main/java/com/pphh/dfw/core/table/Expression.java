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

    public Expression(String expression) {
        this.expression = expression;
    }

    @Override
    public String buildSql() {
        return expression;
    }

}
