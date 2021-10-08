package com.langdashu.flexible.uid.core.factory;

import com.langdashu.flexible.uid.core.constant.Algorithm;
import com.langdashu.flexible.uid.core.constant.ErrorMessage;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.specs.Uid;

import java.util.UUID;

/**
 * 抽象Uid
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public abstract class AbstractUid implements Uid {

    @Override
    public long getLong(Algorithm algorithm) throws UidException  {
        if(algorithm == Algorithm.UUID) {
            throw new UidException(ErrorMessage.INVALID_ALGORITHM);
        }
        if(algorithm == Algorithm.TIMESTAMP) {
            return System.currentTimeMillis();
        }
        return doGetLong(algorithm);
    }

    @Override
    public String getString(Algorithm algorithm) throws UidException {
        if(algorithm == Algorithm.UUID) {
            return UUID.randomUUID().toString();
        }
        return String.valueOf(getLong(algorithm));
    }

    public abstract long doGetLong(Algorithm algorithm) throws UidException;

}
