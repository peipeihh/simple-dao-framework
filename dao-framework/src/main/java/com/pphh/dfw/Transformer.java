package com.pphh.dfw;

import com.pphh.dfw.core.IEntity;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.core.jdbc.IDataSource;
import com.pphh.dfw.core.transform.ITransformer;
import com.pphh.dfw.core.transform.ShardTask;
import com.pphh.dfw.core.transform.ShardTaskResult;
import com.pphh.dfw.jdbc.TomcatDataSource;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/15/2018
 */
public class Transformer implements ITransformer {

    @Override
    public <T> List<T> run(String sql, String dbName, Class<? extends T> resultClz) throws Exception {
        PhysicalDBConfig shardPhysicalDBConfig = GlobalDataSourceConfig.getInstance().getPhysicalDBConfigMap(dbName);

        // execute
        IDataSource dataSource = new TomcatDataSource(shardPhysicalDBConfig);
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        // collect results
        List<T> entities = convert(resultSet, resultClz);

        // close resource
        resultSet.close();
        statement.close();
        connection.close();

        return entities;
    }

    @Override
    public <T> ShardTaskResult<T> run(ShardTask<T> task, Class<? extends IEntity> resultClz) {
        return null;
    }

    private <T> List<T> convert(ResultSet rs, Class<? extends T> resultClz) throws SQLException {
        List<T> results = new ArrayList<>();

        while (rs.next()) {
            try {
                T entity = resultClz.newInstance();

                Field f = resultClz.getDeclaredField("id");
                f.setAccessible(true);
                f.set(entity, rs.getInt("id"));

                f = resultClz.getDeclaredField("name");
                f.setAccessible(true);
                f.set(entity, rs.getString("name"));

                results.add(entity);
            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

}
