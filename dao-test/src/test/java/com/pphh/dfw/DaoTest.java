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

    @Ignore
    @Test
    public void test() throws Exception {
        Dao dao = DaoFactory.generate("tableShard");
        OrderEntity orderEntity = new OrderEntity();
        dao.insert(orderEntity);
    }

}
