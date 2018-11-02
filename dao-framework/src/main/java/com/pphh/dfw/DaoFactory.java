package com.pphh.dfw;

import com.pphh.dfw.core.ds.IDataSourceConfig;
import com.pphh.dfw.core.ds.LogicDBConfig;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class DaoFactory {

    private static Boolean isConfigLoaded = false;
    private static IDataSourceConfig instance = GlobalDataSourceConfig.getInstance();

    public static Dao create(String logicDb) throws Exception {
        if (!isConfigLoaded) {
            instance.load();
            isConfigLoaded = Boolean.TRUE;
        }

        LogicDBConfig logicDBConfig = instance.getLogicDBConfig(logicDb);
        return new Dao(logicDb);
    }

}
