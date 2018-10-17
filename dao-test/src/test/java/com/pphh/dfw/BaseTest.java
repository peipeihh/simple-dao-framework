package com.pphh.dfw;

import com.pphh.dfw.sqlb.SqlBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

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
    protected static final String LOGIC_DB_DB_TABLE_SHARD = "tableDbShard";
    protected static final String TABLE_NAME = Tables.ORDER.getName();
    protected SqlBuilder sqlBuilder;
    protected OrderTable order = Tables.ORDER;

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
                execute(LOGIC_DB_DB_TABLE_SHARD, statements[j], i, j);
            }
            statements[TABLE_MOD] = String.format("DELETE FROM `%s`", TABLE_NAME);
            execute(LOGIC_DB_NO_SHARD, statements[TABLE_MOD], 0, 0);
        }
    }

    private void execute(String logicDb, String sql, int dbShard, int tableShard) throws Exception {
        sqlBuilder = new SqlBuilder(logicDb);
        sqlBuilder.hints().inDbShard(dbShard).inTableShard(tableShard);
        sqlBuilder.append(sql).execute();
    }

}
