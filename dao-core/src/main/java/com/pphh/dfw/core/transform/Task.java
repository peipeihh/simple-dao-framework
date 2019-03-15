package com.pphh.dfw.core.transform;

import com.pphh.dfw.core.constant.SqlTaskType;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class Task {

    private Class pojoClz;
    private SqlTaskType taskType;
    private String sql;
    private List<String> batchSqls;
    private String dbName;
    private Function function;
    private Consumer consumer;

    public Task(SqlTaskType taskType, String sql, List<String> batchSqls, String dbName, Class pojoClz) {
        this.taskType = taskType;
        this.sql = sql;
        this.batchSqls = batchSqls;
        this.dbName = dbName;
        this.pojoClz = pojoClz;
    }

    public Class getPojoClz() {
        return pojoClz;
    }

    public void setPojoClz(Class pojoClz) {
        this.pojoClz = pojoClz;
    }

    public SqlTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(SqlTaskType taskType) {
        this.taskType = taskType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<String> getBatchSqls() {
        return batchSqls;
    }

    public void setBatchSqls(List<String> batchSqls) {
        this.batchSqls = batchSqls;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
