package com.langdashu.flexible.uid.core.factory;

import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.specs.Uid;

/**
 * Uid工厂
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public class UidFactory {

    private static UidFactory instance = new UidFactory();
    private volatile Uid uid;

    private UidFactory(){}

    public static UidFactory getInstance(){
        return instance;
    }

    public Uid uid() throws UidException {
        if(uid == null) {
            throw new UidException();
        }
        return uid;
    }

    public void uid(Uid uid) {
        if(this.uid != null) {
            return;
        }
        synchronized (UidFactory.class){
            if(this.uid != null) {
                return;
            }
            this.uid = uid;
        }
    }

}
