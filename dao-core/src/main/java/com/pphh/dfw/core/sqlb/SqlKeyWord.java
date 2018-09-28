package com.pphh.dfw.core.sqlb;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class SqlKeyWord implements ISqlSegement {

    private String key;

    public SqlKeyWord(String key) {
        this.key = key;
    }

    @Override
    public String buildSql() {
        return this.key;
    }

}
