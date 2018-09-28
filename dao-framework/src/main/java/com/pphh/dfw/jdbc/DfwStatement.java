package com.pphh.dfw.jdbc;

import com.pphh.dfw.core.jdbc.IStatement;

import java.sql.*;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/26/2018
 */
public class DfwStatement implements IStatement {

    private Connection connection;

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        //statement.setInt(1, 0);
        return statement.executeQuery();
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeUpdate();
    }

    @Override
    public int[] executeBatch(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeBatch();
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.execute(sql);
    }

}
