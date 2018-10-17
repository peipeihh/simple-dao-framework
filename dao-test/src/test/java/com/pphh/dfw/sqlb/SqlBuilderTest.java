package com.pphh.dfw.sqlb;

import com.pphh.dfw.BaseTest;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import org.junit.Assert;
import org.junit.Test;

import static com.pphh.dfw.sqlb.SqlStarter.*;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class SqlBuilderTest extends BaseTest {

    private ISqlBuilder sql;

    /**
     * 通过append实现简单的sql语句叠加
     */
    @Test
    public void testPlainSql() {
        sql = sqlBuilder().append("DELETE FROM `order`");
        Assert.assertEquals("DELETE FROM `order`", sql.build());

        sql = sqlBuilder().append("DELETE FROM `order`");
        Assert.assertEquals("DELETE FROM `order`", sql.buildOn(noShardDao));

        String insertSql = String.format("INSERT INTO `order` (`name`, `city_id` , `country_id`) VALUES ( '%s' , '%s' , '%s' )", "apple", 1, 10);
        sql = sqlBuilder().append(insertSql);
        Assert.assertEquals(insertSql, sql.build());
    }

    /**
     * sql查询
     */
    @Test
    public void testSelect() {
        sql = select(order.id, order.name).from(order);
        Assert.assertEquals("SELECT `id` , `name` FROM `order`", sql.build());

        sql = selectAll().from(order);
        Assert.assertEquals("SELECT * FROM `order`", sql.build());

        sql = select().from(order).where();
        Assert.assertEquals("SELECT * FROM `order` WHERE", sql.build());
    }

    /**
     * sql插入
     */
    @Test
    public void testInsert() {
        sql = insertInto(order, order.id, order.name).values("1", "apple");
        Assert.assertEquals("INSERT INTO `order` ( `id` , `name` ) VALUES ( '1' , 'apple' )", sql.build());
    }

    /**
     * sql更新
     */
    @Test
    public void testUpdate() {
        sql = update(order).set(order.id.equal(2), order.name.equal("banana"));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana'", sql.build());

        sql = update(order).set(order.id.equal(2), order.name.equal("banana")).where(order.id.equal(1));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana' WHERE `id` = '1'", sql.build());

        sql = update(order).set(order.id.equal(2), order.name.equal("banana")).where(order.id.equal(1), order.name.equal("apple"));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana' WHERE `id` = '1' , `name` = 'apple'", sql.build());
    }

    @Test
    public void testDelete() {
        sql = deleteFrom(order).where(order.id.equal(1));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1'", sql.build());

        sql = deleteFrom(order).where(order.id.equal(1), order.name.equal("apple"));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1' , `name` = 'apple'", sql.build());
    }

}
