package com.pphh.dfw;

import com.pphh.dfw.sqlb.SqlBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class SqlBuilderTest extends BaseTest {

    @Test
    public void testSelect() {
        sqlBuilder = new SqlBuilder();
        sqlBuilder.select(order.id, order.name).from(order);
        Assert.assertEquals("SELECT `id` , `name` FROM `order`", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.selectAll().from(order);
        Assert.assertEquals("SELECT * FROM `order`", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.select().from(order).where();
        Assert.assertEquals("SELECT * FROM `order` WHERE", sqlBuilder.build());
    }

    @Test
    public void testInsert() {
        sqlBuilder = new SqlBuilder();
        sqlBuilder.insertInto(order, order.id, order.name).values("1", "apple");
        Assert.assertEquals("INSERT INTO `order` ( `id` , `name` ) VALUES ( '1' , 'apple' )", sqlBuilder.build());
    }

    @Test
    public void testUpdate() {
        sqlBuilder = new SqlBuilder();
        sqlBuilder.update(order).set(order.id.equal(2), order.name.equal("banana"));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana'", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.update(order).set(order.id.equal(2), order.name.equal("banana")).where(order.id.equal(1));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana' WHERE `id` = '1'", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.update(order).set(order.id.equal(2), order.name.equal("banana")).where(order.id.equal(1), order.name.equal("apple"));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana' WHERE `id` = '1' , `name` = 'apple'", sqlBuilder.build());
    }

    @Test
    public void testDelete() {
        sqlBuilder = new SqlBuilder();
        sqlBuilder.deleteFrom(order).where(order.id.equal(1));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1'", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.deleteFrom(order).where(order.id.equal(1), order.name.equal("apple"));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1' , `name` = 'apple'", sqlBuilder.build());
    }

    @Test
    public void testPlainSql() {
        sqlBuilder = new SqlBuilder();
        sqlBuilder.append("DELETE FROM `order`");
        Assert.assertEquals("DELETE FROM `order`", sqlBuilder.build());

        sqlBuilder = new SqlBuilder("noShard");
        sqlBuilder.append("DELETE FROM `order`");
        Assert.assertEquals("DELETE FROM `order`", sqlBuilder.build());

        sqlBuilder = new SqlBuilder("tableDbShard");
        sqlBuilder.append("DELETE FROM `order`");
        Assert.assertEquals("DELETE FROM `order`", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        String insertSql = String.format("INSERT INTO `order` (`name`, `city_id` , `country_id`) VALUES ( '%s' , '%s' , '%s' )", "apple", 1, 10);
        sqlBuilder.append(insertSql);
        Assert.assertEquals(insertSql, sqlBuilder.build());
    }

}
