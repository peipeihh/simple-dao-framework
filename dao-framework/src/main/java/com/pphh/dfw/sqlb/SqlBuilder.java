package com.pphh.dfw.sqlb;

import static com.pphh.dfw.core.sqlb.SqlConstant.*;

import com.pphh.dfw.Hints;
import com.pphh.dfw.core.dao.IDao;
import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.core.sqlb.ISqlSegement;
import com.pphh.dfw.core.table.ITable;
import com.pphh.dfw.core.table.ITableField;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class SqlBuilder implements ISqlBuilder {

    IHints hints = new Hints();
    List<ISqlSegement> sqlSegements = new LinkedList<>();

    private List<ISqlSegement> comma(ISqlSegement... segements) {
        return concat(COMMA, segements);
    }

    private List<ISqlSegement> concat(ISqlSegement separator, ISqlSegement... segements) {
        LinkedList<ISqlSegement> list = new LinkedList<>();
        for (ISqlSegement segement : segements) {
            list.add(segement);
            list.add(separator);
        }

        if (segements.length != 0) {
            list.removeLast();
        }
        return list;
    }

    @Override
    public ISqlBuilder select(ITableField... fields) {
        this.sqlSegements.clear();
        if (fields.length == 0) {
            selectAll();
        } else {
            this.append(SELECT, comma(fields));
        }
        return this;
    }

    @Override
    public ISqlBuilder selectAll() {
        this.sqlSegements.clear();
        this.append(SELECT, STAR);
        return this;
    }

    @Override
    public ISqlBuilder selectDistinct(ITableField... fields) {
        this.sqlSegements.clear();
        this.append(SELECT, DISTINCT, comma(fields));
        return this;
    }

    @Override
    public ISqlBuilder selectCount() {
        this.sqlSegements.clear();
        this.append(SELECT, COUNTALL);
        return this;
    }

    @Override
    public ISqlBuilder from(ITable... tables) {
        this.append(FROM, comma(tables));
        return this;
    }

    @Override
    public ISqlBuilder where(Object... conditions) {
        this.append(WHERE);
        return this;
    }

    @Override
    public ISqlBuilder insertInto(ITable table) {
        this.append(INSERT, INTO, table);
        return this;
    }

    @Override
    public ISqlBuilder insertInto(ITable table, ITableField... fields) {
        this.append(INSERT, INTO, LBRACKET, comma(fields), RBRACKET);
        return this;
    }

    @Override
    public ISqlBuilder values(Object... values) {
        List<ISqlSegement> segements = new LinkedList<>();
        for (Object value : values) {
            segements.add(new SqlSegement(values.toString()));
        }
        this.append(LBRACKET, comma(segements.toArray(new ISqlSegement[0])), RBRACKET);
        return this;
    }

    @Override
    public ISqlBuilder update(ITable table) {
        this.append(UPDATE, table);
        return this;
    }

    @Override
    public ISqlBuilder set(Object... sets) {
        return this;
    }

    @Override
    public ISqlBuilder deleteFrom(ITable table) {
        return this;
    }

    @Override
    public ISqlBuilder orderBy(ITableField... fields) {
        return this;
    }

    @Override
    public ISqlBuilder groupBy(ITableField... fields) {
        return this;
    }

    @Override
    public ISqlBuilder limit(int count) {
        return this;
    }

    @Override
    public ISqlBuilder limit(int start, int count) {
        return this;
    }

    @Override
    public ISqlBuilder top(int count) {
        return this;
    }

    @Override
    public ISqlBuilder offset(int start, int count) {
        return this;
    }

    @Override
    public ISqlBuilder and() {
        return this;
    }

    @Override
    public ISqlBuilder or() {
        return this;
    }

    @Override
    public ISqlBuilder not() {
        return this;
    }

    @Override
    public ISqlBuilder includeAll() {
        return this;
    }

    @Override
    public ISqlBuilder excludeAll() {
        return this;
    }

    @Override
    public ISqlBuilder join(ITable table) {
        return this;
    }

    @Override
    public ISqlBuilder innerJoin(ITable table) {
        return this;
    }

    @Override
    public ISqlBuilder leftJoin(ITable table) {
        return this;
    }

    @Override
    public ISqlBuilder rigthJoin(ITable table) {
        return this;
    }

    @Override
    public ISqlBuilder crossJoin(ITable table) {
        return this;
    }

    @Override
    public ISqlBuilder fullJoin(ITable table) {
        return this;
    }

    @Override
    public ISqlBuilder append(Object... clauses) {
        for (Object clause : clauses) {
            if (clause instanceof ISqlSegement) {
                sqlSegements.add((ISqlSegement) clause);
            } else if (clause instanceof List) {
                sqlSegements.addAll((Collection<? extends ISqlSegement>) clause);
            } else {
                sqlSegements.add(new SqlSegement(clause.toString()));
            }
        }
        return this;
    }


    @Override
    public ISqlBuilder appendWhen(Boolean isAppend, Object... clauses) {
        return null;
    }

    @Override
    public ISqlBuilder into(Class clazz) {
        return null;
    }

    @Override
    public IHints hints() {
        return null;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        int i = 1;
        for (ISqlSegement segement : sqlSegements) {
            builder.append(segement.buildSql());
            if (i++ < sqlSegements.size()) {
                builder.append(SPACE.buildSql());
            }
        }

        return builder.toString();
    }

    @Override
    public String buildOn(IDao dao) {
        return null;
    }
}
