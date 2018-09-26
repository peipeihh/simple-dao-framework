package com.pphh.dfw.config;

import com.pphh.dfw.core.ds.LogicDBConfig;

import java.util.Map;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public interface DataSourceConfigLoader {

    public Map<String, LogicDBConfig> load() throws Exception;

}
