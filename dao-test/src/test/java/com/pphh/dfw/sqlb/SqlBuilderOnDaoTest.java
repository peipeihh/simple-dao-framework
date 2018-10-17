package com.pphh.dfw.sqlb;

import com.pphh.dfw.BaseTest;
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
public class SqlBuilderOnDaoTest extends BaseTest {

    private ISqlBuilder sql;

    @Ignore
    @Test
    public void test() {
        sql = select(order.id, order.name).from(order);
        Assert.assertEquals("SELECT `id` , `name` FROM `order`", sql.build());
        Assert.assertNotNull("SELECT `id` , `name` FROM `order_0`", sql.buildOn(tableShardDao));
    }

    @Test
    public void testCleanUp() throws Exception {
        sql = sqlBuilder().append("DELETE FROM `order`");
        int result = noShardDao.run(sql);
        Assert.assertTrue(result >= 0);
    }

    @Test
    public void testInsertRecord() throws Exception {
        String insertSql = String.format("INSERT INTO `order` (`name`, `city_id` , `country_id`) VALUES ( '%s' , '%s' , '%s' )", "apple", 1, 10);
        sql = sqlBuilder().append(insertSql);
        int result = noShardDao.run(sql);

        Assert.assertEquals(1, result);
    }

}
