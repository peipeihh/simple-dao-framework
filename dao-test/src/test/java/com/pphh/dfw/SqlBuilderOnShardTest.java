package com.pphh.dfw;

import com.pphh.dfw.sqlb.SqlBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/9/2018
 */
public class SqlBuilderOnShardTest extends BaseTest {

    @Test
    public void testSimpleShard() {
        sqlBuilder = new SqlBuilder();
        sqlBuilder.hints().dbShardValue(1).tableShardValue(1);
        sqlBuilder.deleteFrom(order).where(order.id.equal(1));

        // noShard
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1'", sqlBuilder.buildOn("noShard"));

        // tableShard
        Assert.assertEquals("DELETE FROM `order_1` WHERE `id` = '1'", sqlBuilder.buildOn("tableShard"));

        // dbShard
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1' -- 1", sqlBuilder.buildOn("dbShard"));

        // tableDbShard
        Assert.assertEquals("DELETE FROM `order_1` WHERE `id` = '1' -- 1", sqlBuilder.buildOn("tableDbShard"));
    }


    @Test
    public void testSelect() {

        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                sqlBuilder = new SqlBuilder();
                sqlBuilder.select(order.id, order.name).from(order).hints().dbShardValue(i).tableShardValue(j);

                // noShard
                String expectedSql = "SELECT `id` , `name` FROM `order`";
                Assert.assertEquals(expectedSql, sqlBuilder.buildOn("noShard"));

                // tableShard
                expectedSql = String.format("SELECT `id` , `name` FROM `order_%s`", j % 2);
                Assert.assertEquals(expectedSql, sqlBuilder.buildOn("tableShard"));

                // dbShard
                expectedSql = String.format("SELECT `id` , `name` FROM `order` -- %s", i % 2);
                Assert.assertEquals(expectedSql, sqlBuilder.buildOn("dbShard"));

                // tableDbShard
                expectedSql = String.format("SELECT `id` , `name` FROM `order_%s` -- %s", j % 2, i % 2);
                Assert.assertEquals(expectedSql, sqlBuilder.buildOn("tableDbShard"));
            }
        }

    }


    @Test
    public void testDelete() {

        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                sqlBuilder = new SqlBuilder();
                sqlBuilder.deleteFrom(order).where(order.id.equal(i * j)).hints().dbShardValue(i).tableShardValue(j);

                // noShard
                String expectedSql = String.format("DELETE FROM `order` WHERE `id` = '%s'", i * j);
                Assert.assertEquals(expectedSql, sqlBuilder.buildOn("noShard"));

                // tableShard
                expectedSql = String.format("DELETE FROM `order_%s` WHERE `id` = '%s'", j % 2, i * j);
                Assert.assertEquals(expectedSql, sqlBuilder.buildOn("tableShard"));

                // dbShard
                expectedSql = String.format("DELETE FROM `order` WHERE `id` = '%s' -- %s", i * j, i % 2);
                Assert.assertEquals(expectedSql, sqlBuilder.buildOn("dbShard"));

                // tableDbShard
                expectedSql = String.format("DELETE FROM `order_%s` WHERE `id` = '%s' -- %s", j % 2, i * j, i % 2);
                Assert.assertEquals(expectedSql, sqlBuilder.buildOn("tableDbShard"));
            }
        }

    }

}
