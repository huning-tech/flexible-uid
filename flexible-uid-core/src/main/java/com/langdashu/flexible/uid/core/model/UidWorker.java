package com.langdashu.flexible.uid.core.model;

import java.util.List;

/**
 * uid应用进程信息
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidWorker {

    private List<String> ips;
    private int port;
    private List<String> serviceAddresses;

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getServiceAddresses() {
        return serviceAddresses;
    }

    public void setServiceAddresses(List<String> serviceAddresses) {
        this.serviceAddresses = serviceAddresses;
    }

    @Override
    public String toString() {
        return "UidWorker{" +
                "ips=" + ips +
                ", port='" + port + '\'' +
                ", serviceAddresses=" + serviceAddresses +
                '}';
    }

}
