package com.pphh.dfw.transaction;

import com.pphh.dfw.*;
import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.exception.DfwException;
import com.pphh.dfw.core.function.DfwFunction;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.dao.DbShardTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;

import static com.pphh.dfw.sqlb.SqlStarter.sqlBuilder;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 3/15/2019
 */
public class TrancOnDbShardTest extends BaseTest {

    private Dao dao;

    private DbShardTest dbShardTest;

    public TrancOnDbShardTest() throws Exception {
        dao = DaoFactory.create(LOGIC_DB_DB_SHARD);
        dbShardTest = new DbShardTest();
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
    public void testTrancQuery() throws Exception {
        int rt = dao.execute(() -> {
            try {
                dbShardTest.testQueryByPk();
            } catch (Exception e) {
                if (!(e instanceof DfwException)) {
                    Assert.fail();
                    e.printStackTrace();
                } else {
                    Assert.assertTrue("check the error message",
                            e.getMessage().contains("The transaction could not be applied on cross database"));
                }
            }
        });
        Assert.assertEquals(1, rt);
    }

    @Test
    public void testTrancInsert() throws Exception {
        int rt = dao.execute(() -> {
            try {
                dbShardTest.testInsert();
            } catch (Exception e) {
                if (!(e instanceof DfwException)) {
                    Assert.fail();
                    e.printStackTrace();
                } else {
                    Assert.assertTrue("check the error message",
                            e.getMessage().contains("The transaction could not be applied on cross database"));
                }
            }
        });
        Assert.assertEquals(1, rt);
    }

    @Test
    public void testTrancInsertByDbIdHint() throws Exception {
        IHints hints = new Hints().inDbShard(0);
        int rt = dao.execute(() -> this.testInsertOnSameShard(0), hints);
        Assert.assertEquals(1, rt);
    }

    @Test
    public void testTrancInsertByDbValueHint() throws Exception {
        IHints hints = new Hints().dbShardValue(1);
        int rt = dao.execute(() -> this.testInsertOnSameShard(1), hints);
        Assert.assertEquals(1, rt);
    }

    @Test
    public void testTrancInsertByDbIdHintByCrossError() throws Exception {
        IHints hints = new Hints().inDbShard(0);
        int rt = dao.execute(() -> {
            try {
                this.testInsertOnSameShard(1);
            } catch (Exception e) {
                if (!(e instanceof DfwException)) {
                    Assert.fail();
                    e.printStackTrace();
                } else {
                    Assert.assertTrue("check the error message",
                            e.getMessage().contains("The transaction could not be applied on cross database"));
                }
            }
        }, hints);
        Assert.assertEquals(1, rt);
    }

    @Test
    public void testTrancInsertByDbValueHintByCrossError() throws Exception {
        IHints hints = new Hints().dbShardValue(1);
        int rt = dao.execute(() -> {
            try {
                this.testInsertOnSameShard(0);
            } catch (Exception e) {
                if (!(e instanceof DfwException)) {
                    Assert.fail();
                    e.printStackTrace();
                } else {
                    Assert.assertTrue("check the error message",
                            e.getMessage().contains("The transaction could not be applied on cross database"));
                }
            }
        }, hints);
        Assert.assertEquals(1, rt);
    }

    public void testInsertOnSameShard(int dbShard) throws Exception {
        for (int i = 100; i < DB_MOD * 3 + 100; i++) {

            if (i % DB_MOD == dbShard) {
                OrderEntity order = new OrderEntity();
                order.setId(i);
                order.setCityID(i);
                order.setCountryID(i * 20 + 1);
                int result = dao.insert(order);
                Assert.assertEquals(1, result);
                List<OrderEntity> entities = dao.queryBySample(order);
                Assert.assertNotNull(entities);
                Assert.assertEquals(1, entities.size());
                Assert.assertEquals(i, entities.get(0).getCityID().intValue());
                Assert.assertEquals(i * 20 + 1, entities.get(0).getCountryID().intValue());
            }

        }
    }
}
