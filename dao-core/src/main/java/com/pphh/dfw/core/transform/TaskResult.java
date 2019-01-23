package com.pphh.dfw.core.transform;

import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class TaskResult<T> {

    // 单个sql执行结果，1表示成功，0表示失败
    private int result;
    // 批量sql执行结果
    private int[] results;
    // 查询sql获取的第一个记录实例对象
    private T firstEntity;
    // 查询sql获取的记录实例对象列表
    private List<T> entities;
    // 统计sql查询的结果，执行的sql样例：select count() from table
    private int count;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int[] getResults() {
        return results;
    }

    public void setResults(int[] results) {
        this.results = results;
    }

    public T getFirstEntity() {
        return firstEntity;
    }

    public void setFirstEntity(T firstEntity) {
        this.firstEntity = firstEntity;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
