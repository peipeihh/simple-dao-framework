package com.pphh.dfw.sqlb;

import com.pphh.dfw.BaseTest;

import static com.pphh.dfw.sqlb.SqlStarter.deleteFrom;
import static com.pphh.dfw.sqlb.SqlStarter.select;

import com.pphh.dfw.Hints;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
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
        ISqlBuilder sqlBuilder = deleteFrom(order).where(order.id.equal(1));
        sqlBuilder.getHints().dbShardValue(1).tableShardValue(1);
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1'", sqlBuilder.buildOn(noShardDao));
        Assert.assertEquals("DELETE FROM `order_1` WHERE `id` = '1'", sqlBuilder.buildOn(tableShardDao));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1' -- 1", sqlBuilder.buildOn(dbShardDao));
        Assert.assertEquals("DELETE FROM `order_1` WHERE `id` = '1' -- 1", sqlBuilder.buildOn(tableDbShardDao));
    }

    @Test
    public void testSelect() {

        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                ISqlBuilder sql = select(order.id, order.name).from(order).hints(new Hints().dbShardValue(i).tableShardValue(j));

                // noShard
                String expectedSql = "SELECT `id` , `name` FROM `order`";
                Assert.assertEquals(expectedSql, sql.buildOn(noShardDao));

                // tableShard
                expectedSql = String.format("SELECT `id` , `name` FROM `order_%s`", j % 3);
                Assert.assertEquals(expectedSql, sql.buildOn(tableShardDao));

                // dbShard
                expectedSql = String.format("SELECT `id` , `name` FROM `order` -- %s", i % 2);
                Assert.assertEquals(expectedSql, sql.buildOn(dbShardDao));

                // tableDbShard
                expectedSql = String.format("SELECT `id` , `name` FROM `order_%s` -- %s", j % 3, i % 2);
                Assert.assertEquals(expectedSql, sql.buildOn(tableDbShardDao));
            }
        }

    }


    @Test
    public void testDelete() {

        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                ISqlBuilder sql = deleteFrom(order).where(order.id.equal(i * j)).hints(new Hints().dbShardValue(i).tableShardValue(j));

                // noShard
                String expectedSql = String.format("DELETE FROM `order` WHERE `id` = '%s'", i * j);
                Assert.assertEquals(expectedSql, sql.buildOn(noShardDao));

                // tableShard
                expectedSql = String.format("DELETE FROM `order_%s` WHERE `id` = '%s'", j % 3, i * j);
                Assert.assertEquals(expectedSql, sql.buildOn(tableShardDao));

                // dbShard
                expectedSql = String.format("DELETE FROM `order` WHERE `id` = '%s' -- %s", i * j, i % 2);
                Assert.assertEquals(expectedSql, sql.buildOn(dbShardDao));

                // tableDbShard
                expectedSql = String.format("DELETE FROM `order_%s` WHERE `id` = '%s' -- %s", j % 3, i * j, i % 2);
                Assert.assertEquals(expectedSql, sql.buildOn(tableDbShardDao));
            }
        }

    }

}
