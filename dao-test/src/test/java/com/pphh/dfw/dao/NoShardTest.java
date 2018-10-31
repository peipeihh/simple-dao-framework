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
 * 无分库分表逻辑的DAO
 *
 * @author huangyinhuang
 * @date 10/17/2018
 */
public class NoShardTest extends BaseTest {

    private Dao dao;

    public NoShardTest() throws Exception {
        dao = DaoFactory.generate(LOGIC_DB_NO_SHARD);
    }

    @Before
    public void setUpCase() throws Exception {
        cleanUpTable();

        for (int k = 0; k < TABLE_MOD; k++) {
            String sql = String.format("INSERT INTO `order` (`id`, `name`, `city_id`, `country_id`) VALUES ('%s', '%s', '%s', '%s')", k + 1, "apple", k * 10 + 1, k * 100 + 1);
            ISqlBuilder sqlb = sqlBuilder().append(sql);
            noShardDao.run(sqlb);
        }
    }

    @After
    public void tearDownCase() {

    }

    @Test
    public void testQueryByPk() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);

            OrderEntity entity = dao.query(order);

            Assert.assertNotNull(entity);
            Assert.assertEquals(k + 1, entity.getId().intValue());
            Assert.assertEquals(k * 10 + 1, entity.getCityID().intValue());
            Assert.assertEquals(k * 100 + 1, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryWithHints() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);

            OrderEntity entity = dao.query(order, new Hints());

            Assert.assertNotNull(entity);
            Assert.assertEquals(k + 1, entity.getId().intValue());
            Assert.assertEquals(k * 10 + 1, entity.getCityID().intValue());
            Assert.assertEquals(k * 100 + 1, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryBySample() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setName("apple");
            order.setCityID(k * 10 + 1);
            order.setCountryID(k * 100 + 1);

            List<OrderEntity> entities = dao.queryBySample(order);

            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals("apple", entities.get(0).getName());
            Assert.assertEquals(k * 10 + 1, entities.get(0).getCityID().intValue());
            Assert.assertEquals(k * 100 + 1, entities.get(0).getCountryID().intValue());
        }
    }

    @Ignore
    @Test
    public void testCountBySample() throws Exception {
        OrderEntity order = new OrderEntity();
        order.setName("apple");
        long count = dao.countBySample(order);
        Assert.assertEquals(TABLE_MOD - 1, count);
    }

    @Test
    public void testInsert() throws Exception {
        OrderEntity order = new OrderEntity();
        order.setName("banana");
        order.setCityID(0);
        order.setCountryID(0);

        int result = dao.insert(order);

        Assert.assertEquals(1, result);
        List<OrderEntity> results = dao.queryBySample(order);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals("banana", results.get(0).getName());
        Assert.assertEquals(0, results.get(0).getCityID().intValue());
        Assert.assertEquals(0, results.get(0).getCountryID().intValue());
    }

    @Test
    public void testDelete() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);

            int result = dao.delete(order);

            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testDeleteWithHints() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);

            int result = dao.delete(order, new Hints().dbShardValue(k * 10 + 1).tableShardValue(k * 100 + 1));

            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testDeleteBySample() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            order.setName("apple");
            order.setCityID(k * 10 + 1);
            order.setCountryID(k * 100 + 1);

            int result = dao.deleteBySample(order);

            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testDeleteBySampleWithHints() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            order.setName("apple");

            int result = dao.deleteBySample(order, new Hints().dbShardValue(k * 10 + 1).tableShardValue(k * 100 + 1));

            Assert.assertEquals(1, result);
            OrderEntity entity = dao.query(order);
            Assert.assertNull(entity);
        }
    }

    @Test
    public void testUpdate() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            order.setName("banana");
            order.setCityID(k * 10 + 2);
            order.setCountryID(k * 100 + 2);

            int result = dao.update(order);

            Assert.assertEquals(1, result);
            OrderEntity pk = new OrderEntity();
            pk.setId(k + 1);
            OrderEntity entity = dao.query(pk);
            Assert.assertEquals(k + 1, entity.getId().intValue());
            Assert.assertEquals(k * 10 + 2, entity.getCityID().intValue());
            Assert.assertEquals(k * 100 + 2, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testUpdateWithHints() throws Exception {
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            order.setName("banana");
            int result = dao.update(order, new Hints().dbShardValue(k * 10 + 2).tableShardValue(k * 100 + 2));
            Assert.assertEquals(1, result);

            OrderEntity entity = dao.query(order);
            Assert.assertEquals(entity.getName(), "banana");
        }
    }

    @Test
    public void testQueryObject() throws Exception {
        // query by (primary key)
        for (int k = 0; k < TABLE_MOD; k++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(k + 1))
                    .into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder);
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
        }

        // query by (full values)
        for (int k = 0; k < TABLE_MOD; k++) {
            ISqlBuilder builder = select().from(order)
                    .where(order.id.equal(k + 1), AND, order.city_id.equal(k * 10 + 1), AND, order.country_id.equal(k * 100 + 1))
                    .hints(new Hints()).into(OrderEntity.class);
            OrderEntity result = dao.queryForObject(builder);
            Assert.assertNotNull(result);
            Assert.assertEquals("apple", result.getName());
            Assert.assertEquals(k * 10 + 1, result.getCityID().intValue());
            Assert.assertEquals(k * 100 + 1, result.getCountryID().intValue());
        }
    }

    @Test
    public void testQueryList() throws Exception {
        ISqlBuilder builder = select().from(order)
                .where(order.name.equal("apple"))
                .into(OrderEntity.class);
        List<OrderEntity> results = dao.queryForList(builder);
        Assert.assertNotNull(results);
        Assert.assertEquals(TABLE_MOD, results.size());
        for (OrderEntity entity : results) {
            Assert.assertEquals("apple", entity.getName());
        }
    }

    @Test
    public void testQueryListWithHints() throws Exception {
        ISqlBuilder builder = select().from(order)
                .where(order.name.equal("apple"))
                .hints(new Hints().dbShardValue(0).tableShardValue(0))
                .into(OrderEntity.class);
        List<OrderEntity> results = dao.queryForList(builder, new Hints().dbShardValue(0).tableShardValue(0));
        Assert.assertNotNull(results);
        Assert.assertEquals(TABLE_MOD, results.size());
        for (OrderEntity entity : results) {
            Assert.assertEquals("apple", entity.getName());
        }
    }

    @Test
    public void testInsertList() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setName("banana");
            order.setCityID(10);
            order.setCountryID(100);
            orders.add(order);
        }

        int[] results = dao.insert(orders);

        for (int result : results) {
            Assert.assertEquals(1, result);
        }
        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        sample.setCityID(10);
        sample.setCountryID(100);
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(TABLE_MOD, entities.size());
        for (OrderEntity entity : entities) {
            Assert.assertEquals("banana", entity.getName());
            Assert.assertEquals(10, entity.getCityID().intValue());
            Assert.assertEquals(100, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testInsertListWithHints() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setName("banana");
            orders.add(order);
        }

        int[] results = dao.insert(orders, new Hints());

        for (int result : results) {
            Assert.assertEquals(1, result);
        }
        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(TABLE_MOD, entities.size());
    }


    @Test
    public void testDeleteListByPk() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            order.setCityID(k * 10 + 1);
            order.setCountryID(k * 100 + 1);
            orders.add(order);
        }

        int[] results = dao.delete(orders);

        for (int result : results) {
            Assert.assertEquals(1, result);
        }
        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(0, entities.size());
    }

    @Test
    public void testDeleteListByPkWithHints() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            orders.add(order);
        }

        int[] results = dao.delete(orders, new Hints());

        for (int result : results) {
            Assert.assertEquals(1, result);
        }
        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(0, entities.size());
    }

    @Test
    public void testDeleteListBySample() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        orders.add(sample);

        int[] results = dao.deleteBySample(orders);

        Assert.assertEquals(1, results.length);
        Assert.assertEquals(3, results[0]);
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(0, entities.size());

        // 删除不存在的数据记录
        results = dao.deleteBySample(orders);
        Assert.assertEquals(1, results.length);
        Assert.assertEquals(0, results[0]);
    }

    @Test
    public void testDeleteListBySampleWithHints() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        OrderEntity sample = new OrderEntity();
        sample.setName("apple");
        orders.add(sample);

        int[] results = dao.deleteBySample(orders, new Hints());

        Assert.assertEquals(1, results.length);
        Assert.assertEquals(3, results[0]);
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(0, entities.size());

        // 删除不存在的数据记录，返回结果为0
        results = dao.deleteBySample(orders, new Hints());
        Assert.assertEquals(1, results.length);
        Assert.assertEquals(0, results[0]);
    }

    @Test
    public void testUpdateList() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            order.setName("banana");
            order.setCityID(10);
            order.setCountryID(100);
            orders.add(order);
        }

        int[] results = dao.update(orders);

        Assert.assertEquals(3, results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }
        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        sample.setCityID(10);
        sample.setCountryID(100);
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(3, entities.size());
        for (OrderEntity entity : entities) {
            Assert.assertEquals("banana", entity.getName());
            Assert.assertEquals(10, entity.getCityID().intValue());
            Assert.assertEquals(100, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testUpdateListWithHints() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int k = 0; k < TABLE_MOD; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            order.setCountryID(100);
            order.setName("banana");
            orders.add(order);
        }

        int[] results = dao.update(orders, new Hints());

        Assert.assertEquals(3, results.length);
        for (int result : results) {
            Assert.assertEquals(1, result);
        }
        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        sample.setCountryID(100);
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(3, entities.size());
        for (OrderEntity entity : entities) {
            Assert.assertEquals("banana", entity.getName());
            Assert.assertNotNull(entity.getCityID());
            Assert.assertEquals(100, entity.getCountryID().intValue());
        }
    }

    @Test
    public void testUpdateListNotExists() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        for (int k = 100; k < TABLE_MOD + 100; k++) {
            OrderEntity order = new OrderEntity();
            order.setId(k + 1);
            order.setName("banana");
            orders.add(order);
        }

        // 更新不存在的数据字段，返回结果为0
        int[] results = dao.update(orders, new Hints());

        Assert.assertEquals(3, results.length);
        for (int result : results) {
            Assert.assertEquals(0, result);
        }

        OrderEntity sample = new OrderEntity();
        sample.setName("banana");
        List<OrderEntity> entities = dao.queryBySample(sample);
        Assert.assertEquals(0, entities.size());
    }

}
