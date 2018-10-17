package com.pphh.dfw.shard;

import com.pphh.dfw.BaseTest;
import com.pphh.dfw.Dao;
import com.pphh.dfw.DaoFactory;
import com.pphh.dfw.OrderEntity;
import com.pphh.dfw.sqlb.SqlBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/17/2018
 */
public class DbTableShardTest extends BaseTest {

    private Dao dao;

    public DbTableShardTest() throws Exception {
        dao = DaoFactory.generate(LOGIC_DB_DB_TABLE_SHARD);
    }

    @Before
    public void setUpCase() throws Exception {
        cleanUpTable();

        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    String sql = String.format("INSERT INTO `order_%d` (`id`, `name`, `city_id`, `country_id`) VALUES ('%s', '%s', '%s', '%s')", j, k + 1, "apple", i, j);
                    sqlBuilder = new SqlBuilder(LOGIC_DB_DB_TABLE_SHARD);
                    sqlBuilder.hints().inDbShard(i).inTableShard(j);
                    sqlBuilder.append(sql).execute();
                }
            }
        }
    }

    @After
    public void tearDownCase() {

    }

    @Test
    public void testQuery() {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setCityID(i);
                    order.setCountryID(j);
                    OrderEntity entity = dao.queryByPk(order);
                    Assert.assertNotNull(entity);
                    Assert.assertEquals(k + 1, entity.getId().intValue());
                    Assert.assertEquals(i, entity.getCityID().intValue());
                    Assert.assertEquals(j, entity.getCountryID().intValue());
                }
            }
        }
    }


    @Test
    public void testQueryWithHints() {
        for (int i = 0; i < DB_MOD; i++) {
            dao.getHints().dbShardValue(i);
            for (int j = 0; j < TABLE_MOD; j++) {
                dao.getHints().tableShardValue(j);
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setCityID(j);
                    order.setCountryID(j * 10);
                    OrderEntity entity = dao.queryByPk(order);
                    Assert.assertNotNull(entity);
                    Assert.assertEquals(k + 1, entity.getId().intValue());
                }
            }
        }
    }

}
