package com.pphh.dfw.hints;

import com.pphh.dfw.Hints;
import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.constant.HintEnum;
import com.pphh.dfw.core.constant.SqlProviderEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/29/2018
 */
public class HintsTest {

    @Test
    public void testHintsSet() {
        IHints hints = new Hints();
        Long dbShard = 1L;
        hints.setHintValue(HintEnum.DB_SHARD, dbShard);
        String dbShardValue = "1";
        hints.setHintValue(HintEnum.DB_SHARD_VALUE, dbShardValue);
        Long tableShard = 2L;
        hints.setHintValue(HintEnum.TABLE_SHARD, tableShard);
        String tableShardValue = "2";
        hints.setHintValue(HintEnum.TABLE_SHARD_VALUE, tableShardValue);
        hints.setHintValue(HintEnum.POJO_CLASS, HintsTest.class);
        hints.setHintValue(HintEnum.SQL_DIALECT, SqlProviderEnum.MYSQL);
        hints.setHintValue(HintEnum.SET_ID_BACK, Boolean.TRUE);
        hints.setHintValue(HintEnum.DEBUG_MODE, Boolean.TRUE);
        hints.setHintValue(HintEnum.INSERT_ID, Boolean.TRUE);

        Assert.assertEquals(dbShard, (Long) hints.getHintValue(HintEnum.DB_SHARD));
        Assert.assertEquals(dbShardValue, hints.getHintValue(HintEnum.DB_SHARD_VALUE));
        Assert.assertEquals(tableShard, (Long) hints.getHintValue(HintEnum.TABLE_SHARD));
        Assert.assertEquals(tableShardValue, hints.getHintValue(HintEnum.TABLE_SHARD_VALUE));
        Assert.assertEquals(HintsTest.class, hints.getHintValue(HintEnum.POJO_CLASS));
        Assert.assertEquals(SqlProviderEnum.MYSQL, hints.getHintValue(HintEnum.SQL_DIALECT));
        Assert.assertEquals(Boolean.TRUE, hints.getHintValue(HintEnum.SET_ID_BACK));
        Assert.assertEquals(Boolean.TRUE, hints.getHintValue(HintEnum.DEBUG_MODE));
        Assert.assertEquals(Boolean.TRUE, hints.getHintValue(HintEnum.INSERT_ID));

    }

    @Test
    public void testHintsAPI() {
        IHints hints = new Hints();

        Assert.assertNull(hints.getHintValue(HintEnum.SET_ID_BACK));
        hints.setIdBack();
        Assert.assertEquals(Boolean.TRUE, hints.getHintValue(HintEnum.SET_ID_BACK));

        Assert.assertNull(hints.getHintValue(HintEnum.DEBUG_MODE));
        hints.debug();
        Assert.assertEquals(Boolean.TRUE, hints.getHintValue(HintEnum.DEBUG_MODE));

        Assert.assertNull(hints.getHintValue(HintEnum.INSERT_ID));
        hints.insertId();
        Assert.assertEquals(Boolean.TRUE, hints.getHintValue(HintEnum.INSERT_ID));

        hints.into(HintsTest.class);
        Assert.assertEquals(HintsTest.class, hints.getHintValue(HintEnum.POJO_CLASS));

        hints.sqlDialect(SqlProviderEnum.MYSQL);
        Assert.assertEquals(SqlProviderEnum.MYSQL, hints.getHintValue(HintEnum.SQL_DIALECT));

        hints.sqlDialect(SqlProviderEnum.SQLSERVER);
        Assert.assertEquals(SqlProviderEnum.SQLSERVER, hints.getHintValue(HintEnum.SQL_DIALECT));

        Long dbShard = 1L;
        hints.inDbShard(dbShard);
        Assert.assertEquals(dbShard, hints.getHintValue(HintEnum.DB_SHARD));

        String dbShardValue = "1";
        hints.dbShardValue(dbShardValue);
        Assert.assertEquals(dbShardValue, hints.getHintValue(HintEnum.DB_SHARD_VALUE));

        Long tableShard = 2L;
        hints.inTableShard(tableShard);
        Assert.assertEquals(tableShard, hints.getHintValue(HintEnum.TABLE_SHARD));

        String tableShardValue = "2";
        hints.tableShardValue(tableShardValue);
        Assert.assertEquals(tableShardValue, hints.getHintValue(HintEnum.TABLE_SHARD_VALUE));
    }

}
