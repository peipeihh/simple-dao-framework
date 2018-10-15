package com.pphh.dfw.core.ds;

import com.pphh.dfw.core.constant.SqlProviderEnum;

import java.util.Properties;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class PhysicalDBConfig {

    private String id;
    private String driverType;
    private String host;
    private String port;
    private String userName;
    private String userPwd;
    private String dbName;
    private String connectionUrl;
    private String connectionProperties;
    private Properties poolProperties = new Properties();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.poolProperties.setProperty("name", id);
        this.id = id;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.poolProperties.setProperty("userName", userName);
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.poolProperties.setProperty("password", userPwd);
        this.userPwd = userPwd;
    }

    public String getConnectionUrl() {
        String connectionUrl = null;
        if (SqlProviderEnum.MYSQL.name().equalsIgnoreCase(this.driverType)) {
            connectionUrl = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);
        } else if (SqlProviderEnum.SQLSERVER.name().equalsIgnoreCase(this.driverType)) {
            connectionUrl = String.format("jdbc:sqlserver://%s:%s;DatabaseName=%s", host, port, dbName);
        }
        return connectionUrl;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.poolProperties.setProperty("connectionProperties", connectionProperties);
        this.connectionProperties = connectionProperties;
    }

    public Properties getPoolProperties() {
        return (Properties) poolProperties.clone();
    }

    public void setPoolProperties(Properties poolProperties) {
        this.poolProperties.putAll(poolProperties);
    }

}
