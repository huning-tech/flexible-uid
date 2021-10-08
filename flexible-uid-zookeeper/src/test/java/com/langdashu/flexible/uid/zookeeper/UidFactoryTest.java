package com.langdashu.flexible.uid.zookeeper;

import com.langdashu.flexible.uid.core.constant.Algorithm;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.factory.AbstractUid;
import com.langdashu.flexible.uid.core.factory.UidFactory;
import com.langdashu.flexible.uid.core.factory.UidGeneratorFactory;
import com.langdashu.flexible.uid.core.generator.impl.UidSnowflakeGenerator;
import com.langdashu.flexible.uid.core.specs.Uid;
import com.langdashu.flexible.uid.core.util.IpUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UidFactory测试
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidFactoryTest {

    private final Logger logger = LoggerFactory.getLogger(UidFactoryTest.class);

    @Before
    public void init() {

        UidFactory.getInstance().uid(new AbstractUid() {
            @Override
            public long doGetLong(Algorithm algorithm) throws UidException {
                UidGeneratorFactory factory = UidGeneratorFactory.build(IpUtil.getIp(), 8080);
                return factory.getGenerator(UidSnowflakeGenerator.class).generate();
            }
        });

    }

    @Test
    public void testUid() throws UidException {
        Uid uid = UidFactory.getInstance().uid();
        Assert.assertNotNull(uid);
        long longId = uid.getLong();
        logger.info("longId:{}", longId);
        String stringId = uid.getString();
        logger.info("stringId:{}", stringId);
        Assert.assertNotEquals(String.valueOf(longId), stringId);
    }

}
