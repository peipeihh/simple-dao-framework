package com.pphh.dfw.core;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface ISqlBuilder {

    ISqlBuilder select();

    ISqlBuilder from();

    ISqlBuilder where();

    ISqlBuilder selectAll();

    ISqlBuilder selectCount();

    ISqlBuilder insertInto();

    ISqlBuilder values();

    ISqlBuilder update();

    ISqlBuilder set();

    ISqlBuilder deleteFrom();

    ISqlBuilder startTransaction();

    ISqlBuilder endTransaction();

    ISqlBuilder into(Class clazz);

    IHints getHints();

    String build();

    String buildOn(IDao dao);

}
