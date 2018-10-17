package com.pphh.dfw.sqlb;

import static com.pphh.dfw.core.sqlb.SqlConstant.*;

import com.pphh.dfw.GlobalDataSourceConfig;
import com.pphh.dfw.Hints;
import com.pphh.dfw.core.ShardStrategy;
import com.pphh.dfw.core.constant.HintEnum;
import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.dao.IDao;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.core.sqlb.ISqlSegement;
import com.pphh.dfw.core.table.Expression;
import com.pphh.dfw.core.table.ITable;
import com.pphh.dfw.core.table.ITableField;
import com.pphh.dfw.table.GenericTable;

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

    private IHints hints = new Hints();
    private List<ISqlSegement> sqlSegements = new LinkedList<>();

    public SqlBuilder() {
    }

    protected List<ISqlSegement> comma(ISqlSegement... segements) {
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
        if (fields.length == 0) {
            selectAll();
        } else {
            this.append(SELECT, comma(fields));
        }
        return this;
    }

    @Override
    public ISqlBuilder selectAll() {
        this.append(SELECT, STAR);
        return this;
    }

    @Override
    public ISqlBuilder selectDistinct(ITableField... fields) {
        this.append(SELECT, DISTINCT, comma(fields));
        return this;
    }

    @Override
    public ISqlBuilder selectCount() {
        this.append(SELECT, COUNTALL);
        return this;
    }

    @Override
    public ISqlBuilder from(ITable... tables) {
        this.append(FROM, comma(tables));
        return this;
    }

    @Override
    public ISqlBuilder where(Expression... conditions) {
        this.append(WHERE, comma(conditions));
        return this;
    }

    @Override
    public ISqlBuilder insertInto(ITable table) {
        this.append(INSERT, INTO, table);
        return this;
    }

    @Override
    public ISqlBuilder insertInto(ITable table, ITableField... fields) {
        this.append(INSERT, INTO, table, LBRACKET, comma(fields), RBRACKET);
        return this;
    }

    @Override
    public ISqlBuilder values(Object... values) {
        List<ISqlSegement> segements = new LinkedList<>();
        for (Object value : values) {
            String formattedValue = String.format("'%s'", value);
            segements.add(new SqlSegement(formattedValue));
        }
        this.append(VALUES, LBRACKET, comma(segements.toArray(new ISqlSegement[0])), RBRACKET);
        return this;
    }

    @Override
    public ISqlBuilder update(ITable table) {
        this.append(UPDATE, table);
        return this;
    }

    @Override
    public ISqlBuilder set(Expression... sets) {
        this.append(SET, comma(sets));
        return this;
    }

    @Override
    public ISqlBuilder deleteFrom(ITable table) {
        this.append(DELETE, FROM, table);
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
    public ISqlBuilder hints(IHints hints) {
        this.hints = hints;
        return this;
    }

    @Override
    public ISqlBuilder into(Class clazz) {
        this.hints.into(clazz);
        return this;
    }

    @Override
    public IHints getHints() {
        return this.hints;
    }

    @Override
    public String build() {
        return build(this.sqlSegements);
    }

    @Override
    public String buildOn(IDao dao) {
        return buildOn(dao.getLogicName());
    }

    @Override
    public <T> List<T> fetchInto(Class<? extends T> clazz) throws Exception {
        return null;
    }

    @Override
    public int execute() throws Exception {
        return 0;
    }

    private String buildOn(String logicDb) {
        // 加载逻辑数据库配置
        String tableShard = null;
        String dbShard = null;
        IDataSourceConfig dsConfig = null;
        try {
            dsConfig = GlobalDataSourceConfig.getInstance().load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据逻辑数据库定义，计算分库分表
        Object dbShardId = hints.getHintValue(HintEnum.DB_SHARD);
        Object tableShardId = hints.getHintValue(HintEnum.TABLE_SHARD);
        Object dbShardValue = hints.getHintValue(HintEnum.DB_SHARD_VALUE);
        Object tableShardValue = hints.getHintValue(HintEnum.TABLE_SHARD_VALUE);

        if (dsConfig != null) {
            LogicDBConfig logicDBConfig = dsConfig.getLogicDBConfig(logicDb);
            ShardStrategy strategy = logicDBConfig.getShardStrategy();
            if (strategy != null) {

                if (logicDBConfig.getDbShardColumn() != null && !logicDBConfig.getDbShardColumn().isEmpty()) {
                    if (dbShardId != null) {
                        dbShard = strategy.locateDbShard(dbShardId.toString(), Boolean.FALSE);
                    } else {
                        dbShard = strategy.locateDbShard(dbShardValue.toString(), Boolean.TRUE);
                    }
                }

                if (logicDBConfig.getTableShardColumn() != null && !logicDBConfig.getTableShardColumn().isEmpty()) {
                    if (tableShardId != null) {
                        tableShard = strategy.locateTableShard(tableShardId.toString(), Boolean.FALSE);
                    } else {
                        tableShard = strategy.locateTableShard(tableShardValue.toString(), Boolean.TRUE);
                    }
                }

            }
        }

        // 切换分库分表之后的sql拼接字段
        List<ISqlSegement> shardSqlSegements = new LinkedList<>();

        if (tableShard != null) {
            for (ISqlSegement segement : sqlSegements) {
                ISqlSegement seg = segement;
                if (segement instanceof ITable) {
                    seg = new GenericTable(tableShard);
                }

                shardSqlSegements.add(seg);
            }
        } else {
            for (ISqlSegement segement : sqlSegements) {
                shardSqlSegements.add(segement);
            }
        }

        if (dbShard != null) {
            shardSqlSegements.add(new Expression("-- " + dbShard));
        }

        return build(shardSqlSegements);
    }

    private String build(List<ISqlSegement> segements) {
        StringBuilder builder = new StringBuilder();

        int i = 1;
        for (ISqlSegement segement : segements) {
            builder.append(segement.buildSql());
            if (i++ < segements.size()) {
                builder.append(SPACE.buildSql());
            }
        }

        return builder.toString();
    }
}
