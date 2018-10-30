package com.pphh.dfw.shard;

import com.pphh.dfw.*;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static com.pphh.dfw.sqlb.SqlStarter.select;
import static com.pphh.dfw.sqlb.SqlStarter.sqlBuilder;
import static com.pphh.dfw.core.sqlb.SqlConstant.*;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/17/2018
 */
public class DbTableShardTest extends BaseTest {

    private Dao dao;

    public DbTableShardTest() throws Exception {
        dao = DaoFactory.generate(LOGIC_DB_TABLE_DB_SHARD);
    }

    @Before
    public void setUpCase() throws Exception {
        cleanUpTable();

        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    String sql = String.format("INSERT INTO `order_%d` (`id`, `name`, `city_id`, `country_id`) VALUES ('%s', '%s', '%s', '%s')", j, k + 1, "apple", i, j);
                    ISqlBuilder sqlb = sqlBuilder().append(sql).hints(new Hints().inDbShard(i).inTableShard(j));
                    tableDbShardDao.run(sqlb);
                }
            }
        }
    }

    @After
    public void tearDownCase() {

    }

    @Test
    public void testQueryByPk() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setCityID(i);
                    order.setCountryID(j);
                    OrderEntity entity = dao.query(order);
                    Assert.assertNotNull(entity);
                    Assert.assertEquals(k + 1, entity.getId().intValue());
                    Assert.assertEquals(i, entity.getCityID().intValue());
                    Assert.assertEquals(j, entity.getCountryID().intValue());
                }
            }
        }
    }


    @Test
    public void testQueryWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            //dao.getHints().dbShardValue(i);
            for (int j = 0; j < TABLE_MOD; j++) {
                //dao.getHints().tableShardValue(j);
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setCityID(j);
                    order.setCountryID(j * 10);
                    OrderEntity entity = dao.query(order);
                    Assert.assertNotNull(entity);
                    Assert.assertEquals(k + 1, entity.getId().intValue());
                }
            }
        }
    }

    @Test
    public void testQueryBySample() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setCityID(i);
                    order.setCountryID(j);
                    List<OrderEntity> entities = dao.queryBySample(order);
                    Assert.assertNotNull(entities);
                    for (OrderEntity entity : entities) {
                        Assert.assertEquals(i, entity.getCityID().intValue());
                        Assert.assertEquals(j, entity.getCountryID().intValue());
                    }
                }
            }
        }
    }

    @Ignore
    @Test
    public void testCountBySample() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                OrderEntity order = new OrderEntity();
                order.setCityID(i);
                order.setCountryID(j);
                long count = dao.countBySample(order);
                Assert.assertEquals(TABLE_MOD, count);
            }
        }
    }

    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                OrderEntity order = new OrderEntity();
                order.setCityID(i);
                order.setCountryID(j);
                int result = dao.insert(order);
                Assert.assertEquals(1, result);
            }
        }
    }

    @Test
    public void testDelete() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setCityID(i);
                    order.setCountryID(j);
                    int result = dao.delete(order);
                    Assert.assertEquals(1, result);
                }
            }
        }
    }

    @Test
    public void testDeleteWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    int result = dao.delete(order, new Hints().dbShardValue(i).tableShardValue(j));
                    Assert.assertEquals(1, result);
                }
            }
        }
    }

    @Test
    public void testDeleteBySample() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setName("apple");
                    order.setCityID(i);
                    order.setCountryID(j);
                    int result = dao.deleteBySample(order);
                    Assert.assertEquals(1, result);
                }
            }
        }
    }

    @Test
    public void testDeleteBySampleWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setName("apple");
                    int result = dao.deleteBySample(order, new Hints().dbShardValue(i).tableShardValue(j));
                    Assert.assertEquals(1, result);
                }
            }
        }
    }

    @Test
    public void testUpdate() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setName("banana");
                    order.setCityID(i);
                    order.setCountryID(j);
                    int result = dao.update(order);
                    Assert.assertEquals(1, result);

                    OrderEntity pk = new OrderEntity();
                    pk.setId(k + 1);
                    OrderEntity entity = dao.query(pk, new Hints().dbShardValue(i).tableShardValue(j));
                    Assert.assertEquals(entity.getName(), "banana");
                }
            }
        }
    }

    @Test
    public void testUpdateWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setName("banana");
                    int result = dao.update(order, new Hints().dbShardValue(i).tableShardValue(j));
                    Assert.assertEquals(1, result);

                    OrderEntity pk = new OrderEntity();
                    pk.setId(k + 1);
                    OrderEntity entity = dao.query(pk, new Hints().dbShardValue(i).tableShardValue(j));
                    Assert.assertEquals(entity.getName(), "banana");
                }
            }
        }
    }

    @Test
    public void testQueryObject() throws Exception {
        // query by (primary key)
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    ISqlBuilder builder = select().from(order)
                            .where(order.id.equal(k + 1))
                            .hints(new Hints().dbShardValue(i).tableShardValue(j)).into(OrderEntity.class);
                    OrderEntity result = dao.queryForObject(builder);
                    Assert.assertNotNull(result);
                }
            }
        }

        // query by (full values)
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    ISqlBuilder builder = select().from(order)
                            .where(order.id.equal(k + 1), AND, order.city_id.equal(i), AND, order.country_id.equal(j))
                            .hints(new Hints().dbShardValue(i).tableShardValue(j)).into(OrderEntity.class);
                    OrderEntity result = dao.queryForObject(builder);
                    Assert.assertNotNull(result);
                }
            }
        }
    }

    @Ignore
    @Test
    public void testQueryObjectWithNoHints() throws Exception {
        // query by (primary key)
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    ISqlBuilder builder = select().from(order)
                            .where(order.id.equal(k + 1))
                            .into(OrderEntity.class);
                    OrderEntity result = dao.queryForObject(builder);
                    Assert.assertNotNull(result);
                }
            }
        }

        // query by (full values)
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    ISqlBuilder builder = select().from(order)
                            .where(order.id.equal(k + 1), AND, order.city_id.equal(i), AND, order.country_id.equal(j))
                            .into(OrderEntity.class);
                    OrderEntity result = dao.queryForObject(builder);
                    Assert.assertNotNull(result);
                }
            }
        }
    }

    @Test
    public void testQueryObjectWithHints() throws Exception {
        // query with hints (by primary key)
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    ISqlBuilder builder = select().from(order)
                            .where(order.id.equal(k + 1))
                            .into(OrderEntity.class);
                    OrderEntity result = dao.queryForObject(builder, new Hints().dbShardValue(i).tableShardValue(j));
                    Assert.assertNotNull(result);
                }
            }
        }

        // query with hints (by full values)
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                for (int k = 0; k < TABLE_MOD; k++) {
                    ISqlBuilder builder = select().from(order)
                            .where(order.id.equal(k + 1), AND, order.city_id.equal(i), AND, order.country_id.equal(j))
                            .into(OrderEntity.class);
                    OrderEntity result = dao.queryForObject(builder, new Hints().dbShardValue(i).tableShardValue(j));
                    Assert.assertNotNull(result);
                }
            }
        }
    }

    @Test
    public void testQueryList() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                ISqlBuilder builder = select().from(order)
                        .where(order.name.equal("apple"))
                        .hints(new Hints().dbShardValue(i).tableShardValue(j))
                        .into(OrderEntity.class);
                List<OrderEntity> results = dao.queryForList(builder);
                Assert.assertNotNull(results);
                Assert.assertEquals(TABLE_MOD, results.size());
                for (OrderEntity entity : results) {
                    Assert.assertEquals("apple", entity.getName());
                }
            }
        }
    }

    @Test
    public void testQueryListWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                ISqlBuilder builder = select().from(order)
                        .where(order.name.equal("apple"))
                        .into(OrderEntity.class);
                List<OrderEntity> results = dao.queryForList(builder, new Hints().dbShardValue(i).tableShardValue(j));
                Assert.assertNotNull(results);
                Assert.assertEquals(TABLE_MOD, results.size());
                for (OrderEntity entity : results) {
                    Assert.assertEquals("apple", entity.getName());
                }
            }
        }
    }

    @Test
    public void testInsertList() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                List<OrderEntity> orders = new ArrayList<>();
                for (int k = 0; k < j + 1; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setName("banana");
                    order.setCityID(i);
                    order.setCountryID(j);
                    orders.add(order);
                }

                int[] results = dao.insert(orders);
                for (int result : results) {
                    Assert.assertEquals(1, result);
                }

                OrderEntity sample = new OrderEntity();
                sample.setName("banana");
                sample.setCityID(i);
                sample.setCountryID(j);
                List<OrderEntity> entities = dao.queryBySample(sample);
                Assert.assertEquals(j + 1, entities.size());

            }
        }
    }

    @Test
    public void testInsertListWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                List<OrderEntity> orders = new ArrayList<>();
                for (int k = 0; k < j + 1; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setName("banana");
                    orders.add(order);
                }

                int[] results = dao.insert(orders, new Hints().dbShardValue(i).tableShardValue(j));
                for (int result : results) {
                    Assert.assertEquals(1, result);
                }

                OrderEntity sample = new OrderEntity();
                sample.setName("banana");
                List<OrderEntity> entities = dao.queryBySample(sample, new Hints().dbShardValue(i).tableShardValue(j));
                Assert.assertEquals(j + 1, entities.size());
            }
        }
    }

    @Test
    public void testDeleteListByPk() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                List<OrderEntity> orders = new ArrayList<>();
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setCityID(i);
                    order.setCountryID(j);
                    orders.add(order);
                }

                int[] results = dao.delete(orders);
                for (int result : results) {
                    Assert.assertEquals(1, result);
                }

                OrderEntity sample = new OrderEntity();
                sample.setName("apple");
                sample.setCityID(i);
                sample.setCountryID(j);
                List<OrderEntity> entities = dao.queryBySample(sample);
                Assert.assertEquals(0, entities.size());
            }
        }
    }

    @Test
    public void testDeleteListByPkWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                List<OrderEntity> orders = new ArrayList<>();
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    orders.add(order);
                }

                int[] results = dao.delete(orders, new Hints().dbShardValue(i).tableShardValue(j));
                for (int result : results) {
                    Assert.assertEquals(1, result);
                }

                OrderEntity sample = new OrderEntity();
                sample.setName("apple");
                List<OrderEntity> entities = dao.queryBySample(sample, new Hints().dbShardValue(i).tableShardValue(j));
                Assert.assertEquals(0, entities.size());
            }
        }
    }

    @Test
    public void testDeleteListBySample() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                List<OrderEntity> orders = new ArrayList<>();
                OrderEntity sample = new OrderEntity();
                sample.setName("apple");
                sample.setCityID(i);
                sample.setCountryID(j);
                orders.add(sample);

                int[] results = dao.deleteBySample(orders);
                Assert.assertEquals(1, results.length);
                Assert.assertEquals(3, results[0]);

                List<OrderEntity> entities = dao.queryBySample(sample);
                Assert.assertEquals(0, entities.size());
            }
        }
    }

    @Test
    public void testDeleteListBySampleWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                List<OrderEntity> orders = new ArrayList<>();
                OrderEntity sample = new OrderEntity();
                sample.setName("apple");
                orders.add(sample);

                int[] results = dao.deleteBySample(orders, new Hints().dbShardValue(i).tableShardValue(j));
                Assert.assertEquals(1, results.length);
                Assert.assertEquals(3, results[0]);

                List<OrderEntity> entities = dao.queryBySample(sample, new Hints().dbShardValue(i).tableShardValue(j));
                Assert.assertEquals(0, entities.size());
            }
        }
    }

    @Test
    public void testUpdateList() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                List<OrderEntity> orders = new ArrayList<>();
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setName("banana");
                    order.setCityID(i);
                    order.setCountryID(j);
                    orders.add(order);
                }

                int[] results = dao.update(orders);
                Assert.assertEquals(3, results.length);
                for (int result : results) {
                    Assert.assertEquals(1, result);
                }

                OrderEntity sample = new OrderEntity();
                sample.setName("banana");
                sample.setCityID(i);
                sample.setCountryID(j);
                List<OrderEntity> entities = dao.queryBySample(sample);
                Assert.assertEquals(3, entities.size());
            }
        }
    }

    @Test
    public void testUpdateListWithHints() throws Exception {
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                List<OrderEntity> orders = new ArrayList<>();
                for (int k = 0; k < TABLE_MOD; k++) {
                    OrderEntity order = new OrderEntity();
                    order.setId(k + 1);
                    order.setName("banana");
                    orders.add(order);
                }

                int[] results = dao.update(orders, new Hints().dbShardValue(i).tableShardValue(j));
                Assert.assertEquals(3, results.length);
                for (int result : results) {
                    Assert.assertEquals(1, result);
                }

                OrderEntity sample = new OrderEntity();
                sample.setName("banana");
                List<OrderEntity> entities = dao.queryBySample(sample, new Hints().dbShardValue(i).tableShardValue(j));
                Assert.assertEquals(3, entities.size());
            }
        }
    }

}
