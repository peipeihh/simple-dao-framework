package com.pphh.dfw.core;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface IHints {

    IHints debug();

    IHints insertId();

    IHints setIdBack();

    IHints inDbShard();

    IHints inTableShard();

    IHints dbShardValue();

    IHints tableShardValue();

}
