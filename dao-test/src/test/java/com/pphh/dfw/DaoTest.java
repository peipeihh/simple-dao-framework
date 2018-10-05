package com.pphh.dfw;

import com.pphh.dfw.sqlb.SqlBuilder;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/30/2018
 */
public class DaoTest {

    private SqlBuilder sqlBuilder;
    private OrderTable order = Tables.ORDER;
    private Dao dao;

    public DaoTest() throws Exception {
        dao = DaoFactory.generate("tableShard");
    }

    @Ignore
    @Test
    public void test() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        dao.insert(orderEntity);
    }

    @Test
    public void testQuery() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        dao.queryByPk(orderEntity);
    }

}
