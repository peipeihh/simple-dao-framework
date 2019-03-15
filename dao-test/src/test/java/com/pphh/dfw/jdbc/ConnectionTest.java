package com.pphh.dfw.jdbc;

import com.pphh.dfw.core.jdbc.IDataSource;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/17/2018
 */
public class ConnectionTest {

    private final static Logger log = LoggerFactory.getLogger(ConnectionTest.class);

    public static void checkDateSource(DataSource dataSource, String testSql) throws SQLException {
        Connection con = dataSource.getConnection();
        checkConnection(con, testSql);
    }


    public static void checkDateSource(IDataSource dataSource, String testSql) throws SQLException {
        Connection con = dataSource.getConnection();
        checkConnection(con, testSql);
    }

    private static void checkConnection(Connection con, String testSql) throws SQLException {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(testSql);
            int cnt = 1;
            while (rs.next()) {
                log.info((cnt++)
                        + ". id:" + rs.getString("id")
                        + " name:" + rs.getString("name")
                        + " city id:" + rs.getString("city_id"));
            }
            rs.close();
            st.close();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }

    }

}
