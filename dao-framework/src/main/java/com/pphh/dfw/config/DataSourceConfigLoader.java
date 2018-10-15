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
public interface DataSourceConfigLoader {

    public Map<String, LogicDBConfig> loadLogic() throws Exception;

    public Map<String, PhysicalDBConfig> loadPhysical() throws Exception;

}
