package com.pphh.dfw;

import com.pphh.dfw.core.IEntity;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.core.jdbc.IDataSource;
import com.pphh.dfw.core.table.ITableField;
import com.pphh.dfw.core.transform.ITransformer;
import com.pphh.dfw.core.transform.ShardTask;
import com.pphh.dfw.core.transform.ShardTaskResult;
import com.pphh.dfw.jdbc.TomcatDataSource;
import com.pphh.dfw.table.GenericTable;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/15/2018
 */
public class Transformer implements ITransformer {

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    @Override
    public int run(String sql, String dbName) throws Exception {
        PhysicalDBConfig shardPhysicalDBConfig = GlobalDataSourceConfig.getInstance().getPhysicalDBConfigMap(dbName);
        IDataSource dataSource = new TomcatDataSource(shardPhysicalDBConfig);
        Connection connection = connectionMap.get(dbName);
        if (connection == null) {
            connection = dataSource.getConnection();
            connectionMap.put(dbName, connection);
        }

        // execute
        PreparedStatement statement = connection.prepareStatement(sql);
        int result = statement.executeUpdate();
        statement.close();

        return result;
    }

    @Override
    public <T> List<T> run(String sql, String dbName, Class<? extends T> resultClz) throws Exception {
        PhysicalDBConfig shardPhysicalDBConfig = GlobalDataSourceConfig.getInstance().getPhysicalDBConfigMap(dbName);
        IDataSource dataSource = new TomcatDataSource(shardPhysicalDBConfig);
        Connection connection = connectionMap.get(dbName);
        if (connection == null) {
            connection = dataSource.getConnection();
            connectionMap.put(dbName, connection);
        }

        // execute
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        // collect results
        List<T> entities = convert(resultSet, resultClz);

        // close resource
        resultSet.close();
        statement.close();

        return entities;
    }

    @Override
    public <T> ShardTaskResult<T> run(ShardTask<T> task, Class<? extends IEntity> resultClz) {
        return null;
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
            case "java.lang.Integer":
                f.set(target, rs.getInt(fieldDef));
                break;
            case "java.lang.String":
                f.set(target, rs.getString(fieldDef));
                break;
            case "java.lang.Long":
                f.set(target, rs.getLong(fieldDef));
                break;
            case "java.sql.Date":
                f.set(target, rs.getDate(fieldDef));
            default:
        }
    }


}
