package com.pphh.dfw;

import com.pphh.dfw.core.dao.IDao;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static com.pphh.dfw.sqlb.SqlStarter.sqlBuilder;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/9/2018
 */
public abstract class BaseTest {

    protected static final int DB_MOD = 2;
    protected static final int TABLE_MOD = 3;
    protected static final String LOGIC_DB_NO_SHARD = "noShard";
    protected static final String LOGIC_DB_DB_SHARD = "dbShard";
    protected static final String LOGIC_DB_TABLE_SHARD = "tableShard";
    protected static final String LOGIC_DB_TABLE_DB_SHARD = "tableDbShard";
    protected static final String TABLE_NAME = Tables.ORDER.getName();
    protected ISqlBuilder builder;
    protected OrderTable order = Tables.ORDER;
    protected Dao noShardDao;
    protected Dao dbShardDao;
    protected Dao tableShardDao;
    protected Dao tableDbShardDao;

    public BaseTest() {
        try {
            noShardDao = DaoFactory.generate(LOGIC_DB_NO_SHARD);
            dbShardDao = DaoFactory.generate(LOGIC_DB_DB_SHARD);
            tableShardDao = DaoFactory.generate(LOGIC_DB_TABLE_SHARD);
            tableDbShardDao = DaoFactory.generate(LOGIC_DB_TABLE_DB_SHARD);
        } catch (Exception ignored) {
        }
    }

    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() {

    }

    protected void cleanUpTable() throws Exception {
        String[] statements = new String[TABLE_MOD + 1];
        for (int i = 0; i < DB_MOD; i++) {
            for (int j = 0; j < TABLE_MOD; j++) {
                statements[j] = String.format("DELETE FROM `%s_%s`", TABLE_NAME, j);
                execute(tableDbShardDao, statements[j], i, j);
            }
            statements[TABLE_MOD] = String.format("DELETE FROM `%s`", TABLE_NAME);
            execute(noShardDao, statements[TABLE_MOD], 0, 0);
        }
    }

    private void execute(IDao dao, String sql, int dbShard, int tableShard) throws Exception {
        dao.run(sqlBuilder().append(sql).hints(new Hints().inDbShard(dbShard).inTableShard(tableShard)));
    }

}
