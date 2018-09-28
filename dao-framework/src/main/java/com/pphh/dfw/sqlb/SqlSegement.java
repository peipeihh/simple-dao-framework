package com.pphh.dfw.sqlb;

import com.pphh.dfw.core.sqlb.ISqlSegement;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class SqlSegement implements ISqlSegement{

    private String segment;

    public SqlSegement(String segment) {
        this.segment = segment;
    }

    @Override
    public String buildSql() {
        return this.segment;
    }
}
