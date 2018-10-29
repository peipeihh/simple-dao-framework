package com.pphh.dfw.core.transform;

import java.util.List;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class TaskResult<T> {

    private int result;
    private int[] results;
    private T firstEntity;
    private List<T> entities;

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
}
