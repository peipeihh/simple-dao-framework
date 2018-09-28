package com.pphh.dfw.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/26/2018
 */
public interface IDataSource {

    public Connection getConnection() throws SQLException;

}
