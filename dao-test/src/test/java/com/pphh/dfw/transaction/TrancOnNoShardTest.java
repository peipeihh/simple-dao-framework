package com.pphh.dfw.transaction;

import com.pphh.dfw.BaseTest;
import com.pphh.dfw.Dao;
import com.pphh.dfw.DaoFactory;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.dao.NoShardTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static com.pphh.dfw.sqlb.SqlStarter.sqlBuilder;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/30/2018
 */
public class TrancOnNoShardTest extends BaseTest {

    private Dao dao;
    private NoShardTest noShardTest;

    public TrancOnNoShardTest() throws Exception {
        dao = DaoFactory.create(LOGIC_DB_NO_SHARD);
        noShardTest = new NoShardTest();
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
    public void testSimpleTransaction() throws Exception {
        int rt = dao.execute((Consumer) t -> {
            try {
                noShardTest.testQueryByPk();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("An exception happened on query by pk.");
            }
        });
        Assert.assertEquals(0, rt);
    }

    @Test
    public void testTransactionRecurrsive() throws Exception {
        int rt = dao.execute((Consumer) t -> {
            try {
                transactionWrapper();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Assert.assertEquals(0, rt);

        noShardTest.testQueryByPk();
    }

    private void transactionWrapper() throws Exception {
        noShardTest.testQueryByPk();
        int rt = dao.execute((Consumer) t -> {
            try {
                noShardTest.testQueryByPk();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("An exception happened on query by pk in the transaction wrapper.");
            }
        });
        Assert.assertEquals(0, rt);
    }

    @Test
    public void testInsert() throws Exception {
        int rt = dao.execute((Consumer) t -> {
            try {
                noShardTest.testInsert();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("An exception happened on transaction execute - testInsert.");
            }
        });
        Assert.assertEquals(0, rt);
    }

    @Test
    public void testDelete() throws Exception {
        int rt = dao.execute((Consumer) t -> {
            try {
                noShardTest.testDelete();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("An exception happened on transaction execute - testDelete.");
            }
        });
        Assert.assertEquals(0, rt);
    }
}
