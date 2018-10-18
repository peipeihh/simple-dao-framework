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

    private IHints hints = new Hints();
    private String logicDbName;
    private EntityParser entityParser;
    private Transformer transformer = new Transformer();

    public Dao(String logicDbName) {
        this.logicDbName = logicDbName;
        this.entityParser = new EntityParser();
    }

    @Override
    public <T> T queryByPk(T entity) throws Exception {
        // parse entity, 获取entity definition
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取主键信息
        ITableField primaryKey = table.getPkField();
        Object value = primaryKey.getFieldValue();
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(this.hints);
        sqlBuilder.select().from(table).where(primaryKey.equal(value));
        setShard(sqlBuilder, table);
        sqlBuilder.into((Class<? extends T>) entity.getClass());

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

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(this.hints);
        setShard(sqlBuilder, table);
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
    public <T extends IEntity> long countBySample(T entity) throws Exception {
        GenericTable table = this.entityParser.parse((IEntity) entity);
        if (table == null) {
            throw new RuntimeException("Sorry, failed to parse table definition by entity object. Please input correct entity object.");
        }

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(this.hints);
        setShard(sqlBuilder, table);
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

        // 获取entity的各个字段定义，生成sql
        ISqlBuilder sqlBuilder = new SqlBuilder().hints(this.hints);
        setShard(sqlBuilder, table);
        sqlBuilder.into((Class<? extends T>) entity.getClass());

        List<ITableField> definitions = new ArrayList<>();
        List<ISqlSegement> values = new ArrayList<>();

        List<ITableField> fields = table.getFields();
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
    public <T extends IEntity> int[] insert(List<T> entities) {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int delete(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int deleteBySample(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int[] delete(List<T> entities) {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int update(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int[] update(List<T> entities) {
        return new int[0];
    }

    @Override
    public <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder) throws Exception {
        return null;
    }

    public int executeUpdate(ISqlBuilder sqlBuilder) throws Exception {
        String sql = sqlBuilder.buildOn(this);
        System.out.println(sql);
        String[] sqlInfo = parse(sql);
        return transformer.run(sqlInfo[0], sqlInfo[1]);
    }

    @Override
    public <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder) throws Exception {
        String sql = sqlBuilder.buildOn(this);
        System.out.println(sql);
        String[] sqlInfo = parse(sql);

        Class<? extends T> pojoClz = (Class<? extends T>) sqlBuilder.getHints().getHintValue(HintEnum.POJO_CLASS);
        return transformer.run(sqlInfo[0], sqlInfo[1], pojoClz);
    }

    @Override
    public int execute(Function function) {
        return 0;
    }

    @Override
    public int run(ISqlBuilder sqlBuilder) throws Exception {
        String sql = sqlBuilder.buildOn(this);
        System.out.println(sql);
        String[] sqlInfo = parse(sql);

        return transformer.run(sqlInfo[0], sqlInfo[1]);
    }

    private String[] parse(String sql) {
        String sqlStatement;
        String dbName;
        LogicDBConfig logicDBConfig = GlobalDataSourceConfig.getInstance().getLogicDBConfig(this.logicDbName);
        if (sql.contains("--")) {
            String[] strArr = sql.split("--");
            sqlStatement = strArr[0];
            String dbShard = strArr[1].trim();
            dbName = logicDBConfig.getPhysicalDbName(dbShard);
        } else {
            sqlStatement = sql;
            dbName = logicDBConfig.getDefaultPhysicalDbName();
        }

        String[] sqlInfo = {sqlStatement, dbName};

        return sqlInfo;
    }

    @Override
    public int[] run(IBatchSqlBuilder sqlBuilder) {
        return new int[0];
    }

    @Override
    public IHints getHints() {
        return this.hints;
    }

    @Override
    public String getLogicName() {
        return this.logicDbName;
    }

    private ISqlBuilder setShard(ISqlBuilder sqlBuilder, GenericTable table) {
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
                    sqlBuilder.getHints().dbShardValue(dbShard);
                }

                if (logicDBConfig.getTableShardColumn() != null && !logicDBConfig.getTableShardColumn().isEmpty()) {
                    String tableShardColumn = logicDBConfig.getTableShardColumn();
                    Object tableShard = table.getFieldValue(tableShardColumn);
                    sqlBuilder.getHints().tableShardValue(tableShard);
                }

            }
        }

        return sqlBuilder;
    }
}
