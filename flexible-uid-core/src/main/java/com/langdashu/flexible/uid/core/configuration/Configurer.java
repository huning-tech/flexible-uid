package com.langdashu.flexible.uid.core.configuration;

import com.langdashu.flexible.uid.core.constant.Env;
import com.langdashu.flexible.uid.core.constant.PropertiesConstant;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.io.ResourceLoader;
import com.langdashu.flexible.uid.core.model.UidConfiguration;
import com.langdashu.flexible.uid.core.model.UidEnvVariable;
import com.langdashu.flexible.uid.core.model.UidStartupArg;

import java.util.Properties;

/**
 * 配置器
 *
 * 加载配置文件 flexible-uid.properties
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class Configurer {

    private UidConfiguration cfg;

    private static final Configurer instance = new Configurer();

    private Configurer(){
    }

    public static Configurer getInstance() {
        return instance;
    }

    public synchronized UidConfiguration getConfiguration(Env env) throws UidException {
        if(cfg != null) {
            return cfg;
        }
        try{
            cfg = new UidConfiguration();
            Properties properties = ResourceLoader.getResourceAsProperties(PropertiesConstant.DEFAULT_FILE_NAME);
            String startTimeMillis = properties.getProperty(PropertiesConstant.KEY_SNOWFLAKE_START_TIME_MILLIS);
            cfg.setSnowflakeStartTimeMillis(Long.parseLong(startTimeMillis));
            cfg.setZookeeperAddress(properties.getProperty(PropertiesConstant.KEY_ZOOKEEPER_ADDRESS));
            UidEnvVariable envVariable = new UidEnvVariable();
            envVariable.setDataCenter(properties.getProperty(PropertiesConstant.KEY_ENV_VARIABLE_DATA_CENTER));
            cfg.setEnvVariable(envVariable);
            UidStartupArg startupArg = new UidStartupArg();
            startupArg.setDataCenter(properties.getProperty(PropertiesConstant.KEY_STARTUP_ARG_DATA_CENTER));
            cfg.setStartupArg(startupArg);
        }catch (Exception e) {
            throw new UidException(e);
        }
        return cfg;
    }

    public UidConfiguration getConfiguration() throws UidException {
        return getConfiguration(null);
    }

}
