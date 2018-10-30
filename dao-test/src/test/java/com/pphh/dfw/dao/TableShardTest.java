package com.pphh.dfw.dao;

import com.pphh.dfw.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.pphh.dfw.sqlb.SqlStarter.sqlBuilder;

/**
 * 实现分表逻辑的DAO
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
                builder = sqlBuilder().append(sql).hints(new Hints().inDbShard(0).inTableShard(j));
                tableDbShardDao.run(builder);
            }
        }
    }

    @Test
    public void testSimpleQuery() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        OrderEntity entity = dao.query(orderEntity);
        Assert.assertNotNull(entity);
        Assert.assertTrue(entity.getId().equals(1));
        Assert.assertTrue(entity.getCityID().equals(1));
        Assert.assertTrue(entity.getCountryID().equals(10));
    }
}
