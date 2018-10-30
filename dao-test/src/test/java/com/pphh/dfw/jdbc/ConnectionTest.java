package com.pphh.dfw.jdbc;

import org.apache.tomcat.jdbc.pool.DataSource;

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

    public static void checkConnection(DataSource dataSource, String testSql) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(testSql);
            int cnt = 1;
            while (rs.next()) {
                System.out.println((cnt++)
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
