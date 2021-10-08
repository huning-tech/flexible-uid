package com.langdashu.flexible.uid.core;

import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.factory.UidGeneratorFactory;
import com.langdashu.flexible.uid.core.generator.impl.UidUUIDGenerator;
import com.langdashu.flexible.uid.core.util.IpUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UUIDGenerator测试
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidUUIDGeneratorTest {

    private final Logger logger = LoggerFactory.getLogger(UidUUIDGeneratorTest.class);

    /**
     * 测试UUID
     * @throws UidException uid异常
     */
    @Test
    public void testUUIDGenerator() throws UidException {
        UidGeneratorFactory factory = UidGeneratorFactory.build(IpUtil.getIp(), 8080);
        String uid = factory.getGenerator(UidUUIDGenerator.class).generate();
        logger.info("uid:{}", uid);
        Assert.assertNotNull(uid);
    }

}
