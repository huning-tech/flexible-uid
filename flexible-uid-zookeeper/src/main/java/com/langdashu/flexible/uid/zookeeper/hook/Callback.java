package com.langdashu.flexible.uid.zookeeper.hook;

/**
 * 回调接口
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public interface Callback<T> {

    T invoke();

}
