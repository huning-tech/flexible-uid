package com.langdashu.flexible.uid.core.model;

/**
 * uid配置信息
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidConfiguration {

    private Long snowflakeStartTimeMillis;
    private String zookeeperAddress;
    private UidEnvVariable envVariable;
    private UidStartupArg startupArg;
    private String redisAddress;

    public Long getSnowflakeStartTimeMillis() {
        return snowflakeStartTimeMillis;
    }

    public void setSnowflakeStartTimeMillis(Long snowflakeStartTimeMillis) {
        this.snowflakeStartTimeMillis = snowflakeStartTimeMillis;
    }

    public String getZookeeperAddress() {
        return zookeeperAddress;
    }

    public void setZookeeperAddress(String zookeeperAddress) {
        this.zookeeperAddress = zookeeperAddress;
    }

    public UidEnvVariable getEnvVariable() {
        return envVariable;
    }

    public void setEnvVariable(UidEnvVariable envVariable) {
        this.envVariable = envVariable;
    }

    public UidStartupArg getStartupArg() {
        return startupArg;
    }

    public void setStartupArg(UidStartupArg startupArg) {
        this.startupArg = startupArg;
    }

    public String getRedisAddress() {
        return redisAddress;
    }

    public void setRedisAddress(String redisAddress) {
        this.redisAddress = redisAddress;
    }

}
