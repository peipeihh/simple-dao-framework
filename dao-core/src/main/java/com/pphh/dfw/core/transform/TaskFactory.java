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

    public Task getQueryTask(String querySql, String dbName, Class pojoClz) {
        return new Task(SqlTaskType.ExecuteQuery, querySql, null, dbName, pojoClz);
    }

    public Task getCountTask(String querySql, String dbName) {
        return new Task(SqlTaskType.ExecuteQueryCount, querySql, null, dbName, null);
    }

    public Task getUpdateTask(String updateSql, String dbName, Class pojoClz) {
        return new Task(SqlTaskType.ExecuteUpdate, updateSql, null, dbName, pojoClz);
    }

    public Task getBatchQueryTask(List<String> querySqlList, String dbName, Class pojoClz) {
        return new Task(SqlTaskType.ExecuteBatchQuery, null, querySqlList, dbName, pojoClz);
    }

    public Task getBatchUpdateTask(List<String> updateSqlList, String dbName, Class pojoClz) {
        return new Task(SqlTaskType.ExecuteBatchUpdate, null, updateSqlList, dbName, pojoClz);
    }

}
