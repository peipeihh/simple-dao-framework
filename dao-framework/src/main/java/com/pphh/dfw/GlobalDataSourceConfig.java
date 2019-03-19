package com.pphh.dfw;

import com.pphh.dfw.config.DataSourceConfigLoader;
import com.pphh.dfw.config.LocalDSConfigLoader;
import com.pphh.dfw.config.RemoteDSConfigLoader;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.constant.ConfigTypeEnum;

import java.util.Map;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class GlobalDataSourceConfig implements IDataSourceConfig {
    private static IDataSourceConfig instance = new GlobalDataSourceConfig();
    private DataSourceConfigLoader loader = null;
    private Map<String, LogicDBConfig> logicDBConfigMap;
    private Map<String, PhysicalDBConfig> physicalDBConfigMap;

    private GlobalDataSourceConfig() {
    }

    public static IDataSourceConfig getInstance() {
        return instance;
    }

    @Override
    public GlobalDataSourceConfig load() throws Exception {
        String location = "local";

        if (this.loader == null) {
            if (ConfigTypeEnum.REMOTE.name().equalsIgnoreCase(location)) {
                this.loader = new RemoteDSConfigLoader();
            } else if (ConfigTypeEnum.LOCAL.name().equalsIgnoreCase(location)) {
                this.loader = new LocalDSConfigLoader();
            } else {
                this.loader = new LocalDSConfigLoader();
            }

            this.logicDBConfigMap = this.loader.loadLogic();
            this.physicalDBConfigMap = this.loader.loadPhysical();
        }

        return this;
    }

    @Override
    public LogicDBConfig getLogicDBConfig(String id) {
        return logicDBConfigMap.get(id);
    }

    @Override
    public PhysicalDBConfig getPhysicalDBConfigMap(String id) {
        return physicalDBConfigMap.get(id);
    }

}
