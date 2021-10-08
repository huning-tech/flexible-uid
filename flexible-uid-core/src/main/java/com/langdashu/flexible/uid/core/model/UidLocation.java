package com.langdashu.flexible.uid.core.model;

/**
 * uid定位符
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidLocation {

    public static final UidLocation DEFAULT = new UidLocation(-1, -1);

    private long dataCenterId;
    private long workerId;

    public UidLocation(long dataCenterId, long workerId) {
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return "UidLocation{" +
                "workerId=" + workerId +
                ", dataCenterId=" + dataCenterId +
                '}';
    }

}
