package com.pphh.dfw.core.sqlb;

import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.dao.IDao;
import com.pphh.dfw.core.table.AbstractTableField;
import com.pphh.dfw.core.table.Expression;
import com.pphh.dfw.core.table.ITable;
import com.pphh.dfw.core.table.ITableField;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface ISqlBuilder {

    ISqlBuilder select(ITableField... fields);

    ISqlBuilder selectAll();

    ISqlBuilder selectDistinct(ITableField... fields);

    ISqlBuilder selectCount();

    ISqlBuilder from(ITable... tables);

    ISqlBuilder where(Expression... conditions);

    ISqlBuilder insertInto(ITable table);

    ISqlBuilder insertInto(ITable table, ITableField... fields);

    ISqlBuilder values(Object... values);

    ISqlBuilder update(ITable table);

    ISqlBuilder set(Expression... sets);

    ISqlBuilder deleteFrom(ITable table);

    ISqlBuilder orderBy(ITableField... fields);

    ISqlBuilder groupBy(ITableField... fields);

    ISqlBuilder limit(int count);

    ISqlBuilder limit(int start, int count);

    ISqlBuilder top(int count);

    ISqlBuilder offset(int start, int count);

    ISqlBuilder and();

    ISqlBuilder or();

    ISqlBuilder not();

    ISqlBuilder includeAll();

    ISqlBuilder excludeAll();

    ISqlBuilder join(ITable table);

    ISqlBuilder innerJoin(ITable table);

    ISqlBuilder leftJoin(ITable table);

    ISqlBuilder rigthJoin(ITable table);

    ISqlBuilder crossJoin(ITable table);

    ISqlBuilder fullJoin(ITable table);

    ISqlBuilder append(Object... clauses);

    ISqlBuilder appendWhen(Boolean isAppend, Object... clauses);

    ISqlBuilder into(Class clazz);

    IHints hints();

    String build();

    String buildOn(String logicDb);

}
