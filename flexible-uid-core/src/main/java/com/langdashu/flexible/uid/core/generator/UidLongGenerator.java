package com.langdashu.flexible.uid.core.generator;

import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.specs.UidGenerator;

/**
 * Long类型uid生成器
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public abstract class UidLongGenerator implements UidGenerator<Long> {

    @Override
    public Long generate() throws UidException {
        return generateLong();
    }

    public abstract Long generateLong() throws UidException;

}
