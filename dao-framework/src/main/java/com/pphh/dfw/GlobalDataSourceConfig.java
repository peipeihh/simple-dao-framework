package com.pphh.dfw;

import com.pphh.dfw.config.DataSourceConfigLoader;
import com.pphh.dfw.config.LocalDSConfigLoader;
import com.pphh.dfw.config.RemoteDSConfigLoader;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.constant.ConfigTypeEnum;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class GlobalDataSourceConfig {
    private static GlobalDataSourceConfig instance = new GlobalDataSourceConfig();
    private Map<String, LogicDBConfig> logicDBConfigMap;
    private Map<String, PhysicalDBConfig> physicalDBConfigMap;

    private GlobalDataSourceConfig() {
    }

    public static GlobalDataSourceConfig getInstance() {
        return instance;
    }

    public GlobalDataSourceConfig load() throws Exception {
        String location = "local";

        DataSourceConfigLoader loader = null;
        if (ConfigTypeEnum.REMOTE.name().equalsIgnoreCase(location)) {
            loader = new RemoteDSConfigLoader();
        } else if (ConfigTypeEnum.LOCAL.name().equalsIgnoreCase(location)) {
            loader = new LocalDSConfigLoader();
        } else {
            loader = new LocalDSConfigLoader();
        }

        this.logicDBConfigMap = loader.load();
        this.physicalDBConfigMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, LogicDBConfig> entry : this.logicDBConfigMap.entrySet()) {
            LogicDBConfig logicDbConfig = entry.getValue();
            Set<String> idList = logicDbConfig.getPhysicalDBIdList();
            for (String id : idList) {
                PhysicalDBConfig physicalDbConfig = logicDbConfig.getPhysicalDBConfig(id);
                if (!this.physicalDBConfigMap.containsKey(id)) {
                    this.physicalDBConfigMap.put(id, physicalDbConfig);
                }
            }
        }

        return this;
    }

    public LogicDBConfig getLogicDBConfig(String id) {
        return logicDBConfigMap.get(id);
    }

    public PhysicalDBConfig getPhysicalDBConfigMap(String id) {
        return physicalDBConfigMap.get(id);
    }

}
