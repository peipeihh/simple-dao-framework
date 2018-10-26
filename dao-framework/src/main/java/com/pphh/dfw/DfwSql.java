package com.pphh.dfw;

import com.pphh.dfw.core.ISql;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/25/2018
 */
public class DfwSql implements ISql {

    private String sql;
    private String dbName;

    public DfwSql(String sql, String dbName) {
        this.sql = sql;
        this.dbName = dbName;
    }

    @Override
    public String getSql() {
        return this.sql;
    }

    @Override
    public String getDb() {
        return this.dbName;
    }

}
