package com.pphh.dfw.sqlb;

import com.pphh.dfw.BaseTest;
import com.pphh.dfw.core.exception.DfwException;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.pphh.dfw.sqlb.SqlStarter.*;
import static com.pphh.dfw.core.sqlb.SqlConstant.*;

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
    public void testPlainSql() throws DfwException {
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

        sql = select().from(order).orderBy(order.id.desc());
        Assert.assertEquals("SELECT * FROM `order` ORDER BY `id` DESC", sql.build());

        sql = select().from(order).orderBy(order.id.desc(), order.city_id.asc());
        Assert.assertEquals("SELECT * FROM `order` ORDER BY `id` DESC , `city_id` ASC", sql.build());

        sql = selectDistinct(order.id, order.name).from(order);
        Assert.assertEquals("SELECT DISTINCT `id` , `name` FROM `order`", sql.build());

        sql = selectCount().from(order);
        Assert.assertEquals("SELECT COUNT(1) FROM `order`", sql.build());
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

        sql = update(order).set(order.id.equal(2), order.name.equal("banana")).where(order.id.equal(1), AND, order.name.equal("apple"));
        Assert.assertEquals("UPDATE `order` SET `id` = '2' , `name` = 'banana' WHERE `id` = '1' AND `name` = 'apple'", sql.build());
    }

    @Test
    public void testDelete() {
        sql = deleteFrom(order).where(order.id.equal(1));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1'", sql.build());

        sql = deleteFrom(order).where(order.id.equal(1), AND, order.name.equal("apple"));
        Assert.assertEquals("DELETE FROM `order` WHERE `id` = '1' AND `name` = 'apple'", sql.build());
    }

    @Test
    public void testWhere() {
        // AND
        sql = select().from(order).where(order.id.equal(1), AND, order.name.equal("apple"));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` = '1' AND `name` = 'apple'", sql.build());

        // FIXME
        sql = select().from(order).where(order.id.equal(null), AND, order.name.equal(null));
        //Assert.assertEquals("SELECT * FROM `order`", sql.build());

        sql = select().from(order).where(order.id.equal(null), AND, order.name.equal("apple"));
        Assert.assertEquals("SELECT * FROM `order` WHERE `name` = 'apple'", sql.build());

        // FIXME
        sql = select().from(order).where(order.id.equal(1), AND, order.name.equal(null));
        //Assert.assertEquals("SELECT * FROM `order` WHERE `id` = '1'", sql.build());

        sql = select().from(order).where(order.id.equal(1), AND, order.name.equal(null).nullable());
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` = '1' AND `name` IS NULL", sql.build());

        sql = select().from(order).where(order.id.isNull(), AND, order.name.equal("apple"));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` IS NULL AND `name` = 'apple'", sql.build());

        sql = select().from(order).where(order.id.isNotNull(), AND, order.name.equal("apple"));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` IS NOT NULL AND `name` = 'apple'", sql.build());

        // OR
        sql = select().from(order).where(order.id.equal(1), OR, order.name.equal("apple"));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` = '1' OR `name` = 'apple'", sql.build());

        // FIXME
        sql = select().from(order).where(order.id.equal(null), OR, order.name.equal(null));
        //Assert.assertEquals("SELECT * FROM `order`", sql.build());

        sql = select().from(order).where(order.id.equal(null), OR, order.name.equal("apple"));
        Assert.assertEquals("SELECT * FROM `order` WHERE `name` = 'apple'", sql.build());

        // FIXME
        sql = select().from(order).where(order.id.equal(1), OR, order.name.equal(null));
        //Assert.assertEquals("SELECT * FROM `order` WHERE `id` = '1'", sql.build());

        sql = select().from(order).where().append(order.id.equal(1)).and().append(order.name.equal("apple"));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` = '1' AND `name` = 'apple'", sql.build());

        sql = select().from(order).where().append(order.id.equal(1), AND, order.name.equal("apple"));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` = '1' AND `name` = 'apple'", sql.build());

        sql = select().from(order).where(order.id.in(1));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` IN ( '1' )", sql.build());

        sql = select().from(order).where(order.id.in(1, 2));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` IN ( '1', '2' )", sql.build());

        // IN / NOT IN
        List values = new ArrayList<>();
        values.add(1);
        sql = select().from(order).where(order.id.in(values));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` IN ( '1' )", sql.build());

        values.add(2);
        sql = select().from(order).where(order.id.in(values));
        Assert.assertEquals("SELECT * FROM `order` WHERE `id` IN ( '1', '2' )", sql.build());

        values.clear();
        values.add("banana");
        sql = select().from(order).where(order.name.notIn(values));
        Assert.assertEquals("SELECT * FROM `order` WHERE `name` NOT IN ( 'banana' )", sql.build());

        values.add("apple");
        sql = select().from(order).where(order.name.notIn(values));
        Assert.assertEquals("SELECT * FROM `order` WHERE `name` NOT IN ( 'banana', 'apple' )", sql.build());
    }

    @Ignore
    @Test
    public void testTopLimit() {
        // top
        // limit
    }

    @Ignore
    @Test
    public void testComplexSql() {
        // insert into order () values select id,name as temp WHERE NOT EXISTS (select id,name from order where id = 10);
    }

}
