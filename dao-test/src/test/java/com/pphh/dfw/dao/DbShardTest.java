package com.pphh.dfw.dao;

import com.pphh.dfw.*;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static com.pphh.dfw.core.sqlb.SqlConstant.AND;
import static com.pphh.dfw.sqlb.SqlStarter.select;
import static com.pphh.dfw.sqlb.SqlStarter.sqlBuilder;

/**
 * 实现分库逻辑的DAO
 *
 * @author huangyinhuang
 * @date 10/17/2018
 */
public class DbShardTest extends BaseTest {

    private Dao dao;

    public DbShardTest() throws Exception {
        dao = DaoFactory.generate(LOGIC_DB_DB_SHARD);
    }

    @Before
    public void setUpCase() throws Exception {
        cleanUpTable();

        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            String sql = String.format("INSERT INTO `order` (`id`, `name`, `city_id`, `country_id`) VALUES ('%s', '%s', '%s', '%s')", i, "apple", i, i * 10 + 1);
            ISqlBuilder sqlb = sqlBuilder().append(sql).hints(new Hints().dbShardValue(i));
            dbShardDao.run(sqlb);
        }
    }

    @After
    public void tearDownCase() {

    }

    @Test
    public void testQueryByPk() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);

            OrderEntity entity = dao.query(order);

            Assert.assertNotNull(entity);
            Assert.assertEquals(i, entity.getId().intValue());
            Assert.assertEquals(i, entity.getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryWithHints() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            OrderEntity entity = dao.query(order, new Hints().dbShardValue(i));
            Assert.assertNotNull(entity);
            Assert.assertEquals(i, entity.getId().intValue());
            Assert.assertEquals(i, entity.getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, entity.getCountryID().intValue());
        }

        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            OrderEntity entity = dao.query(order, new Hints().inDbShard(i % DB_MOD));
            Assert.assertNotNull(entity);
            Assert.assertEquals(i, entity.getId().intValue());
            Assert.assertEquals(i, entity.getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryBySample() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);
            List<OrderEntity> entities = dao.queryBySample(order);
            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals(i, entities.get(0).getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, entities.get(0).getCountryID().intValue());
        }
    }

    @Test
    public void testQueryBySampleWithHints() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);
            List<OrderEntity> entities = dao.queryBySample(order, new Hints().inDbShard(i % DB_MOD));
            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals(i, entities.get(0).getId().intValue());
            Assert.assertEquals(i, entities.get(0).getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, entities.get(0).getCountryID().intValue());
        }

        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);
            List<OrderEntity> entities = dao.queryBySample(order, new Hints().dbShardValue(i));
            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals(i, entities.get(0).getId().intValue());
            Assert.assertEquals(i, entities.get(0).getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, entities.get(0).getCountryID().intValue());
        }
    }

    @Ignore
    @Test
    public void testCountBySample() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);
            long count = dao.countBySample(order);
            Assert.assertEquals(DB_MOD * 3, count);
        }
    }

    @Test
    public void testInsert() throws Exception {
        for (int i = 100; i < DB_MOD * 3 + 100; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);
            int result = dao.insert(order);
            Assert.assertEquals(1, result);
            List<OrderEntity> entities = dao.queryBySample(order);
            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals(i, entities.get(0).getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, entities.get(0).getCountryID().intValue());
        }
    }

    @Test
    public void testDelete() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);

            int result = dao.delete(order);

            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }


    @Test
    public void testDeleteWithHints() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            int result = dao.delete(order, new Hints().dbShardValue(i));
            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testDeleteWithHints2() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            int result = dao.delete(order, new Hints().inDbShard(i % DB_MOD));
            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testDeleteBySample() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("apple");
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);

            int result = dao.deleteBySample(order);

            Assert.assertEquals(1, result);
            List<OrderEntity> entities = dao.queryBySample(order);
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testDeleteBySampleWithHintsValue() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setName("apple");
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);

            int result = dao.deleteBySample(order, new Hints().dbShardValue(i));

            Assert.assertEquals(1, result);
            List<OrderEntity> entities = dao.queryBySample(order, new Hints().dbShardValue(i));
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testDeleteBySampleWithHintsShard() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setName("apple");
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);

            int result = dao.deleteBySample(order, new Hints().inDbShard(i % DB_MOD));

            Assert.assertEquals(1, result);
            List<OrderEntity> entities = dao.queryBySample(order, new Hints().inDbShard(i % DB_MOD));
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testUpdate() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(10 + i);
            order.setCountryID(100 + i);

            int result = dao.update(order);

            Assert.assertEquals(1, result);
            OrderEntity pk = new OrderEntity();
            pk.setId(i);
            OrderEntity entity = dao.query(pk);
            Assert.assertEquals("banana", entity.getName());
            Assert.assertEquals(10 + i, entity.getCityID().intValue());
            Assert.assertEquals(100 + i, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testUpdateWithHints() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(10 + i);
            order.setCountryID(100 + i);

            int result = dao.update(order, new Hints().dbShardValue(i));

            OrderEntity pk = new OrderEntity();
            pk.setId(i);
            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(pk);
            Assert.assertEquals("banana", entity.getName());
            Assert.assertEquals(10 + i, entity.getCityID().intValue());
            Assert.assertEquals(100 + i, entity.getCountryID().intValue());

            order.setName("banana-new");
            order.setCityID(20 + i);
            order.setCountryID(200 + i);

            result = dao.update(order, new Hints().inDbShard(i % DB_MOD));
            Assert.assertEquals(1, result);
            entity = dao.query(pk);
            Assert.assertEquals("banana-new", entity.getName());
            Assert.assertEquals(20 + i, entity.getCityID().intValue());
            Assert.assertEquals(200 + i, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryObject() throws Exception {
        // query by (primary key)
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(i))
                    .hints(new Hints().dbShardValue(i)).into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder);
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(i, result.getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, result.getCountryID().intValue());
        }

        // query by (full values)
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(i), AND, order.city_id.equal(i), AND, order.country_id.equal(i * 10 + 1))
                    .hints(new Hints().inDbShard(i % DB_MOD)).into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder);
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(i, result.getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, result.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryObjectWithHints() throws Exception {
        // query by (primary key)
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(i))
                    .into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder, new Hints().dbShardValue(i));
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(i, result.getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, result.getCountryID().intValue());
        }

        // query by (full values)
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(i), AND, order.city_id.equal(i), AND, order.country_id.equal(i * 10 + 1))
                    .into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder, new Hints().inDbShard(i % DB_MOD));
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(i, result.getCityID().intValue());
            Assert.assertEquals(i * 10 + 1, result.getCountryID().intValue());
        }
    }

    @Ignore
    @Test
    public void testQueryList() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.name.equal("apple"), order.city_id.equal(i), order.country_id.equal(i * 10 + 1))
                    .into(OrderEntity.class);
            List<OrderEntity> results = dao.queryForList(builder);
            Assert.assertNotNull(results);
            Assert.assertEquals(TABLE_MOD, results.size());
            for (OrderEntity entity : results) {
                Assert.assertEquals("apple", entity.getName());
            }
        }
    }

    @Test
    public void testQueryListWithHints() throws Exception {
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.name.equal("apple"))
                    .hints(new Hints().dbShardValue(i))
                    .into(OrderEntity.class);
            List<OrderEntity> results = dao.queryForList(builder);
            Assert.assertNotNull(results);
            Assert.assertEquals(TABLE_MOD, results.size());
            for (OrderEntity entity : results) {
                Assert.assertEquals("apple", entity.getName());
            }
        }

        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.name.equal("apple"))
                    .into(OrderEntity.class);
            List<OrderEntity> results = dao.queryForList(builder, new Hints().inDbShard(i % DB_MOD));
            Assert.assertNotNull(results);
            Assert.assertEquals(TABLE_MOD, results.size());
            for (OrderEntity entity : results) {
                Assert.assertEquals("apple", entity.getName());
            }
        }
    }

    @Test
    public void testInsertList() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int i = 100; i < DB_MOD * 6 + 100; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(10);
            order.setCountryID(101);
            orders.add(order);
        }

        int[] results = dao.insert(orders);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        sample.setCityID(10);
        sample.setCountryID(101);
        for (int i = 0; i < DB_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inDbShard(i));
            Assert.assertEquals(DB_MOD * 3, entities.size());
        }
    }

    @Test
    public void testInsertListWithHints() throws Exception {
        List<OrderEntity> orders_db0 = new ArrayList<>();
        List<OrderEntity> orders_db1 = new ArrayList<>();
        for (int i = 100; i < DB_MOD * 6 + 100; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(10);
            order.setCountryID(101);
            if (i % DB_MOD == 0) {
                orders_db0.add(order);
            } else {
                orders_db1.add(order);
            }
        }

        int[] results = dao.insert(orders_db0, new Hints().inDbShard(0));
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.insert(orders_db1, new Hints().dbShardValue(1));
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        sample.setCityID(10);
        sample.setCountryID(101);
        for (int i = 0; i < DB_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inDbShard(i));
            Assert.assertEquals(DB_MOD * 3, entities.size());
        }
    }

    @Test
    public void testDeleteListByPk() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("apple");
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);
            orders.add(order);
        }

        int[] results = dao.delete(orders);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        for (int i = 0; i < DB_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inDbShard(i));
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testDeleteListByPkWithHints() throws Exception {
        List<OrderEntity> orders_db0 = new ArrayList<>();
        List<OrderEntity> orders_db1 = new ArrayList<>();
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("apple");
            order.setCityID(i);
            order.setCountryID(i * 10 + 1);
            if (i % DB_MOD == 0) {
                orders_db0.add(order);
            } else {
                orders_db1.add(order);
            }
        }

        int[] results = dao.delete(orders_db0, new Hints().inDbShard(0));
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.delete(orders_db1, new Hints().dbShardValue(1));
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        for (int i = 0; i < DB_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inDbShard(i));
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testDeleteListBySample() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("apple");
            orders.add(order);
        }

        int[] results = dao.deleteBySample(orders);
        Assert.assertEquals(DB_MOD * 3, results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        for (int i = 0; i < DB_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inDbShard(i));
            Assert.assertEquals(0, entities.size());
        }

        // 删除不存在的数据记录，返回结果为0
        results = dao.deleteBySample(orders);
        Assert.assertEquals(DB_MOD * 3, results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }
    }

    @Test
    public void testDeleteListBySampleWithHints() throws Exception {
        List<OrderEntity> orders_db0 = new ArrayList<>();
        List<OrderEntity> orders_db1 = new ArrayList<>();
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("apple");
            if (i % DB_MOD == 0) {
                orders_db0.add(order);
            } else {
                orders_db1.add(order);
            }
        }

        int[] results = dao.deleteBySample(orders_db0, new Hints().inDbShard(0));
        Assert.assertEquals(orders_db0.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.deleteBySample(orders_db1, new Hints().dbShardValue(1));
        Assert.assertEquals(orders_db1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        for (int i = 0; i < DB_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inDbShard(i));
            Assert.assertEquals(0, entities.size());
        }

        // 删除不存在的数据记录，返回结果为0
        results = dao.deleteBySample(orders_db0, new Hints().inDbShard(0));
        Assert.assertEquals(orders_db0.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }

        results = dao.deleteBySample(orders_db1, new Hints().dbShardValue(1));
        Assert.assertEquals(orders_db1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }
    }

    @Test
    public void testUpdateList() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(100);
            order.setCountryID(200);
            orders.add(order);
        }

        int[] results = dao.update(orders);
        Assert.assertEquals(orders.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        for (int i = 0; i < DB_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inDbShard(i));
            Assert.assertEquals(3, entities.size());
            for (OrderEntity entity : entities) {
                Assert.assertEquals(100, entity.getCityID().intValue());
                Assert.assertEquals(200, entity.getCountryID().intValue());
            }
        }
    }

    @Test
    public void testUpdateListWithHints() throws Exception {
        List<OrderEntity> orders_db0 = new ArrayList<>();
        List<OrderEntity> orders_db1 = new ArrayList<>();
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(101);
            order.setCountryID(202);
            if (i % DB_MOD == 0) {
                orders_db0.add(order);
            } else {
                orders_db1.add(order);
            }
        }

        int[] results = dao.update(orders_db0, new Hints().inDbShard(0));
        Assert.assertEquals(orders_db0.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.update(orders_db1, new Hints().dbShardValue(1));
        Assert.assertEquals(orders_db1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        for (int i = 0; i < DB_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inDbShard(i));
            Assert.assertEquals(3, entities.size());
            for (OrderEntity entity : entities) {
                Assert.assertEquals(101, entity.getCityID().intValue());
                Assert.assertEquals(202, entity.getCountryID().intValue());
            }
        }
    }

    @Test
    public void testUpdateListWithCrossShard() throws Exception {
        List<OrderEntity> orders_db0 = new ArrayList<>();
        List<OrderEntity> orders_db1 = new ArrayList<>();
        for (int i = 1; i < DB_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            if (i % DB_MOD == 0) {
                orders_db0.add(order);
            } else {
                orders_db1.add(order);
            }
        }

        // 当前表中无orders_db0的数据字段，因而更新结果为0
        int[] results = dao.update(orders_db0, new Hints().inDbShard(1));
        Assert.assertEquals(orders_db0.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }

        // 当前表中无orders_db1的数据字段，因而更新结果为0
        results = dao.update(orders_db1, new Hints().dbShardValue(0));
        Assert.assertEquals(orders_db1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }
    }
}
