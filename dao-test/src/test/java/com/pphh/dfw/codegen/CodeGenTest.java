package com.pphh.dfw.codegen;

import com.pphh.dfw.GlobalDataSourceConfig;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.core.jdbc.IDataSource;
import com.pphh.dfw.jdbc.TomcatJdbcDataSource;
import com.pphh.dfw.tool.codegen.CodeGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/5/5
 */
public class CodeGenTest {

    @Test
    public void testSimple() throws Exception {
        IDataSourceConfig instance = GlobalDataSourceConfig.getInstance().load();
        PhysicalDBConfig shardPhysicalDBConfig = instance.getPhysicalDBConfigMap("db1");

        Connection conn = null;
        try {
            IDataSource dataSource = new TomcatJdbcDataSource(shardPhysicalDBConfig);
            conn = dataSource.getConnection();

            CodeGenerator codeGenerator = new CodeGenerator();
            Boolean bSuccess = codeGenerator.produceByJdbcConn(conn, null);
            Assert.assertTrue("succeeded to produce entity/table class for all tables", bSuccess);
            bSuccess = codeGenerator.produceByJdbcConn(conn, "full_type_mysql");
            Assert.assertTrue("succeeded to produce entity/table class for full_type_mysql table", bSuccess);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("failed to use code generator to produce entity/table class.");
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (Exception ignore) {
            }
        }
    }

}
