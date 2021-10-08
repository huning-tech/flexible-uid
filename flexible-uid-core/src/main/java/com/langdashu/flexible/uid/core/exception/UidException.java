package com.langdashu.flexible.uid.core.exception;

/**
 * UID异常
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidException extends Exception {

    public UidException(){
        super();
    }

    public UidException(Throwable cause){
        super(cause);
    }

    public UidException(String message){
        super(message);
    }

    public UidException(String message, Throwable cause){
        super(message, cause);
    }

}
