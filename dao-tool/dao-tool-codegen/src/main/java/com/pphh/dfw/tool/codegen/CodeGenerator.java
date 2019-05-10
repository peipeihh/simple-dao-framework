package com.pphh.dfw.tool.codegen;

import com.pphh.dfw.tool.CodeGenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 代码生成器：根据表的元数据，生成相应的table entity/dao代码
 *
 * @author huangyinhuang
 * @date 2019/3/29
 */
public class CodeGenerator {

    private final static Logger log = LoggerFactory.getLogger(CodeGenerator.class);

    /**
     * 代码生成器，根据表的元数据，生成相应的table entity/dao代码
     *
     * @param conn      JDBC连接
     * @param tableName 表名
     * @return True说明生成代码成功，否则为False
     */
    public Boolean produceByJdbcConn(Connection conn, String tableName) {
        Boolean bSuccess = Boolean.FALSE;

        try {
            String catalog = conn.getCatalog();
            log.info("database = {}", catalog);

            DatabaseMetaData metaData = conn.getMetaData();
            Statement st = conn.createStatement();
            ResultSet rs = metaData.getTables(null, "%", "%", new String[]{"TABLE"});

            while (rs.next()) {
                String table = rs.getString("TABLE_NAME");
                if (tableName == null) {
                    produceByDbMeta(metaData, table);
                    bSuccess = Boolean.TRUE;
                } else if (tableName.equals(table)) {
                    produceByDbMeta(metaData, table);
                    bSuccess = Boolean.TRUE;
                    break;
                }
            }

            rs.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
            bSuccess = Boolean.FALSE;
        }

        return bSuccess;

    }

    private void produceByDbMeta(DatabaseMetaData metaData, String table) throws Exception {
        log.info("tablename = {}", table);

        ResultSet fieldRs = metaData.getColumns(null, "%", table, "%");
        List tableFields = new ArrayList();
        List tableFieldPks = new ArrayList();
        Map<String, String> tableFieldsType = new HashMap();
        while (fieldRs.next()) {
            String columnName = fieldRs.getString("COLUMN_NAME");
            String columnType = fieldRs.getString("TYPE_NAME");
            String columnSqlType = fieldRs.getString("DATA_TYPE");
            Integer columnSqlTypeInt = Types.OTHER;
            try {
                columnSqlTypeInt = Integer.parseInt(columnSqlType);
            } catch (NumberFormatException ignored) {
            }
            String columnJavaType = getColumnClassName(columnSqlTypeInt);

            tableFields.add(columnName);
            tableFieldsType.put(columnName, columnJavaType);
            log.info("column name = {}, type = {}, sql type = ({}) {}", columnName, columnType, columnSqlType, columnJavaType);
        }
        fieldRs.close();

        ResultSet pkRs = metaData.getPrimaryKeys(null, null, table);
        while (pkRs.next()) {
            String pkName = pkRs.getString("COLUMN_NAME");
            tableFieldPks.add(pkName);
            log.info("primary key = {}", pkName);
        }
        pkRs.close();

        CodeGenUtil.produceBy("pphh", table, tableFields, tableFieldPks, tableFieldsType);
    }

    /**
     * please see following doc reference,
     * -  https://dev.mysql.com/doc/connectors/en/connector-j-reference-type-conversions.html
     * <p>
     * the code is copied from
     * - javax.sql.rowset.RowSetMetaDataImpl.getColumnClassName()
     *
     * @param sqlType sql类型
     * @return 返回Java的数据类型
     * @throws SQLException
     */

    public String getColumnClassName(int sqlType) throws SQLException {
        String className = String.class.getName();

        switch (sqlType) {

            case Types.NUMERIC:
            case Types.DECIMAL:
                className = java.math.BigDecimal.class.getName();
                break;

            case Types.BIT:
                className = java.lang.Boolean.class.getName();
                break;

            case Types.TINYINT:
                className = java.lang.Byte.class.getName();
                break;

            case Types.SMALLINT:
                className = java.lang.Short.class.getName();
                break;

            case Types.INTEGER:
                className = java.lang.Integer.class.getName();
                break;

            case Types.BIGINT:
                className = java.lang.Long.class.getName();
                break;

            case Types.REAL:
                className = java.lang.Float.class.getName();
                break;

            case Types.FLOAT:
            case Types.DOUBLE:
                className = java.lang.Double.class.getName();
                break;

            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                className = "byte[]";
                break;

            case Types.DATE:
                className = java.sql.Date.class.getName();
                break;

            case Types.TIME:
                className = java.sql.Time.class.getName();
                break;

            case Types.TIMESTAMP:
                className = java.sql.Timestamp.class.getName();
                break;

            case Types.BLOB:
                className = java.sql.Blob.class.getName();
                break;

            case Types.CLOB:
                className = java.sql.Clob.class.getName();
                break;
        }

        return className;
    }
}
