package com.pphh.dfw;

import com.pphh.dfw.sqlb.SqlBuilder;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/9/2018
 */
public class BaseTest {

    protected SqlBuilder sqlBuilder;
    protected OrderTable order = Tables.ORDER;
    protected static final int DB_MOD = 2;
    protected static final int TABLE_MOD = 3;

}
