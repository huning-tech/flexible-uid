package com.langdashu.flexible.uid.core.constant;

/**
 * 环境标识
 *
 * <p>更多内容参看<a href="https://langdashu.com"><b>浪大叔</b></a>
 * @author 浪大叔
 */
public enum Env {

    LOCAL("local"),
    DEV("dev"),
    TEST("test"),
    STG("stg"),
    SIT("sit"),
    PRD("prd"),
    NONE("none");

    private String code;

    Env(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
