package com.pphh.dfw;

import com.pphh.dfw.config.DataSourceConfigLoader;
import com.pphh.dfw.config.LocalDSConfigLoader;
import com.pphh.dfw.config.RemoteDSConfigLoader;
import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.constant.ConfigTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class GlobalDataSourceConfig implements IDataSourceConfig {

    private final static Logger log = LoggerFactory.getLogger(GlobalDataSourceConfig.class);

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
    public GlobalDataSourceConfig load() {
        String location = "local";

        if (this.loader == null) {
            if (ConfigTypeEnum.REMOTE.name().equalsIgnoreCase(location)) {
                this.loader = new RemoteDSConfigLoader();
            } else if (ConfigTypeEnum.LOCAL.name().equalsIgnoreCase(location)) {
                this.loader = new LocalDSConfigLoader();
            } else {
                this.loader = new LocalDSConfigLoader();
            }

            try {
                this.logicDBConfigMap = this.loader.loadLogic();
                this.physicalDBConfigMap = this.loader.loadPhysical();
            } catch (Exception e) {
                log.error("failed to load logic and physical db configuration, msg = {}", e.getMessage());
            }
        }

        return this;
    }

    @Override
    public LogicDBConfig getLogicDBConfig(String id) {
        LogicDBConfig dbConfig = null;
        if (this.logicDBConfigMap != null && id != null) {
            dbConfig = logicDBConfigMap.get(id);
        }
        return dbConfig;
    }

    @Override
    public PhysicalDBConfig getPhysicalDBConfigMap(String id) {
        PhysicalDBConfig dbConfig = null;
        if (this.physicalDBConfigMap != null && id != null) {
            dbConfig = physicalDBConfigMap.get(id);
        }
        return dbConfig;
    }

}
