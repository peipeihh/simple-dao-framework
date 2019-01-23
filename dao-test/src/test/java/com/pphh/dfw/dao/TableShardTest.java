package com.pphh.dfw.dao;

import com.pphh.dfw.*;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.pphh.dfw.core.sqlb.SqlConstant.AND;
import static com.pphh.dfw.sqlb.SqlStarter.select;
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
        dao = DaoFactory.create(LOGIC_DB_TABLE_SHARD);
    }

    @Before
    public void setUpCase() throws Exception {
        cleanUpTable();

        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            String sql = String.format("INSERT INTO `order_%d` (`id`, `name`, `city_id`, `country_id`) VALUES ('%s', '%s', '%s', '%s')", j % TABLE_MOD, j, "apple", j * 10 + 1, j * 100 + 1);
            builder = sqlBuilder().append(sql).hints(new Hints().tableShardValue(j));
            tableShardDao.run(builder);
        }
    }

    @Test
    public void testSimpleQuery() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        OrderEntity entity = dao.query(orderEntity);
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getId().intValue());
        Assert.assertEquals(11, entity.getCityID().intValue());
        Assert.assertEquals(101, entity.getCountryID().intValue());
    }

    @Test
    public void testQueryByPk() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);

            OrderEntity entity = dao.query(order);

            Assert.assertNotNull(entity);
            Assert.assertEquals(j, entity.getId().intValue());
            Assert.assertEquals(j * 10 + 1, entity.getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryWithHints() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            OrderEntity entity = dao.query(order, new Hints().tableShardValue(j));
            Assert.assertNotNull(entity);
            Assert.assertEquals(j, entity.getId().intValue());
            Assert.assertEquals(j * 10 + 1, entity.getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, entity.getCountryID().intValue());
        }

        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            OrderEntity entity = dao.query(order, new Hints().inTableShard(j % TABLE_MOD));
            Assert.assertNotNull(entity);
            Assert.assertEquals(j, entity.getId().intValue());
            Assert.assertEquals(j * 10 + 1, entity.getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryBySample() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            order.setCityID(j * 10 + 1);
            order.setCountryID(j * 100 + 1);
            List<OrderEntity> entities = dao.queryBySample(order);
            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals(j, entities.get(0).getId().intValue());
            Assert.assertEquals(j * 10 + 1, entities.get(0).getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, entities.get(0).getCountryID().intValue());
        }
    }

    @Test
    public void testQueryBySampleWithHints() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setCityID(j * 10 + 1);
            order.setCountryID(j * 100 + 1);
            List<OrderEntity> entities = dao.queryBySample(order, new Hints().inTableShard(j % TABLE_MOD));
            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals(j, entities.get(0).getId().intValue());
            Assert.assertEquals(j * 10 + 1, entities.get(0).getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, entities.get(0).getCountryID().intValue());
        }

        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setCityID(j * 10 + 1);
            order.setCountryID(j * 100 + 1);
            List<OrderEntity> entities = dao.queryBySample(order, new Hints().tableShardValue(j));
            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals(j, entities.get(0).getId().intValue());
            Assert.assertEquals(j * 10 + 1, entities.get(0).getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, entities.get(0).getCountryID().intValue());
        }
    }

    @Test
    public void testCountBySample() throws Exception {

        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            order.setCityID(j * 10 + 1);
            order.setCountryID(j * 100 + 1);
            long count = dao.countBySample(order);
            Assert.assertEquals(1, count);
        }

        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setName("apple");
            long count = dao.countBySample(order, new Hints().tableShardValue(j));
            Assert.assertEquals(TABLE_MOD, count);
        }

        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setName("apple");
            long count = dao.countBySample(order, new Hints().inTableShard(j % TABLE_MOD));
            Assert.assertEquals(TABLE_MOD, count);
        }

    }

    @Test
    public void testInsert() throws Exception {
        for (int j = 100; j < TABLE_MOD * 3 + 100; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            order.setName("banana");
            order.setCityID(j * 10 + 1);
            order.setCountryID(j * 100 + 1);
            int result = dao.insert(order);
            Assert.assertEquals(1, result);
            List<OrderEntity> entities = dao.queryBySample(order);
            Assert.assertNotNull(entities);
            Assert.assertEquals(j, entities.get(0).getId().intValue());
            Assert.assertEquals("banana", entities.get(0).getName());
            Assert.assertEquals(j * 10 + 1, entities.get(0).getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, entities.get(0).getCountryID().intValue());
        }
    }

    @Test
    public void testDelete() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            order.setName("apple");

            int result = dao.delete(order);

            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testDeleteWithHints() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            int result = dao.delete(order, new Hints().tableShardValue(j));
            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testDeleteWithHints2() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            int result = dao.delete(order, new Hints().inTableShard(j % TABLE_MOD));
            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testDeleteBySample() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            order.setName("apple");
            order.setCityID(j * 10 + 1);
            order.setCountryID(j * 100 + 1);

            int result = dao.deleteBySample(order);

            Assert.assertEquals(1, result);
            List<OrderEntity> entities = dao.queryBySample(order);
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testDeleteBySampleWithHintsValue() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setName("apple");
            order.setCityID(j * 10 + 1);
            order.setCountryID(j * 100 + 1);

            int result = dao.deleteBySample(order, new Hints().tableShardValue(j));

            Assert.assertEquals(1, result);
            List<OrderEntity> entities = dao.queryBySample(order, new Hints().tableShardValue(j));
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testDeleteBySampleWithHintsShard() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setName("apple");
            order.setCityID(j * 10 + 1);
            order.setCountryID(j * 100 + 1);

            int result = dao.deleteBySample(order, new Hints().inTableShard(j % TABLE_MOD));

            Assert.assertEquals(1, result);
            List<OrderEntity> entities = dao.queryBySample(order, new Hints().inTableShard(j % TABLE_MOD));
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testUpdate() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            order.setName("banana");
            order.setCityID(j * 10 + 2);
            order.setCountryID(j * 100 + 2);

            int result = dao.update(order);

            Assert.assertEquals(1, result);
            OrderEntity pk = new OrderEntity();
            pk.setId(j);
            OrderEntity entity = dao.query(pk);
            Assert.assertEquals("banana", entity.getName());
            Assert.assertEquals(j * 10 + 2, entity.getCityID().intValue());
            Assert.assertEquals(j * 100 + 2, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testUpdateWithHints() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            OrderEntity order = new OrderEntity();
            order.setId(j);
            order.setName("banana");
            order.setCityID(j * 10 + 2);
            order.setCountryID(j * 100 + 2);

            int result = dao.update(order, new Hints().tableShardValue(j));

            OrderEntity pk = new OrderEntity();
            pk.setId(j);
            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(pk);
            Assert.assertEquals("banana", entity.getName());
            Assert.assertEquals(j * 10 + 2, entity.getCityID().intValue());
            Assert.assertEquals(j * 100 + 2, entity.getCountryID().intValue());

            order.setName("banana-new");
            order.setCityID(j * 10 + 3);
            order.setCountryID(j * 100 + 3);

            result = dao.update(order, new Hints().inTableShard(j % TABLE_MOD));
            Assert.assertEquals(1, result);
            entity = dao.query(pk);
            Assert.assertEquals("banana-new", entity.getName());
            Assert.assertEquals(j * 10 + 3, entity.getCityID().intValue());
            Assert.assertEquals(j * 100 + 3, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryObject() throws Exception {
        // query by (primary key)
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(j))
                    .hints(new Hints().tableShardValue(j)).into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder);
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(j * 10 + 1, result.getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, result.getCountryID().intValue());
        }

        // query by (full values)
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(j), AND, order.city_id.equal(j * 10 + 1), AND, order.country_id.equal(j * 100 + 1))
                    .hints(new Hints().inTableShard(j % TABLE_MOD)).into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder);
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(j * 10 + 1, result.getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, result.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryObjectWithHints() throws Exception {
        // query by (primary key)
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(j))
                    .into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder, new Hints().tableShardValue(j));
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(j * 10 + 1, result.getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, result.getCountryID().intValue());
        }

        // query by (full values)
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(j), AND, order.city_id.equal(j * 10 + 1), AND, order.country_id.equal(j * 100 + 1))
                    .into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder, new Hints().inTableShard(j % TABLE_MOD));
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(j * 10 + 1, result.getCityID().intValue());
            Assert.assertEquals(j * 100 + 1, result.getCountryID().intValue());
        }
    }

    @Ignore
    @Test
    public void testQueryList() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(j), AND, order.city_id.equal(j * 10 + 1), AND, order.country_id.equal(j * 100 + 1))
                    .into(OrderEntity.class);
            List<OrderEntity> results = dao.queryForList(builder);
            Assert.assertNotNull(results);
            Assert.assertEquals(TABLE_MOD, results.size());
            for (OrderEntity entity : results) {
                Assert.assertEquals("apple", entity.getName());
                Assert.assertEquals(j * 10 + 1, entity.getCityID().intValue());
                Assert.assertEquals(j * 100 + 1, entity.getCountryID().intValue());
            }
        }
    }

    @Test
    public void testQueryListWithHints() throws Exception {
        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.name.equal("apple"))
                    .hints(new Hints().tableShardValue(j))
                    .into(OrderEntity.class);
            List<OrderEntity> results = dao.queryForList(builder);
            Assert.assertNotNull(results);
            Assert.assertEquals(TABLE_MOD, results.size());
            for (OrderEntity entity : results) {
                Assert.assertEquals("apple", entity.getName());
            }
        }

        for (int j = 1; j < TABLE_MOD * 3 + 1; j++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.name.equal("apple"))
                    .into(OrderEntity.class);
            List<OrderEntity> results = dao.queryForList(builder, new Hints().inTableShard(j % TABLE_MOD));
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
        for (int i = 100; i < TABLE_MOD * 6 + 100; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(i * 10 + 2);
            order.setCountryID(i * 100 + 2);
            orders.add(order);
        }

        int[] results = dao.insert(orders);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        for (int i = 0; i < TABLE_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inTableShard(i));
            Assert.assertEquals(TABLE_MOD * 2, entities.size());
        }
    }

    @Test
    public void testInsertListWithHints() throws Exception {
        List<OrderEntity> orders_table0 = new ArrayList<>();
        List<OrderEntity> orders_table1 = new ArrayList<>();
        List<OrderEntity> orders_table2 = new ArrayList<>();
        for (int i = 100; i < TABLE_MOD * 6 + 100; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(10);
            order.setCountryID(101);
            if (i % TABLE_MOD == 0) {
                orders_table0.add(order);
            } else if (i % TABLE_MOD == 1) {
                orders_table1.add(order);
            } else {
                orders_table2.add(order);
            }
        }

        int[] results = dao.insert(orders_table0, new Hints().inTableShard(0));
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.insert(orders_table1, new Hints().inTableShard(1));
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.insert(orders_table2, new Hints().inTableShard(2));
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        sample.setCityID(10);
        sample.setCountryID(101);
        for (int i = 0; i < TABLE_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inTableShard(i));
            Assert.assertEquals(TABLE_MOD * 2, entities.size());
        }
    }

    @Test
    public void testDeleteListByPk() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int i = 1; i < TABLE_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            orders.add(order);
        }

        int[] results = dao.delete(orders);
        Assert.assertEquals(TABLE_MOD * 3, results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        for (int i = 0; i < TABLE_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inTableShard(i));
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testDeleteListByPkWithHints() throws Exception {
        List<OrderEntity> orders_table0 = new ArrayList<>();
        List<OrderEntity> orders_table1 = new ArrayList<>();
        List<OrderEntity> orders_table2 = new ArrayList<>();
        for (int i = 1; i < TABLE_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("apple");
            order.setCityID(i * 10 + 1);
            order.setCountryID(i * 100 + 1);
            if (i % TABLE_MOD == 0) {
                orders_table0.add(order);
            } else if (i % TABLE_MOD == 1) {
                orders_table1.add(order);
            } else {
                orders_table2.add(order);
            }
        }

        int[] results = dao.delete(orders_table0, new Hints().inTableShard(0));
        Assert.assertEquals(3, results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.delete(orders_table1, new Hints().tableShardValue(1));
        Assert.assertEquals(3, results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.delete(orders_table2, new Hints().inTableShard(2));
        Assert.assertEquals(3, results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        for (int i = 0; i < TABLE_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inTableShard(i));
            Assert.assertEquals(0, entities.size());
        }
    }

    @Test
    public void testDeleteListBySample() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int i = 1; i < TABLE_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("apple");
            order.setCityID(i * 10 + 1);
            order.setCountryID(i * 100 + 1);
            orders.add(order);
        }

        int[] results = dao.deleteBySample(orders);
        Assert.assertEquals(TABLE_MOD * 3, results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        for (int i = 0; i < TABLE_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inTableShard(i));
            Assert.assertEquals(0, entities.size());
        }

        // 删除不存在的数据记录，返回结果为0
        results = dao.deleteBySample(orders);
        Assert.assertEquals(TABLE_MOD * 3, results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }
    }

    @Test
    public void testDeleteListBySampleWithHints() throws Exception {
        List<OrderEntity> orders_table0 = new ArrayList<>();
        List<OrderEntity> orders_table1 = new ArrayList<>();
        List<OrderEntity> orders_table2 = new ArrayList<>();
        for (int i = 1; i < TABLE_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("apple");
            if (i % TABLE_MOD == 0) {
                orders_table0.add(order);
            } else if (i % TABLE_MOD == 1) {
                orders_table1.add(order);
            } else {
                orders_table2.add(order);
            }
        }

        int[] results = dao.deleteBySample(orders_table0, new Hints().inTableShard(0));
        Assert.assertEquals(orders_table0.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.deleteBySample(orders_table1, new Hints().tableShardValue(1));
        Assert.assertEquals(orders_table1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.deleteBySample(orders_table2, new Hints().inTableShard(2));
        Assert.assertEquals(orders_table2.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        for (int i = 0; i < TABLE_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inTableShard(i));
            Assert.assertEquals(0, entities.size());
        }

        // 删除不存在的数据记录，返回结果为0
        results = dao.deleteBySample(orders_table0, new Hints().inTableShard(0));
        Assert.assertEquals(orders_table0.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }

        results = dao.deleteBySample(orders_table1, new Hints().tableShardValue(1));
        Assert.assertEquals(orders_table1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }

        results = dao.deleteBySample(orders_table2, new Hints().inTableShard(2));
        Assert.assertEquals(orders_table2.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }
    }

    @Test
    public void testUpdateList() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int i = 1; i < TABLE_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(101);
            order.setCountryID(201);
            orders.add(order);
        }

        int[] results = dao.update(orders);
        Assert.assertEquals(orders.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        for (int i = 0; i < TABLE_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inTableShard(i));
            Assert.assertEquals(3, entities.size());
            for (OrderEntity entity : entities) {
                Assert.assertEquals(101, entity.getCityID().intValue());
                Assert.assertEquals(201, entity.getCountryID().intValue());
            }
        }
    }

    @Test
    public void testUpdateListWithHints() throws Exception {
        List<OrderEntity> orders_table0 = new ArrayList<>();
        List<OrderEntity> orders_table1 = new ArrayList<>();
        List<OrderEntity> orders_table2 = new ArrayList<>();
        for (int i = 1; i < TABLE_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            order.setCityID(102);
            order.setCountryID(202);
            if (i % TABLE_MOD == 0) {
                orders_table0.add(order);
            } else if (i % TABLE_MOD == 1) {
                orders_table1.add(order);
            } else {
                orders_table2.add(order);
            }
        }

        int[] results = dao.update(orders_table0, new Hints().inTableShard(0));
        Assert.assertEquals(orders_table0.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.update(orders_table1, new Hints().tableShardValue(1));
        Assert.assertEquals(orders_table1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        results = dao.update(orders_table2, new Hints().inTableShard(2));
        Assert.assertEquals(orders_table2.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        for (int i = 0; i < TABLE_MOD; i++) {
            List<OrderEntity> entities = dao.queryBySample(sample, new Hints().inTableShard(i));
            Assert.assertEquals(3, entities.size());
            for (OrderEntity entity : entities) {
                Assert.assertEquals(102, entity.getCityID().intValue());
                Assert.assertEquals(202, entity.getCountryID().intValue());
            }
        }
    }

    @Test
    public void testUpdateListWithCrossShard() throws Exception {
        List<OrderEntity> orders_table0 = new ArrayList<>();
        List<OrderEntity> orders_table1 = new ArrayList<>();
        for (int i = 1; i < TABLE_MOD * 3 + 1; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setName("banana");
            if (i % TABLE_MOD == 0) {
                orders_table0.add(order);
            } else if (i % TABLE_MOD == 1) {
                orders_table1.add(order);
            }
        }

        // 当前表中无orders_table1的数据字段，因而更新结果为0
        int[] results = dao.update(orders_table1, new Hints().inTableShard(0));
        Assert.assertEquals(orders_table1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }

        // 当前表中无orders_table0的数据字段，因而更新结果为0
        results = dao.update(orders_table0, new Hints().tableShardValue(1));
        Assert.assertEquals(orders_table0.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }

        // 当前表中无orders_table1的数据字段，因而更新结果为0
        results = dao.update(orders_table1, new Hints().tableShardValue(2));
        Assert.assertEquals(orders_table1.size(), results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }
    }

}
