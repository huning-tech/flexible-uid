package com.langdashu.flexible.uid.springboot.config;

import com.langdashu.flexible.uid.core.constant.Algorithm;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.factory.AbstractUid;
import com.langdashu.flexible.uid.core.factory.UidFactory;
import com.langdashu.flexible.uid.core.factory.UidGeneratorFactory;
import com.langdashu.flexible.uid.core.generator.impl.UidSnowflakeGenerator;
import com.langdashu.flexible.uid.core.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * SpringBoot自动配置
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
@Configuration
public class UidAutoConfiguration implements ApplicationListener<WebServerInitializedEvent>  {

    private static final Logger logger = LoggerFactory.getLogger(UidAutoConfiguration.class);

    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            final UidGeneratorFactory factory
                    = UidGeneratorFactory.build(IpUtil.getIp(), event.getWebServer().getPort());
            if(logger.isDebugEnabled()) {
                logger.debug("uid auto configure, ip:{}, port:{}", IpUtil.getIp(), event.getWebServer().getPort());
            }
            UidFactory.getInstance().uid(new AbstractUid() {
                @Override
                public long doGetLong(Algorithm algorithm) throws UidException {
                    return factory.getGenerator(UidSnowflakeGenerator.class).generateLong();
                }
            });
        } catch (UidException e) {
            logger.error("uid auto configure exception: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
