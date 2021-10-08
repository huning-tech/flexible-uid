package com.langdashu.flexible.uid.core.generator;

import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.specs.UidGenerator;

/**
 * 字符串类型uid生成器
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public abstract class UidStringGenerator implements UidGenerator<String> {

    @Override
    public String generate() throws UidException {
        return generateString();
    }

    public abstract String generateString() throws UidException;

}
