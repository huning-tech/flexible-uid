package com.langdashu.flexible.uid.core.algorithm;

import com.langdashu.flexible.uid.core.exception.UidException;

/**
 * 雪花算法
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class Snowflake {

    /**
     * 开始时间截
     */
    private long startTimeMillis = 1420041600000L;
    /**
     * 机器码位数(5bit)
     */
    private final long workerIdBits = 5L;
    /**
     * 数据中心码位数(5bit)
     */
    private final long dataCenterIdBits = 5L;
    /**
     * 支持的最大机器码(31)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 支持的最大数据中心码(31)
     */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    /**
     * 序列位数(12bit)
     */
    private final long sequenceBits = 12L;
    /**
     * 机器码左移12位
     */
    private final long workerIdShift = sequenceBits;
    /**
     * 数据中心码左移17位(12+5)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间截左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    /**
     * 生成序列的掩码4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 机器码(0~31)
     */
    private long workerId;
    /**
     * 数据中心码(0~31)
     */
    private long dataCenterId;
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     * @param dataCenterId 数据中心ID (0~31)
     * @param workerId     工作ID (0~31)
     */
    public Snowflake(long workerId, long dataCenterId) throws UidException {
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new UidException(String.format("data center Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new UidException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    public Snowflake(long workerId, long dataCenterId, long startTimeMillis) throws UidException {
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new UidException(String.format("data center Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new UidException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        this.startTimeMillis = startTimeMillis;
    }

    /**
     * 获得下一个ID
     * @return ID
     */
    public synchronized long nextId() throws UidException {
        long timestamp = getCurrentTimeMillis();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new UidException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = blockUntilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }
        // 上次生成ID的时间截
        lastTimestamp = timestamp;
        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - startTimeMillis) << timestampLeftShift) //
                | (dataCenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    private long blockUntilNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimeMillis();
        }
        return timestamp;
    }

    private long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

}
