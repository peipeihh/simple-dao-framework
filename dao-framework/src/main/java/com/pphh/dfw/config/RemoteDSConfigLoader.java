package com.pphh.dfw.config;

import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;

import java.util.Map;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class RemoteDSConfigLoader implements DataSourceConfigLoader {

    @Override
    public Map<String, LogicDBConfig> loadLogic() throws Exception {
        return null;
    }

    @Override
    public Map<String, PhysicalDBConfig> loadPhysical() throws Exception {
        return null;
    }
}
