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

    @Test
    public void testSelect() {
        SqlBuilder sqlBuilder = new SqlBuilder();
        OrderTable order = Tables.ORDER;
        sqlBuilder.select(order.ID, order.NAME).from(order);
        Assert.assertEquals("SELECT `id` , `name` FROM `order`", sqlBuilder.build());

        sqlBuilder.selectAll().from(order);
        Assert.assertEquals("SELECT * FROM `order`", sqlBuilder.build());

        sqlBuilder.select().from(order).where();
        Assert.assertEquals("SELECT * FROM `order` WHERE", sqlBuilder.build());
    }


}
