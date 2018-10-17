package com.pphh.dfw.shard;

import com.pphh.dfw.BaseTest;
import com.pphh.dfw.Dao;
import com.pphh.dfw.DaoFactory;
import com.pphh.dfw.OrderEntity;
import com.pphh.dfw.sqlb.SqlBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/17/2018
 */
public class TableShardTest extends BaseTest {

    private Dao dao;

    public TableShardTest() throws Exception {
        dao = DaoFactory.generate(LOGIC_DB_TABLE_SHARD);
    }

    @Before
    public void setUpCase() throws Exception {
        cleanUpTable();

        for (int j = 0; j < TABLE_MOD; j++) {
            for (int k = 0; k < TABLE_MOD; k++) {
                String sql = String.format("INSERT INTO `order_%d` (`id`, `name`, `city_id`, `country_id`) VALUES ('%s', '%s', '%s', '%s')", j, k + 1, "apple", j, j * 10);
                sqlBuilder = new SqlBuilder(LOGIC_DB_DB_TABLE_SHARD);
                sqlBuilder.hints().inDbShard(0).inTableShard(j);
                sqlBuilder.append(sql).execute();
            }
        }
    }

    @Test
    public void testSimpleQuery() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        OrderEntity entity = dao.queryByPk(orderEntity);
        Assert.assertNotNull(entity);
        Assert.assertTrue(entity.getId().equals(1));
        Assert.assertTrue(entity.getCityID().equals(1));
        Assert.assertTrue(entity.getCountryID().equals(10));
    }
}
