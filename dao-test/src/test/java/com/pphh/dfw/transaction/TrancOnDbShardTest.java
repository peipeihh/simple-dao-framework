package com.pphh.dfw.transaction;

import com.pphh.dfw.*;
import com.pphh.dfw.core.exception.DfwException;
import com.pphh.dfw.core.function.DfwFunction;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.dao.DbShardTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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
        Assert.assertEquals(0, rt);
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
        Assert.assertEquals(0, rt);
    }

}
