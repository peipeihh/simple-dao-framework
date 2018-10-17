package com.pphh.dfw;

import com.pphh.dfw.core.*;
import com.pphh.dfw.core.constant.HintEnum;
import com.pphh.dfw.core.dao.IBatchSqlBuilder;
import com.pphh.dfw.core.dao.IDao;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.core.table.ITableField;
import com.pphh.dfw.sqlb.SqlBuilder;
import com.pphh.dfw.table.GenericTable;

import java.util.List;
import java.util.function.Function;

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

    public Dao(String logicDbName) {
        this.logicDbName = logicDbName;
        this.entityParser = new EntityParser();
    }

    @Override
    public <T> T queryByPk(T entity) {
        T result = null;

        // parse entity, 获取entity definition
        GenericTable table = this.entityParser.parse((IEntity) entity);

        if (table != null) {
            // 主键Id + 主键
            ITableField primaryKey = table.getPkField();
            String keyDefName = primaryKey.getFieldName();
            Object value = table.getFieldValue(primaryKey);

            SqlBuilder sqlBuilder = new SqlBuilder(logicDbName);
            Object dbShard = this.hints.getHintValue(HintEnum.DB_SHARD_VALUE);
            if (dbShard != null) {
                sqlBuilder.hints().dbShardValue(dbShard);
            }
            Object tableShard = this.hints.getHintValue(HintEnum.TABLE_SHARD_VALUE);
            if (tableShard != null) {
                sqlBuilder.hints().tableShardValue(tableShard);
            }
            sqlBuilder.select().from(table).where(primaryKey.equal(value));
            setShard(sqlBuilder, table);
            String sql = sqlBuilder.buildOn(logicDbName);
            System.out.println(sql);

            try {
                List<T> results = sqlBuilder.fetchInto((Class<? extends T>) entity.getClass());
                if (results.size() > 0) {
                    result = results.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public <T extends IEntity> List<T> queryBySample(T entity) {
        return null;
    }

    @Override
    public <T extends IEntity> List<T> queryAll(Long limit) {
        return null;
    }

    @Override
    public <T extends IEntity> long countBySample(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int insert(T entity) {
        return 0;
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
    public <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder) {
        return null;
    }

    @Override
    public <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder) {
        return null;
    }

    @Override
    public int execute(Function function) {
        return 0;
    }

    @Override
    public int run(ISqlBuilder sqlBuilder) {
        return 0;
    }

    @Override
    public int[] run(IBatchSqlBuilder sqlBuilder) {
        return new int[0];
    }

    @Override
    public IHints getHints() {
        return this.hints;
    }

    private SqlBuilder setShard(SqlBuilder sqlBuilder, GenericTable table) {
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
                    sqlBuilder.hints().dbShardValue(dbShard);
                }

                if (logicDBConfig.getTableShardColumn() != null && !logicDBConfig.getTableShardColumn().isEmpty()) {
                    String tableShardColumn = logicDBConfig.getTableShardColumn();
                    Object tableShard = table.getFieldValue(tableShardColumn);
                    sqlBuilder.hints().tableShardValue(tableShard);
                }

            }
        }

        return sqlBuilder;
    }
}
