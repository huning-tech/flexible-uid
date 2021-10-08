package com.langdashu.flexible.uid.core.specs;

import com.langdashu.flexible.uid.core.constant.Algorithm;
import com.langdashu.flexible.uid.core.exception.UidException;

/**
 * Uid模型
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public interface Uid {

    default long getLong() throws UidException {
        return getLong(Algorithm.SNOWFLAKE);
    }

    default String getString() throws UidException {
        return getString(Algorithm.SNOWFLAKE);
    }

    long getLong(Algorithm algorithm) throws UidException;

    String getString(Algorithm algorithm) throws UidException;

}
