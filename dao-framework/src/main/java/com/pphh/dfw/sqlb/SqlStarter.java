package com.pphh.dfw.sqlb;

import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.core.table.ITable;
import com.pphh.dfw.core.table.ITableField;

import static com.pphh.dfw.core.sqlb.SqlConstant.*;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/17/2018
 */
public class SqlStarter {

    public static ISqlBuilder sqlBuilder() {
        return new SqlBuilder();
    }

    public static ISqlBuilder select(ITableField... fields) {
        SqlBuilder sqlb = new SqlBuilder();
        if (fields.length == 0) {
            sqlb.selectAll();
        } else {
            sqlb.append(SELECT, sqlb.comma(fields));
        }
        return sqlb;
    }

    public static ISqlBuilder selectAll() {
        SqlBuilder sqlb = new SqlBuilder();
        sqlb.append(SELECT, STAR);
        return sqlb;
    }

    public static ISqlBuilder selectDistinct(ITableField... fields) {
        SqlBuilder sqlb = new SqlBuilder();
        sqlb.append(SELECT, DISTINCT, sqlb.comma(fields));
        return sqlb;
    }

    public static ISqlBuilder selectCount() {
        SqlBuilder sqlb = new SqlBuilder();
        sqlb.append(SELECT, COUNTALL);
        return sqlb;
    }

    public static ISqlBuilder insertInto(ITable table) {
        SqlBuilder sqlb = new SqlBuilder();
        sqlb.append(INSERT, INTO, table);
        return sqlb;
    }

    public static ISqlBuilder insertInto(ITable table, ITableField... fields) {
        SqlBuilder sqlb = new SqlBuilder();
        sqlb.append(INSERT, INTO, table, LBRACKET, sqlb.comma(fields), RBRACKET);
        return sqlb;
    }

    public static ISqlBuilder update(ITable table) {
        SqlBuilder sqlb = new SqlBuilder();
        sqlb.append(UPDATE, table);
        return sqlb;
    }

    public static ISqlBuilder deleteFrom(ITable table) {
        SqlBuilder sqlb = new SqlBuilder();
        sqlb.append(DELETE, FROM, table);
        return sqlb;
    }

}
