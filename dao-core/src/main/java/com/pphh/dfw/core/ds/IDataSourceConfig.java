package com.pphh.dfw.core.ds;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface IDataSourceConfig {

    public IDataSourceConfig load() throws Exception;

    public LogicDBConfig getLogicDBConfig(String id);

    public PhysicalDBConfig getPhysicalDBConfigMap(String id);

}
