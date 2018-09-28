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
public class SqlBuilderTest {

    private SqlBuilder sqlBuilder;
    private OrderTable order = Tables.ORDER;

    @Test
    public void testSelect() {
        sqlBuilder = new SqlBuilder();
        sqlBuilder.select(order.ID, order.NAME).from(order);
        Assert.assertEquals("SELECT `id` , `name` FROM `order`", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.selectAll().from(order);
        Assert.assertEquals("SELECT * FROM `order`", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.select().from(order).where();
        Assert.assertEquals("SELECT * FROM `order` WHERE", sqlBuilder.build());
    }

    @Test
    public void testInsert(){
        sqlBuilder = new SqlBuilder();
        sqlBuilder.insertInto(order, order.ID, order.NAME).values("1", "apple");
        Assert.assertEquals("INSERT INTO `order` ( `id` , `name` ) VALUES ( '1' , 'apple' )", sqlBuilder.build());
    }

    @Test
    public void testUpdate(){
        sqlBuilder = new SqlBuilder();
        sqlBuilder.update(order).set(order.ID.equal(2), order.NAME.equal("banana"));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana'", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.update(order).set(order.ID.equal(2), order.NAME.equal("banana")).where(order.ID.equal(1));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana' WHERE `id` = '1'", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.update(order).set(order.ID.equal(2), order.NAME.equal("banana")).where(order.ID.equal(1), order.NAME.equal("apple"));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana' WHERE `id` = '1' , `name` = 'apple'", sqlBuilder.build());
    }

    @Test
    public void testDelete(){
        sqlBuilder = new SqlBuilder();
        sqlBuilder.deleteFrom(order).where(order.ID.equal(1));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1'", sqlBuilder.build());

        sqlBuilder = new SqlBuilder();
        sqlBuilder.deleteFrom(order).where(order.ID.equal(1), order.NAME.equal("apple"));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1' , `name` = 'apple'", sqlBuilder.build());
    }


}
