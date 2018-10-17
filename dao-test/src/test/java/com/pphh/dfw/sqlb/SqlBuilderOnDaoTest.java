package com.pphh.dfw.sqlb;

import com.pphh.dfw.BaseTest;
import com.pphh.dfw.sqlb.SqlBuilder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/30/2018
 */
public class SqlBuilderOnDaoTest extends BaseTest {

    @Ignore
    @Test
    public void test() {
        sqlBuilder = new SqlBuilder();
        sqlBuilder.select(order.id, order.name).from(order);
        Assert.assertEquals("SELECT `id` , `name` FROM `order`", sqlBuilder.build());
        Assert.assertNotNull("SELECT `id` , `name` FROM `order_0`", sqlBuilder.buildOn("tableShard"));
    }

    @Test
    public void testCleanUp() throws Exception {
        sqlBuilder = new SqlBuilder("noShard");
        sqlBuilder.append("DELETE FROM `order`");
        int result = sqlBuilder.execute();
        Assert.assertTrue(result >= 0);
    }

    @Test
    public void testInsertRecord() throws Exception {
        sqlBuilder = new SqlBuilder("noShard");
        String insertSql = String.format("INSERT INTO `order` (`name`, `city_id` , `country_id`) VALUES ( '%s' , '%s' , '%s' )", "apple", 1, 10);
        sqlBuilder.append(insertSql);
        int result = sqlBuilder.execute();
        Assert.assertEquals(1, result);
    }

}
