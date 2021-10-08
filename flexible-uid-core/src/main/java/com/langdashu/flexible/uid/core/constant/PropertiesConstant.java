package com.langdashu.flexible.uid.core.constant;

/**
 * Properties文件及内容相关常量
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class PropertiesConstant {

    public final static String DEFAULT_FILE_NAME = "flexible-uid.properties";
    public final static String ENV_FILE_NAME = "flexible-uid-%s.properties";

    public final static String KEY_REGISTRY_ZOOKEEPER = "flexible.uid.registry.zookeeper";
    public final static String KEY_REGISTRY_REDIS = "flexible.uid.registry.redis";

    public final static String KEY_SNOWFLAKE_START_TIME_MILLIS = "snowflake.startTimeMillis";

    public final static String KEY_ZOOKEEPER_ADDRESS = "zookeeper.address";

    public final static String KEY_ENV_VARIABLE_DATA_CENTER = "env.variable.datacenter";
    public final static String KEY_STARTUP_ARG_DATA_CENTER = "startup.arg.datacenter";

}
