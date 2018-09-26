package com.pphh.dfw;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/26/2018
 */
public class TestConnection {

    public static void testConnection(DataSource dataSource) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from `database0`.order");
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
