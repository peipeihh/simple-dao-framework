package com.pphh.dfw;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/30/2018
 */
public class DaoTest extends BaseTest {

    private Dao dao;

    public DaoTest() throws Exception {
        dao = DaoFactory.generate("tableShard");
    }

    @Ignore
    @Test
    public void testInsert() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        dao.insert(orderEntity);
    }

    @Test
    public void testQuery() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        OrderEntity entity = dao.queryByPk(orderEntity);
        Assert.assertNotNull(entity);
        Assert.assertTrue(entity.getId().equals(1));
    }


}
