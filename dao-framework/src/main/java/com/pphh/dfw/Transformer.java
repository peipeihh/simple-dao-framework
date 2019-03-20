package com.pphh.dfw;

import com.pphh.dfw.core.IEntity;
import com.pphh.dfw.core.constant.SqlTaskType;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.core.jdbc.IDataSource;
import com.pphh.dfw.core.table.ITableField;
import com.pphh.dfw.core.transform.ITransformer;
import com.pphh.dfw.core.transform.Task;
import com.pphh.dfw.core.transform.TaskResult;
import com.pphh.dfw.jdbc.TomcatJdbcDataSource;
import com.pphh.dfw.table.GenericTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/15/2018
 */
public class Transformer implements ITransformer {

    final static Logger log = LoggerFactory.getLogger(Transformer.class);

    @Override
    public <T> TaskResult<T> run(Task task) throws Exception {
        String dbName = task.getDbName();

        Boolean isTrancOn = Transactioner.getInstance().isTransactionOn();
        Connection connection = null;
        if (isTrancOn) {
            connection = Transactioner.getInstance().getConnection(dbName);
            if (connection == null) {
                connection = Transactioner.getInstance().initConnection(dbName);
            }
        } else {
            connection = DataSourceManager.getInstance().getConnection(dbName);
        }

        TaskResult result = new TaskResult();
        try {

            SqlTaskType taskType = task.getTaskType();
            if (taskType == SqlTaskType.ExecuteQuery) {
                // query for result set
                PreparedStatement statement = connection.prepareStatement(task.getSql());
                ResultSet resultSet = statement.executeQuery();
                List<T> entities = convert(resultSet, task.getPojoClz());
                result.setEntities(entities);
                if (entities.size() > 0) {
                    result.setFirstEntity(entities.get(0));
                }
                resultSet.close();
                statement.close();
            } else if (taskType == SqlTaskType.ExecuteQueryCount) {
                // query by count
                PreparedStatement statement = connection.prepareStatement(task.getSql());
                ResultSet resultSet = statement.executeQuery();
                resultSet.last();
                int count = resultSet.getInt(1);
                result.setCount(count);
                resultSet.close();
                statement.close();
            } else if (taskType == SqlTaskType.ExecuteUpdate) {
                // a single update operation
                PreparedStatement statement = connection.prepareStatement(task.getSql());
                int rt = statement.executeUpdate();
                result.setResult(rt);
                statement.close();
            } else if (taskType == SqlTaskType.ExecuteBatchUpdate) {
                // a batch update operation
                Statement statement = connection.createStatement();
                List<String> sqls = task.getBatchSqls();
                for (String sql : sqls) {
                    statement.addBatch(sql);
                }
                int[] rts = statement.executeBatch();
                result.setResults(rts);
                statement.close();
            }

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            if (connection != null && !isTrancOn) {
                connection.close();
            }
        }

        return result;
    }


    private <T> List<T> convert(ResultSet rs, Class<? extends T> resultClz) throws SQLException {
        List<T> results = new ArrayList<>();

        EntityParser entityParser = new EntityParser();
        GenericTable table = entityParser.parse((Class<? extends IEntity>) resultClz);

        while (rs.next()) {
            try {
                T entity = resultClz.newInstance();

                List<ITableField> fields = table.getFields();
                for (ITableField field : fields) {
                    String fieldName = field.getFieldName();
                    String fieldDef = field.getFieldDefinition();
                    Type fieldType = field.getFieldType();

                    Field f = resultClz.getDeclaredField(fieldName);
                    setFieldValue(f, fieldDef, fieldType, entity, rs);
                }

                results.add(entity);
            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    private void setFieldValue(Field f, String fieldDef, Type fieldType, Object target, ResultSet rs) throws SQLException, IllegalAccessException {
        f.setAccessible(true);
        switch (fieldType.getTypeName()) {
            case "java.lang.String":
                f.set(target, rs.getString(fieldDef));
                break;
            case "java.lang.Short":
                f.set(target, rs.getShort(fieldDef));
                break;
            case "java.lang.Integer":
                f.set(target, rs.getInt(fieldDef));
                break;
            case "java.lang.Long":
                f.set(target, rs.getLong(fieldDef));
                break;
            case "java.lang.Float":
                f.set(target, rs.getFloat(fieldDef));
                break;
            case "java.lang.Double":
                f.set(target, rs.getDouble(fieldDef));
                break;
            case "java.sql.Date":
                f.set(target, rs.getDate(fieldDef));
                break;
            case "java.sql.Timestamp":
                f.set(target, rs.getTimestamp(fieldDef));
                break;
            case "java.sql.Time":
                f.set(target, rs.getTime(fieldDef));
                break;
            case "java.math.BigDecimal":
                f.set(target, rs.getBigDecimal(fieldDef));
                break;
            case "java.sql.Clob":
                f.set(target, rs.getClob(fieldDef));
                break;
            case "java.sql.Blob":
                f.set(target, rs.getBlob(fieldDef));
                break;
            case "java.net.URL":
                f.set(target, rs.getURL(fieldDef));
                break;
            case "java.lang.Byte":
                f.set(target, rs.getByte(fieldDef));
                break;
            case "java.lang.Byte[]":
                f.set(target, rs.getBytes(fieldDef));
                break;
            case "java.lang.Array":
                f.set(target, rs.getArray(fieldDef));
                break;
            case "java.lang.Object":
                f.set(target, rs.getObject(fieldDef));
                break;
            default:
        }
    }


}
