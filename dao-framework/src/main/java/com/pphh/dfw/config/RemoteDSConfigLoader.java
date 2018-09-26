package com.pphh.dfw.config;

import com.pphh.dfw.core.ds.LogicDBConfig;

import java.util.Map;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class RemoteDSConfigLoader implements DataSourceConfigLoader {

    @Override
    public Map<String, LogicDBConfig> load() {
        // read data source configuration from remote config server
        return null;
    }

}
