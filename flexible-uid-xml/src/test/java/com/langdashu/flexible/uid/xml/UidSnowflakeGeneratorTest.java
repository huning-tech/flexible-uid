package com.langdashu.flexible.uid.xml;

import com.langdashu.flexible.uid.core.constant.Env;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.factory.UidGeneratorFactory;
import com.langdashu.flexible.uid.core.generator.impl.UidSnowflakeGenerator;
import com.langdashu.flexible.uid.core.util.IpUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式雪花算法测试
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidSnowflakeGeneratorTest {

    private final Logger logger = LoggerFactory.getLogger(UidSnowflakeGeneratorTest.class);

    /**
     * 默认环境信息场景
     * @throws UidException uid异常
     */
    @Test
    public void testUid4Snowflake() throws UidException {
        logger.info("testUidSnowflake,ip:{}", IpUtil.getIp());
        UidGeneratorFactory factory = UidGeneratorFactory.build(IpUtil.getIp(), 8083);
        Long uid = factory.getGenerator(UidSnowflakeGenerator.class).generate();
        logger.info("uid:{}", uid);
    }

    /**
     * 指定环境信息场景
     * @throws UidException uid异常
     */
    @Test
    public void testUid4SnowflakeWithEnv() throws UidException {
        UidGeneratorFactory factory = UidGeneratorFactory.build(IpUtil.getIp(), 8888, Env.STG);
        Long uid = factory.getGenerator(UidSnowflakeGenerator.class).generate();
        logger.info("uid:{}", uid);
    }

}
