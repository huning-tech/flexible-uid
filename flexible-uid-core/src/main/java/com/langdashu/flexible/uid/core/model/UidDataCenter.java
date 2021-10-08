package com.langdashu.flexible.uid.core.model;

import java.util.List;

/**
 * uid数据中心
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidDataCenter {

    private String code;
    private String group;
    private List<UidWorker> uidWorkers;

    public UidDataCenter() {}

    public UidDataCenter(String code) {
        this.code = code;
    }

    public UidDataCenter(String code, String group) {
        this.code = code;
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<UidWorker> getWorkers() {
        return uidWorkers;
    }

    public void setWorkers(List<UidWorker> uidWorkers) {
        this.uidWorkers = uidWorkers;
    }

    @Override
    public String toString() {
        return "UidDataCenter{" +
                "code='" + code + '\'' +
                ", group='" + group + '\'' +
                ", workers=" + uidWorkers +
                '}';
    }

}
