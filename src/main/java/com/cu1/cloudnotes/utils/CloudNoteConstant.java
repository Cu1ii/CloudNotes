package com.cu1.cloudnotes.utils;

public interface CloudNoteConstant {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认状态的登录凭证超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 选择记住我的登录状态凭证超时时间
     */
    int REMEBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * 实体类型: 用户
     */
    int ENTITY_TYPE_USER = 3;
}
