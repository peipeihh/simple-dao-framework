package com.pphh.dfw.config;

import com.pphh.dfw.core.ShardStrategy;
import com.pphh.dfw.core.ds.LogicDBConfig;
import com.pphh.dfw.core.ds.PhysicalDBConfig;
import com.pphh.dfw.support.BiFunctionThrow;
import com.pphh.dfw.utils.ConvertUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class LocalDSConfigLoader implements DataSourceConfigLoader {

    private static final String LOCAL_DB_CONFIG_FILE = "dfw-db-config.properties";
    private static final String LOCAL_DB_POOL_FILE = "dfw-db-pool.properties";


    @Override
    public Map<String, LogicDBConfig> load() throws Exception {
        Properties dbConfigProps = readProperty("/" + LOCAL_DB_CONFIG_FILE);
        Properties dbPoolProps = readProperty("/" + LOCAL_DB_POOL_FILE);

        // load logic db configuration
        List logicPrefixes = Stream.of(dbConfigProps.getProperty("logic.databases").split(","))
                .map(String::trim)
                .map(prefix -> String.format("logic.%s", prefix))
                .collect(Collectors.toList());
        List<LogicDBConfig> logicDBConfigList = parseArray(dbConfigProps, logicPrefixes, this::mapLogicDBConfig);

        Set phyPrefixes = new HashSet<>();
        Map<String, PhysicalDBConfig> physicalDBConfigMap = new ConcurrentHashMap<>();
        for (LogicDBConfig logicDb : logicDBConfigList) {
            List<String> dbEntries = logicDb.getDbEntries();
            for (String dbEntry : dbEntries) {
                // load physical db configuration
                if (!phyPrefixes.contains(dbEntry)) {
                    PhysicalDBConfig physicalDb = parseObject(dbConfigProps, String.format("physical.%s", dbEntry), this::mapPhysicalDBConfig);
                    physicalDb.setPoolProperties(dbPoolProps);
                    physicalDBConfigMap.put(dbEntry, physicalDb);
                    phyPrefixes.add(dbEntry);
                }

                // set physical db into logic db object
                PhysicalDBConfig physicalDb = physicalDBConfigMap.get(dbEntry);
                logicDb.setPhysicalDBConfig(dbEntry, physicalDb);
            }
        }

        return ConvertUtils.map(logicDBConfigList, LogicDBConfig::getId);
    }

    /**
     * 从配置列表中，根据所匹配的属性进行分组，通过map函数来解析出一组可用的对象列表
     *
     * @param propertyList 配置列表
     * @param prefixes     属性名，其值为通过逗号划分的一组属性列表
     * @param mapper       map函数
     * @param <R>          根据map函数定义返回的对象
     * @return 返回所解析的对象列表
     * @throws Exception 抛出出现的异常
     */
    private <R> List<R> parseArray(Properties propertyList, List<String> prefixes, BiFunctionThrow<Properties, String, R> mapper) throws Exception {
        List<R> list = new ArrayList<>();

        for (String prefix : prefixes) {
            R r = prefix.isEmpty() ? null : mapper.apply(propertyList, prefix);
            if (r != null) {
                list.add(r);
            }
        }

        return list;
    }

    private <R> R parseObject(Properties propertyList, String prefix, BiFunctionThrow<Properties, String, R> mapper) throws Exception {
        return prefix.isEmpty() ? null : mapper.apply(propertyList, prefix);
    }

    protected LogicDBConfig mapLogicDBConfig(Properties propertyList, String prefix) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        LogicDBConfig dbConfig = new LogicDBConfig();
        dbConfig.setId(prefix.replaceFirst("logic\\.", ""));
        String[] dbEntries = propertyList.getProperty(prefix + ".dbEntries").split(",");
        dbConfig.setDbEntries(Stream.of(dbEntries).map(String::trim).collect(Collectors.toList()));
        dbConfig.setDefaultDriverContext(propertyList.getProperty(prefix + ".defaultDriverContext"));

        String shardStrategy = propertyList.getProperty(prefix + ".shardStrategy");
        if (shardStrategy != null){
            ShardStrategy strategy = (ShardStrategy) Class.forName(shardStrategy).newInstance();
            Properties settings = new Properties();
            settings.setProperty("tableName", propertyList.getProperty(prefix + ".tableName"));
            settings.setProperty("tableSeparator", propertyList.getProperty(prefix + ".tableSeparator"));
            strategy.initialize(settings);
            dbConfig.setShardStrategy(strategy);
        }

        dbConfig.setDbShardColumn(propertyList.getProperty(prefix + ".dbShardColumn"));
        dbConfig.setTableShardColumn(propertyList.getProperty(prefix + ".tableShardColumn"));
        dbConfig.setTableName(propertyList.getProperty(prefix + ".tableName"));
        dbConfig.setTableSeparator(propertyList.getProperty(prefix + ".tableSeparator"));
        return dbConfig;
    }

    protected PhysicalDBConfig mapPhysicalDBConfig(Properties propertyList, String prefix) {
        PhysicalDBConfig dbConfig = new PhysicalDBConfig();
        dbConfig.setId(prefix.replaceFirst("pyshcial\\.", ""));
        dbConfig.setDriverType(propertyList.getProperty(prefix + ".driverType"));
        dbConfig.setHost(propertyList.getProperty(prefix + ".host"));
        dbConfig.setPort(propertyList.getProperty(prefix + ".port"));
        dbConfig.setUserName(propertyList.getProperty(prefix + ".userName"));
        dbConfig.setUserPwd(propertyList.getProperty(prefix + ".userPwd"));
        dbConfig.setDbName(propertyList.getProperty(prefix + ".dbName"));
        dbConfig.setInShard(propertyList.getProperty(prefix + ".inShard"));
        return dbConfig;
    }

    protected Properties readProperty(String filePath) {
        Properties props = new Properties();

        InputStream stream = LocalDSConfigLoader.class.getResourceAsStream(filePath);
        if (stream != null) {
            try {
                props.load(stream);
                stream.close();
            } catch (IOException ignore) {
            }
        }

        return props;
    }

}
