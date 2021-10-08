package com.langdashu.flexible.uid.core.model;

import com.langdashu.flexible.uid.core.constant.Env;

/**
 * uid注册器上下文
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidRegistryContext {

    private String ip;
    private int port;
    private Env env;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Env getEnv() {
        return env;
    }

    public void setEnv(Env env) {
        this.env = env;
    }

}
