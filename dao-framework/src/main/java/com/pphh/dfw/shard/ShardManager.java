package com.pphh.dfw.shard;

import com.pphh.dfw.GlobalDataSourceConfig;
import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.ShardStrategy;
import com.pphh.dfw.core.constant.HintEnum;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.exception.DfwException;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/3/19
 */
public class ShardManager {

    /**
     * 根据逻辑数据库定义，结合hints提示，计算表分片
     *
     * @param logicDbName 逻辑数据库名
     * @param tableHints  表分片提示
     * @return 返回表分片
     * @throws DfwException 若逻辑表需要分片，但无任何可用的分片提示，则抛出异常
     */
    public static String getTableShardByHints(String logicDbName, IHints tableHints) throws DfwException {
        String tableShard = null;

        IDataSourceConfig dsConfig = GlobalDataSourceConfig.getInstance().load();

        Object tableShardId = tableHints.getHintValue(HintEnum.TABLE_SHARD);
        Object tableShardValue = tableHints.getHintValue(HintEnum.TABLE_SHARD_VALUE);

        LogicDBConfig logicDBConfig = dsConfig.getLogicDBConfig(logicDbName);
        ShardStrategy strategy = logicDBConfig.getShardStrategy();
        if (strategy != null) {
            if (logicDBConfig.getTableShardColumn() != null && !logicDBConfig.getTableShardColumn().isEmpty()) {
                if (tableShardId != null) {
                    tableShard = strategy.locateTableShard(tableShardId.toString(), Boolean.FALSE);
                } else if (tableShardValue != null) {
                    tableShard = strategy.locateTableShard(tableShardValue.toString(), Boolean.TRUE);
                }

                if (tableShardId == null && tableShardValue == null) {
                    throw new DfwException(String.format("This dao requires table shard, but no shard id/value is found to column [%s]. " +
                            "Please specify shard id/value before building sql.", logicDBConfig.getTableShardColumn()));
                }
            }
        }

        return tableShard;
    }

    /**
     * 根据逻辑数据库定义，结合hints提示，计算database分片
     *
     * @param logicDbName 逻辑数据库名
     * @param dbHints     db分片提示
     * @return 返回database分片
     * @throws DfwException 若逻辑库需要分片，但无任何可用的分片提示，则抛出异常
     */
    public static String getDbShardByHints(String logicDbName, IHints dbHints) throws DfwException {
        String dbShard = null;

        IDataSourceConfig dsConfig = GlobalDataSourceConfig.getInstance().load();

        Object dbShardId = dbHints.getHintValue(HintEnum.DB_SHARD);
        Object dbShardValue = dbHints.getHintValue(HintEnum.DB_SHARD_VALUE);
        LogicDBConfig logicDBConfig = dsConfig.getLogicDBConfig(logicDbName);
        ShardStrategy strategy = logicDBConfig.getShardStrategy();
        if (strategy != null) {
            if (logicDBConfig.getDbShardColumn() != null && !logicDBConfig.getDbShardColumn().isEmpty()) {
                if (dbShardId != null) {
                    dbShard = strategy.locateDbShard(dbShardId.toString(), Boolean.FALSE);
                } else if (dbShardValue != null) {
                    dbShard = strategy.locateDbShard(dbShardValue.toString(), Boolean.TRUE);
                }

                if (dbShardId == null && dbShardValue == null) {
                    throw new DfwException(String.format("This dao requires db shard, but no shard id/value is found to column [%s]. " +
                            "Please specify shard id/value before building sql.", logicDBConfig.getDbShardColumn()));
                }
            }
        }

        return dbShard;
    }

    /**
     * 根据逻辑数据库定义，结合hints提示，获取数据库database名
     *
     * @param logicDbName 逻辑数据库名
     * @param dbHints     db分片提示
     * @return 返回数据库名
     * @throws DfwException 若逻辑库需要分片，但无任何可用的分片提示，则抛出异常
     */
    public static String getDbNameByHints(String logicDbName, IHints dbHints) throws DfwException {
        String dbName = null;

        String dbShard = getDbShardByHints(logicDbName, dbHints);
        if (dbShard != null) {
            IDataSourceConfig dsConfig = GlobalDataSourceConfig.getInstance().load();
            LogicDBConfig logicDBConfig = dsConfig.getLogicDBConfig(logicDbName);
            if (logicDBConfig != null) {
                dbName = logicDBConfig.getPhysicalDbName(dbShard);
            }
        }

        return dbName;
    }
}
