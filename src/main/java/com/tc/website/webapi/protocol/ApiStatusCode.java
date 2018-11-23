package com.tc.website.webapi.protocol;

/**
 * 业务响应码定义
 * @author Unicorn Lien
 * 2016年10月18日 下午11:18:38 创建
 */
public enum ApiStatusCode {
    /**
     * 成功
     */
    OK,

    /**
     * 未找到资源
     */
    NOT_FOUND,

    /**
     *  未登录
     */
    UNAUTHORIZED,

    /**
     * 没有权限
     */
    PERMISSION_DENIED,

    /**
     * 请求参数错误
     */
    ILLEGAL_ARGUMENT,

    /**
     * 失败
     */
    FAILED,

    /**
     * 资源占用
     */
    OCCUPY
}
