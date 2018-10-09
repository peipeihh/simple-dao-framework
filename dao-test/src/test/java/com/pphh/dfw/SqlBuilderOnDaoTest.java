package com.pphh.dfw;

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

}
