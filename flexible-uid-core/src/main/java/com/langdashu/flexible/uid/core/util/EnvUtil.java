package com.langdashu.flexible.uid.core.util;

import java.util.Map;

/**
 * 环境信息工具类
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class EnvUtil {

    public static String getEnvVariable(String name) {
        Map<String, String> envs = System.getenv();
        for(String key : envs.keySet()) {
           if(key.equals(name)) {
               return envs.get(key);
           }
        }
        return null;
    }

}
