package com.pphh.dfw.core.transform;

import com.pphh.dfw.core.constant.SqlTaskType;

import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 10/25/2018
 */
public class TaskFactory {
    private static TaskFactory ourInstance = new TaskFactory();

    private TaskFactory() {
    }

    public static TaskFactory getInstance() {
        return ourInstance;
    }

    public Task createSingleQueryTask(String querySql, String dbName, Class pojoClz) {
        return new Task(SqlTaskType.ExecuteQuery, querySql, null, dbName, pojoClz);
    }

    public Task createSingleUpdateTask(String updateSql, String dbName, Class pojoClz) {
        return new Task(SqlTaskType.ExecuteUpdate, updateSql, null, dbName, pojoClz);
    }

    public Task executeBatchQueryTask(List<String> querySqlList, String dbName, Class pojoClz) {
        return new Task(SqlTaskType.ExecuteBatchQuery, null, querySqlList, dbName, pojoClz);
    }

    public Task executeBatchUpdateTask(List<String> updateSqlList, String dbName, Class pojoClz) {
        return new Task(SqlTaskType.ExecuteBatchUpdate, null, updateSqlList, dbName, pojoClz);
    }

}
