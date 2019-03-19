package com.pphh.dfw;

import com.pphh.dfw.core.*;
import com.pphh.dfw.core.constant.HintEnum;
import com.pphh.dfw.core.dao.IBatchSqlBuilder;
import com.pphh.dfw.core.dao.IDao;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.exception.DfwException;
import com.pphh.dfw.core.function.DfwFunction;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.core.sqlb.ISqlSegement;
import com.pphh.dfw.core.table.Expression;
import com.pphh.dfw.core.table.ITableField;
import com.pphh.dfw.core.transform.Task;
import com.pphh.dfw.core.transform.TaskFactory;
import com.pphh.dfw.core.transform.TaskResult;
import com.pphh.dfw.shard.ShardManager;
import com.pphh.dfw.sqlb.SqlBuilder;
import com.pphh.dfw.table.GenericTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pphh.dfw.core.sqlb.SqlConstant.*;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class Dao implements IDao {

    private final static Logger log = LoggerFactory.getLogger(Dao.class);

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
        if (primaryKey == null || primaryKey.getFieldDefinition() == null && primaryKey.getFieldDefinition().isEmpty()) {
            throw new RuntimeException("Sorry, primary key is missing in the table definition");
        } else if (primaryKey.getFieldValue() == null) {
            throw new RuntimeException("Sorry, primary key has empty value in the entity. Please input a entity with primary key specified.");
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
    public <T extends IEntity> List<T> queryAll(Long limit) throws Exception {
        return queryAll(limit, new Hints());
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

        return this.count(sqlBuilder);
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
        return insert(entities, null);
    }

    @Override
    public <T extends IEntity> int[] insert(List<T> entities, IHints hints) throws Exception {
        List<ISqlBuilder> sqlBuilders = new ArrayList<>();
        for (T entity : entities) {
            GenericTable table = this.entityParser.parse((IEntity) entity);
            if (table == null) {
                throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
            }

            // 获取entity的各个字段定义，生成sql
            ISqlBuilder sqlBuilder = new SqlBuilder();
            sqlBuilder.hints(getShardHints(table));
            sqlBuilder.appendHints(hints);
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

            sqlBuilders.add(sqlBuilder);
        }

        return executeBatchUpdate(sqlBuilders);
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
        if (primaryKey == null || primaryKey.getFieldDefinition() == null && primaryKey.getFieldDefinition().isEmpty()) {
            throw new RuntimeException("Sorry, primary key is missing in the table definition");
        } else if (primaryKey.getFieldValue() == null) {
            throw new RuntimeException("Sorry, primary key has empty value in the entity. Please input a entity with primary key specified.");
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
    public <T extends IEntity> int[] delete(List<T> entitiesWithPk) throws Exception {
        return delete(entitiesWithPk, null);
    }

    @Override
    public <T extends IEntity> int[] delete(List<T> entitiesWithPk, IHints hints) throws Exception {
        List<ISqlBuilder> sqlBuilders = new ArrayList<>();
        for (T entity : entitiesWithPk) {
            GenericTable table = this.entityParser.parse((IEntity) entity);
            if (table == null) {
                throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
            }

            // 获取entity的各个字段定义，生成sql
            ISqlBuilder sqlBuilder = new SqlBuilder();
            sqlBuilder.hints(getShardHints(table));
            sqlBuilder.appendHints(hints);
            sqlBuilder.into((Class<? extends T>) entity.getClass());

            ITableField primaryKey = table.getPkField();
            if (primaryKey == null || primaryKey.getFieldDefinition() == null && primaryKey.getFieldDefinition().isEmpty()) {
                throw new RuntimeException("Sorry, primary key is missing in the table definition");
            } else if (primaryKey.getFieldValue() == null) {
                throw new RuntimeException("Sorry, primary key has empty value in the entity. Please input a entity with primary key specified.");
            }

            Expression condition = new Expression(String.format("`%s` = '%s'", primaryKey.getFieldDefinition(), primaryKey.getFieldValue()));
            sqlBuilder.deleteFrom(table).where(condition);

            sqlBuilders.add(sqlBuilder);
        }

        return executeBatchUpdate(sqlBuilders);
    }

    @Override
    public <T extends IEntity> int[] deleteBySample(List<T> samples) throws Exception {
        return deleteBySample(samples, null);
    }

    @Override
    public <T extends IEntity> int[] deleteBySample(List<T> samples, IHints hints) throws Exception {
        List<ISqlBuilder> sqlBuilders = new ArrayList<>();
        for (T entity : samples) {
            GenericTable table = this.entityParser.parse((IEntity) entity);
            if (table == null) {
                throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
            }

            // 获取entity的各个字段定义，生成sql
            ISqlBuilder sqlBuilder = new SqlBuilder();
            sqlBuilder.hints(getShardHints(table));
            sqlBuilder.appendHints(hints);
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

            sqlBuilders.add(sqlBuilder);
        }

        return executeBatchUpdate(sqlBuilders);
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
        if (primaryKey == null || primaryKey.getFieldDefinition() == null && primaryKey.getFieldDefinition().isEmpty()) {
            throw new RuntimeException("Sorry, primary key is missing in the table definition");
        } else if (primaryKey.getFieldValue() == null) {
            throw new RuntimeException("Sorry, primary key has empty value in the entity. Please input a entity with primary key specified.");
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
    public <T extends IEntity> int[] update(List<T> entitiesWithPk) throws Exception {
        return update(entitiesWithPk, null);
    }

    @Override
    public <T extends IEntity> int[] update(List<T> entitiesWithPk, IHints hints) throws Exception {
        List<ISqlBuilder> sqlBuilders = new ArrayList<>();
        for (T entityWithPk : entitiesWithPk) {
            GenericTable table = this.entityParser.parse((IEntity) entityWithPk);
            if (table == null) {
                throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
            }

            // 获取entity的各个字段定义，生成sql
            ISqlBuilder sqlBuilder = new SqlBuilder();
            sqlBuilder.hints(getShardHints(table));
            sqlBuilder.appendHints(hints);
            sqlBuilder.into((Class<? extends T>) entityWithPk.getClass());

            ITableField primaryKey = table.getPkField();
            if (primaryKey == null || primaryKey.getFieldDefinition() == null && primaryKey.getFieldDefinition().isEmpty()) {
                throw new RuntimeException("Sorry, primary key is missing in the table definition");
            } else if (primaryKey.getFieldValue() == null) {
                throw new RuntimeException("Sorry, primary key has empty value in the entity. Please input a entity with primary key specified.");
            }

            List<ITableField> fields = table.getFields();
            List<ISqlSegement> expressions = new ArrayList<>();
            for (ITableField field : fields) {
                if (field.getFieldValue() != null) {
                    expressions.add(field.equal(field.getFieldValue()));
                }
            }
            sqlBuilder.update(table).set(expressions.toArray(new ISqlSegement[expressions.size()])).where(primaryKey.equal(primaryKey.getFieldValue()));

            sqlBuilders.add(sqlBuilder);
        }

        return executeBatchUpdate(sqlBuilders);
    }

    @Override
    public <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder) throws Exception {
        return queryForObject(sqlBuilder, sqlBuilder.getHints());
    }

    @Override
    public <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder, IHints hints) throws Exception {
        sqlBuilder.appendHints(hints);
        String sql = sqlBuilder.buildOn(this);
        DfwSql dfwSql = parse(sql);
        Class<? extends T> pojoClz = (Class<? extends T>) sqlBuilder.getHints().getHintValue(HintEnum.POJO_CLASS);
        Task task = TaskFactory.getInstance().getQueryTask(dfwSql.getSql(), dfwSql.getDb(), pojoClz);
        TaskResult taskResult = transformer.run(task);
        return (T) taskResult.getFirstEntity();
    }

    @Override
    public <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder) throws Exception {
        return queryForList(sqlBuilder, sqlBuilder.getHints());
    }

    @Override
    public <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder, IHints hints) throws Exception {
        sqlBuilder.appendHints(hints);
        String sql = sqlBuilder.buildOn(this);
        log.debug(sql);
        DfwSql dfwSql = parse(sql);
        Class<? extends T> pojoClz = (Class<? extends T>) sqlBuilder.getHints().getHintValue(HintEnum.POJO_CLASS);
        Task task = TaskFactory.getInstance().getQueryTask(dfwSql.getSql(), dfwSql.getDb(), pojoClz);
        TaskResult taskResult = transformer.run(task);
        return taskResult.getEntities();
    }

    @Override
    public int count(ISqlBuilder sqlBuilder) throws Exception {
        return count(sqlBuilder, null);
    }

    public int count(ISqlBuilder sqlBuilder, IHints hints) throws Exception {
        sqlBuilder.appendHints(hints);
        String sql = sqlBuilder.buildOn(this);
        log.debug(sql);
        DfwSql dfwSql = parse(sql);
        Task task = TaskFactory.getInstance().getCountTask(dfwSql.getSql(), dfwSql.getDb());
        TaskResult taskResult = transformer.run(task);
        return taskResult.getCount();
    }

    @Override
    public int execute(DfwFunction function) throws Exception {
        return Transactioner.getInstance().execute(function);
    }

    @Override
    public int execute(DfwFunction function, IHints dbHints) throws Exception {
        // 加载逻辑数据库配置
        String dbName = ShardManager.getDbNameByHints(this.logicDbName, dbHints);
        return Transactioner.getInstance().execute(function, dbName);
    }

    @Override
    public int run(ISqlBuilder updateSqlBuilder) throws Exception {
        return run(updateSqlBuilder, null);
    }

    @Override
    public int run(ISqlBuilder updateSqlBuilder, IHints hints) throws Exception {
        updateSqlBuilder.appendHints(hints);
        return executeUpdate(updateSqlBuilder);
    }

    @Override
    public int[] run(IBatchSqlBuilder updateSqlBuilder) throws Exception {
        return new int[0];
    }

    @Override
    public int[] run(IBatchSqlBuilder updateSqlBuilder, IHints hints) throws Exception {
        return new int[0];
    }

    @Override
    public String getLogicName() {
        return this.logicDbName;
    }

    private IHints getShardHints(GenericTable table) {
        IHints hints = new Hints();

        // 加载逻辑数据库配置
        IDataSourceConfig dsConfig = GlobalDataSourceConfig.getInstance().load();

        // 根据逻辑数据库定义，获取分库分表字段
        LogicDBConfig logicDBConfig = dsConfig.getLogicDBConfig(this.logicDbName);
        if (logicDBConfig != null) {
            hints.sqlDialect(logicDBConfig.getDefaultDriver());

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

    private int[] executeBatchUpdate(List<ISqlBuilder> sqlBuilders) throws Exception {
        int[] results = new int[sqlBuilders.size()];

        // 按dbName对sqls进行分类，以便下一步归类执行
        Integer index = 0;
        Map<String, Map<Integer, String>> sqlsMap = new HashMap<>();
        for (ISqlBuilder sqlBuilder : sqlBuilders) {
            String sql = sqlBuilder.buildOn(this);
            DfwSql dfwSql = parse(sql);

            String dbName = dfwSql.getDb();
            if (!sqlsMap.containsKey(dbName)) {
                sqlsMap.put(dbName, new HashMap<>());
            }

            Map<Integer, String> sqls = sqlsMap.get(dbName);
            sqls.put(index++, dfwSql.getSql());
        }

        // 按dbName对sqls进行归类执行，相同db合并在一起执行
        for (Map.Entry<String, Map<Integer, String>> dbShardEntry : sqlsMap.entrySet()) {
            String dbName = dbShardEntry.getKey();
            Map<Integer, String> sqls = dbShardEntry.getValue();

            List<Integer> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();

            for (Map.Entry<Integer, String> sqlEntry : sqls.entrySet()) {
                keys.add(sqlEntry.getKey());
                values.add(sqlEntry.getValue());
            }

            Task task = TaskFactory.getInstance().getBatchUpdateTask(values, dbName, null);
            TaskResult taskResult = transformer.run(task);
            int[] dbShardResults = taskResult.getResults();

            // sqls语句执行的结果数目必须和sqls数目保持一致，以便下一步按序收集
            if (dbShardResults.length != values.size()) {
                throw new Exception(String.format("the length of batch update results is incorrect. %s != %s",
                        dbShardResults.length,
                        values.size()));
            }

            for (int i = 0; i < values.size(); i++) {
                Integer indexR = keys.get(i);
                results[indexR] = dbShardResults[i];
            }

        }

        return results;
    }

    private int executeUpdate(ISqlBuilder singleSqlBuilder) throws Exception {
        String sql = singleSqlBuilder.buildOn(this);
        log.debug(sql);
        DfwSql dfwSql = parse(sql);
        Task task = TaskFactory.getInstance().getUpdateTask(dfwSql.getSql(), dfwSql.getDb(), null);
        TaskResult tResult = transformer.run(task);
        return tResult.getResult();
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
