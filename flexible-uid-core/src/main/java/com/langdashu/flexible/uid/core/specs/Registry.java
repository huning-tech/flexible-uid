package com.langdashu.flexible.uid.core.specs;

import com.langdashu.flexible.uid.core.constant.SymbolConstant;
import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.model.UidLocation;
import com.langdashu.flexible.uid.core.model.UidRegistryContext;

import java.util.StringJoiner;

/**
 * 注册器
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public interface Registry {

    void init(UidRegistryContext context) throws UidException;

    UidLocation register(String ip, int port) throws UidException;

    default boolean available(UidGenerator generator) {
        return false;
    }

    default void destroy() throws UidException {}

    default String getServiceAddress(String ip, int port) {
        return getServiceAddress(ip, String.valueOf(port));
    }

    default String getServiceAddress(String ip, String port) {
        StringJoiner joiner = new StringJoiner(SymbolConstant.SYMBOL_COLON);
        joiner.add(ip);
        joiner.add(port);
        return joiner.toString();
    }

}
