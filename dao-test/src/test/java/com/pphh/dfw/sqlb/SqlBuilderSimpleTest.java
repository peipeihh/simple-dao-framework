package com.pphh.dfw.sqlb;

import com.pphh.dfw.BaseTest;
import com.pphh.dfw.core.exception.DfwException;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static com.pphh.dfw.sqlb.SqlStarter.select;
import static com.pphh.dfw.sqlb.SqlStarter.sqlBuilder;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/30/2018
 */
public class SqlBuilderSimpleTest extends BaseTest {

    private ISqlBuilder sql;

    /**
     * 实现表的分片查询
     */
    @Test
    public void test() throws DfwException {
        sql = select(order.id, order.name).from(order);
        Assert.assertEquals("SELECT `id` , `name` FROM `order`", sql.build());
    }

    /**
     * 实现表的分片查询
     * 由于没有指定table分片值，将会抛出异常
     */
    @Test(expected = DfwException.class)
    public void testTableShardWithExpectedException() throws DfwException {
        sql = select(order.id, order.name).from(order);
        Assert.assertNotNull(sql.buildOn(tableShardDao));
        Assert.fail("An expected exception is NOT thrown out.");
    }

    @Test
    public void testTableShardWithNoException() throws DfwException {
        sql = select(order.id, order.name).from(order);
        sql.getHints().tableShardValue(0);
        Assert.assertEquals("SELECT `id` , `name` FROM `order_0`", sql.buildOn(tableShardDao));
    }

    /**
     * 实现表的分片查询
     * 由于没有指定db分片值，将会抛出异常
     */
    @Test(expected = DfwException.class)
    public void testDbShardWithExpectedException() throws DfwException {
        sql = select(order.id, order.name).from(order);
        Assert.assertNotNull(sql.buildOn(dbShardDao));
        Assert.fail("An expected exception is NOT thrown out.");
    }

    @Test
    public void testDbShardWithNoException() throws DfwException {
        sql = select(order.id, order.name).from(order);
        sql.getHints().dbShardValue(0);
        Assert.assertEquals("SELECT `id` , `name` FROM `order` -- 0", sql.buildOn(dbShardDao));
    }

    /**
     * 实现表的分片查询
     * 由于没有指定db和table分片值，将会抛出异常
     */
    @Test(expected = DfwException.class)
    public void testTableDbShardWithExpectedException() throws DfwException {
        sql = select(order.id, order.name).from(order);
        Assert.assertNotNull(sql.buildOn(tableDbShardDao));
        Assert.fail("An expected exception is NOT thrown out.");
    }

    @Test
    public void testTableDbShardWithNoException() throws DfwException {
        sql = select(order.id, order.name).from(order);
        sql.getHints().dbShardValue(0).tableShardValue(0);
        Assert.assertEquals("SELECT `id` , `name` FROM `order_0` -- 0", sql.buildOn(tableDbShardDao));
    }

    /**
     * 实现表的清理
     *
     * @throws Exception
     */
    @Test
    public void testCleanUp() throws Exception {
        sql = sqlBuilder().append("DELETE FROM `order`");
        int result = noShardDao.run(sql);
        Assert.assertTrue(result >= 0);
    }

    /**
     * 添加表记录
     *
     * @throws Exception
     */
    @Test
    public void testInsertRecord() throws Exception {
        String insertSql = String.format("INSERT INTO `order` (`name`, `city_id` , `country_id`) VALUES ( '%s' , '%s' , '%s' )", "apple", 1, 10);
        sql = sqlBuilder().append(insertSql);
        int result = noShardDao.run(sql);
        Assert.assertEquals(1, result);
    }

}
