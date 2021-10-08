package com.langdashu.flexible.uid.redis.registry;

import com.langdashu.flexible.uid.core.constant.ErrorMessage;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.model.UidLocation;
import com.langdashu.flexible.uid.core.model.UidRegistryContext;
import com.langdashu.flexible.uid.core.specs.Registry;

/**
 * Redis注册器
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class RedisRegistry implements Registry {

    public void init(UidRegistryContext context) throws UidException {
        throw new UidException(ErrorMessage.UNSUPPORTED);
    }

    public UidLocation register(String ip, int port) throws UidException {
        throw new UidException(ErrorMessage.UNSUPPORTED);
    }

}
