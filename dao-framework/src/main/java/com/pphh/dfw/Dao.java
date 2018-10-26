package com.pphh.dfw;

import com.pphh.dfw.core.*;
import com.pphh.dfw.core.constant.HintEnum;
import com.pphh.dfw.core.dao.IBatchSqlBuilder;
import com.pphh.dfw.core.dao.IDao;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.core.sqlb.ISqlSegement;
import com.pphh.dfw.core.table.Expression;
import com.pphh.dfw.core.table.ITableField;
import com.pphh.dfw.sqlb.SqlBuilder;
import com.pphh.dfw.table.GenericTable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.pphh.dfw.core.sqlb.SqlConstant.*;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class Dao implements IDao {

    private final String logicDbName;
    private EntityParser entityParser;
    private Transformer transformer = new Transformer();

    public Dao(String logicDbName) {
        this.logicDbName = logicDbName;
        this.entityParser = new EntityParser();
    }


    @Override
    public <T> T query(T entityWithPk) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entityWithPk);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        return query(entityWithPk, getShardHints(table));
    }

    @Override
    public <T> T query(T entityWithPk, IHints hints) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entityWithPk);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取主键信息
        ITableField primaryKey = table.getPkField();
        if (primaryKey == null || primaryKey.getFieldDefinition() == null || primaryKey.getFieldDefinition().isEmpty()) {
            throw new RuntimeException("Sorry, primary key is missing in the table definition");
        }

        Object value = primaryKey.getFieldValue();
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(hints);
        sqlBuilder.select().from(table).where(primaryKey.equal(value));
        sqlBuilder.into((Class<? extends T>) entityWithPk.getClass());

        List<T> results = (List<T>) this.queryForList(sqlBuilder);
        T result = null;
        if (results.size() > 0) {
            result = results.get(0);
        }
        return result;
    }

    @Override
    public <T extends IEntity> List<T> queryBySample(T entity) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        return queryBySample(entity, getShardHints(table));
    }

    @Override
    public <T extends IEntity> List<T> queryBySample(T entity, IHints hints) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(hints);
        sqlBuilder.into((Class<? extends T>) entity.getClass());

        List<ISqlSegement> conditions = new ArrayList<>();
        List<ITableField> fields = table.getFields();
        for (ITableField field : fields) {
            if (field.getFieldValue() != null) {
                ISqlSegement condition = field.equal(field.getFieldValue());
                conditions.add(condition);
                conditions.add(AND);
            }
        }
        if (conditions.size() > 0) {
            conditions.remove(conditions.size() - 1);
        }
        sqlBuilder.select().from(table).where(conditions.toArray(new ISqlSegement[conditions.size()]));

        return (List<T>) this.queryForList(sqlBuilder);
    }

    @Override
    public <T extends IEntity> List<T> queryAll(Long limit) {
        return null;
    }

    @Override
    public <T extends IEntity> List<T> queryAll(Long limit, IHints hints) throws Exception {
        return null;
    }

    @Override
    public <T extends IEntity> long countBySample(T entity) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        return countBySample(entity, getShardHints(table));
    }

    @Override
    public <T extends IEntity> long countBySample(T entity, IHints hints) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(hints);
        sqlBuilder.into((Class<? extends T>) entity.getClass());

        List<ISqlSegement> conditions = new ArrayList<>();
        List<ITableField> fields = table.getFields();
        for (ITableField field : fields) {
            if (field.getFieldValue() != null) {
                ISqlSegement condition = field.equal(field.getFieldValue());
                conditions.add(condition);
                conditions.add(AND);
            }
        }
        if (conditions.size() > 0) {
            conditions.remove(conditions.size() - 1);
        }
        sqlBuilder.selectCount().from(table).where(conditions.toArray(new ISqlSegement[conditions.size()]));

        // FIXME fetch the count of sql query
        List<T> results = this.queryForList(sqlBuilder);
        return results.size();
    }

    @Override
    public <T extends IEntity> int insert(T entity) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        return insert(entity, getShardHints(table));
    }

    @Override
    public <T extends IEntity> int insert(T entity, IHints hints) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(hints);
        sqlBuilder.into((Class<? extends T>) entity.getClass());

        List<ITableField> fields = table.getFields();
        List<ITableField> definitions = new ArrayList<>();
        List<ISqlSegement> values = new ArrayList<>();
        for (ITableField field : fields) {
            if (field.getFieldValue() != null) {
                definitions.add(field);
                values.add(new Expression(String.format("'%s'", field.getFieldValue())));
            }
        }

        sqlBuilder.insertInto(table, definitions.toArray(new ITableField[definitions.size()]))
                .values(values.toArray(new Expression[values.size()]));

        return this.executeUpdate(sqlBuilder);
    }

    @Override
    public <T extends IEntity> int[] insert(List<T> entities) throws Exception {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int[] insert(List<T> entities, IHints hints) throws Exception {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int delete(T entityWithPk) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entityWithPk);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        return delete(entityWithPk, getShardHints(table));
    }

    @Override
    public <T extends IEntity> int delete(T entityWithPk, IHints hints) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entityWithPk);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(hints);
        sqlBuilder.into((Class<? extends T>) entityWithPk.getClass());

        ITableField primaryKey = table.getPkField();
        if (primaryKey == null || primaryKey.getFieldDefinition() == null || primaryKey.getFieldDefinition().isEmpty()) {
            throw new RuntimeException("Sorry, primary key is missing in the table definition");
        }

        Expression condition = new Expression(String.format("`%s` = '%s'", primaryKey.getFieldDefinition(), primaryKey.getFieldValue()));
        sqlBuilder.deleteFrom(table).where(condition);

        return this.executeUpdate(sqlBuilder);
    }

    @Override
    public <T extends IEntity> int deleteBySample(T entity) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        return deleteBySample(entity, getShardHints(table));
    }

    @Override
    public <T extends IEntity> int deleteBySample(T entity, IHints hints) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(hints);
        sqlBuilder.into((Class<? extends T>) entity.getClass());

        List<ITableField> fields = table.getFields();
        List<ISqlSegement> conditions = new ArrayList<>();
        for (ITableField field : fields) {
            if (field.getFieldValue() != null) {
                conditions.add(field.equal(field.getFieldValue()));
                conditions.add(AND);
            }
        }
        if (conditions.size() > 0) {
            conditions.remove(conditions.size() - 1);
        }

        sqlBuilder.deleteFrom(table).where(conditions.toArray(new ISqlSegement[conditions.size()]));
        return this.executeUpdate(sqlBuilder);
    }

    @Override
    public <T extends IEntity> int[] delete(List<T> entities) throws Exception {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int[] delete(List<T> entities, IHints hints) throws Exception {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int update(T entityWithPk) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entityWithPk);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        return update(entityWithPk, getShardHints(table));
    }

    @Override
    public <T extends IEntity> int update(T entityWithPk, IHints hints) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entityWithPk);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(hints);
        sqlBuilder.into((Class<? extends T>) entityWithPk.getClass());

        ITableField primaryKey = table.getPkField();
        if (primaryKey == null || primaryKey.getFieldDefinition() == null || primaryKey.getFieldDefinition().isEmpty()) {
            throw new RuntimeException("Sorry, primary key is missing in the table definition");
        }

        List<ITableField> fields = table.getFields();
        List<ISqlSegement> expressions = new ArrayList<>();
        for (ITableField field : fields) {
            if (field.getFieldValue() != null) {
                expressions.add(field.equal(field.getFieldValue()));
            }
        }

        sqlBuilder.update(table).set(expressions.toArray(new ISqlSegement[expressions.size()])).where(primaryKey.equal(primaryKey.getFieldValue()));
        return this.executeUpdate(sqlBuilder);
    }

    @Override
    public <T extends IEntity> int[] update(List<T> entities) throws Exception {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int[] update(List<T> entities, IHints hints) throws Exception {
        return new int[0];
    }

    @Override
    public <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder) throws Exception {
        return null;
    }

    @Override
    public <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder, IHints hints) throws Exception {
        return null;
    }

    @Override
    public <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder) throws Exception {
        String sql = sqlBuilder.buildOn(this);
        System.out.println(sql);
        DfwSql dfwSql = parse(sql);
        Class<? extends T> pojoClz = (Class<? extends T>) sqlBuilder.getHints().getHintValue(HintEnum.POJO_CLASS);
        return transformer.run(dfwSql.getSql(), dfwSql.getDb(), pojoClz);
    }

    @Override
    public <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder, IHints hints) throws Exception {
        return null;
    }

    @Override
    public int execute(Function function) throws Exception {
        return 0;
    }

    @Override
    public int execute(Function function, IHints hints) throws Exception {
        return 0;
    }

    @Override
    public int run(ISqlBuilder sqlBuilder) throws Exception {
        String sql = sqlBuilder.buildOn(this);
        System.out.println(sql);
        DfwSql dfwSql = parse(sql);
        return transformer.run(dfwSql.getSql(), dfwSql.getDb());
    }

    @Override
    public int run(ISqlBuilder sqlBuilder, IHints hints) throws Exception {
        return 0;
    }

    @Override
    public int[] run(IBatchSqlBuilder sqlBuilder) throws Exception {
        return new int[0];
    }

    @Override
    public int[] run(IBatchSqlBuilder sqlBuilder, IHints hints) throws Exception {
        return new int[0];
    }

    @Override
    public String getLogicName() {
        return this.logicDbName;
    }

    private IHints getShardHints(GenericTable table) {
        IHints hints = new Hints();

        // 加载逻辑数据库配置
        IDataSourceConfig dsConfig = null;
        try {
            dsConfig = GlobalDataSourceConfig.getInstance().load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据逻辑数据库定义，获取分库分表字段
        if (dsConfig != null) {
            LogicDBConfig logicDBConfig = dsConfig.getLogicDBConfig(logicDbName);
            ShardStrategy strategy = logicDBConfig.getShardStrategy();
            if (strategy != null) {

                if (logicDBConfig.getDbShardColumn() != null && !logicDBConfig.getDbShardColumn().isEmpty()) {
                    String dbShardColumn = logicDBConfig.getDbShardColumn();
                    Object dbShard = table.getFieldValue(dbShardColumn);
                    hints.dbShardValue(dbShard);
                }

                if (logicDBConfig.getTableShardColumn() != null && !logicDBConfig.getTableShardColumn().isEmpty()) {
                    String tableShardColumn = logicDBConfig.getTableShardColumn();
                    Object tableShard = table.getFieldValue(tableShardColumn);
                    hints.tableShardValue(tableShard);
                }

            }
        }

        return hints;
    }

    private int executeUpdate(ISqlBuilder sqlBuilder) throws Exception {
        String sql = sqlBuilder.buildOn(this);
        System.out.println(sql);
        DfwSql dfwSql = parse(sql);
        return transformer.run(dfwSql.getSql(), dfwSql.getDb());
    }

    private DfwSql parse(String sql) {
        String statement;
        String dbName;

        LogicDBConfig logicDBConfig = GlobalDataSourceConfig.getInstance().getLogicDBConfig(this.logicDbName);
        if (sql.contains("--")) {
            String[] strArr = sql.split("--");
            statement = strArr[0];
            String dbShard = strArr[1].trim();
            dbName = logicDBConfig.getPhysicalDbName(dbShard);
        } else {
            statement = sql;
            dbName = logicDBConfig.getDefaultPhysicalDbName();
        }

        return new DfwSql(statement, dbName);
    }
}
