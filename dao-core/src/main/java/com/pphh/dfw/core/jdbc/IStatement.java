package com.pphh.dfw.core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/26/2018
 */
public interface IStatement {

    ResultSet executeQuery(String sql) throws SQLException;

    int executeUpdate(String sql) throws SQLException;

    int[] executeBatch(String sql) throws SQLException;

    boolean execute(String sql) throws SQLException;

}
