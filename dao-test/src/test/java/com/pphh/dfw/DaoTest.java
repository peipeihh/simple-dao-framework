package com.pphh.dfw;

import com.pphh.dfw.sqlb.SqlBuilder;
import org.junit.Assert;
import org.junit.Before;
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
        dao = DaoFactory.generate(LOGIC_DB_TABLE_SHARD);
    }

    @Ignore
    @Test
    public void testInsert() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        dao.insert(orderEntity);
    }


}
