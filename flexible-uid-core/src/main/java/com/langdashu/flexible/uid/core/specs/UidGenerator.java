package com.langdashu.flexible.uid.core.specs;

import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.model.UidGeneratorInitParam;

/**
 * uid生成器
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public interface UidGenerator<T> {

    /**
     * 初始化
     * @param param 初始化参数
     * @throws UidException Uid模块异常
     */
    default void init(UidGeneratorInitParam param) throws UidException {
    }

    /**
     * 生成uid
     * @return uid
     * @throws UidException Uid模块异常
     */
    T generate() throws UidException;

    /**
     * 销毁
     * @throws UidException Uid模块异常
     */
    default void destroy() throws UidException {
    }

}
