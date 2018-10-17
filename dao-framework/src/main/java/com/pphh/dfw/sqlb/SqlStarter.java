package com.pphh.dfw.sqlb;

import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.core.table.ITable;
import com.pphh.dfw.core.table.ITableField;

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
        ISqlBuilder sqlb = new SqlBuilder();
        return sqlb.select(fields);
    }

    public static ISqlBuilder selectAll() {
        ISqlBuilder sqlb = new SqlBuilder();
        return sqlb.selectAll();
    }

    public static ISqlBuilder selectDistinct(ITableField... fields) {
        ISqlBuilder sqlb = new SqlBuilder();
        return sqlb.selectDistinct(fields);
    }

    public static ISqlBuilder selectCount() {
        ISqlBuilder sqlb = new SqlBuilder();
        return sqlb.selectCount();
    }

    public static ISqlBuilder insertInto(ITable table) {
        ISqlBuilder sqlb = new SqlBuilder();
        return sqlb.insertInto(table);
    }

    public static ISqlBuilder insertInto(ITable table, ITableField... fields) {
        ISqlBuilder sqlb = new SqlBuilder();
        return sqlb.insertInto(table, fields);
    }

    public static ISqlBuilder update(ITable table) {
        ISqlBuilder sqlb = new SqlBuilder();
        return sqlb.update(table);
    }

    public static ISqlBuilder deleteFrom(ITable table) {
        ISqlBuilder sqlb = new SqlBuilder();
        return sqlb.deleteFrom(table);
    }

}
