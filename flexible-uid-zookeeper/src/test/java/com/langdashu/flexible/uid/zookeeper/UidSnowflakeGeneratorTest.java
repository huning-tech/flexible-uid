package com.langdashu.flexible.uid.zookeeper;

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

    @Test
    public void testUid4Snowflake() throws UidException {
        UidGeneratorFactory factory = UidGeneratorFactory.build(IpUtil.getIp(), 8080);
        Long uid = factory.getGenerator(UidSnowflakeGenerator.class).generate();
        logger.info("uid:{}", uid);
    }

}
