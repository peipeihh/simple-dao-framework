package com.pphh.dfw;

import com.pphh.dfw.core.IDao;
import com.pphh.dfw.core.IHints;
import com.pphh.dfw.core.ISqlBuilder;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class SqlBuilder implements ISqlBuilder {


    @Override
    public ISqlBuilder select() {
        return null;
    }

    @Override
    public ISqlBuilder from() {
        return null;
    }

    @Override
    public ISqlBuilder where() {
        return null;
    }

    @Override
    public ISqlBuilder selectAll() {
        return null;
    }

    @Override
    public ISqlBuilder selectCount() {
        return null;
    }

    @Override
    public ISqlBuilder insertInto() {
        return null;
    }

    @Override
    public ISqlBuilder values() {
        return null;
    }

    @Override
    public ISqlBuilder update() {
        return null;
    }

    @Override
    public ISqlBuilder set() {
        return null;
    }

    @Override
    public ISqlBuilder deleteFrom() {
        return null;
    }

    @Override
    public ISqlBuilder startTransaction() {
        return null;
    }

    @Override
    public ISqlBuilder endTransaction() {
        return null;
    }

    @Override
    public ISqlBuilder into(Class clazz) {
        return null;
    }

    @Override
    public IHints getHints() {
        return null;
    }

    @Override
    public String build() {
        return null;
    }

    @Override
    public String buildOn(IDao dao) {
        return null;
    }
}
