package com.tc.website.webapi.protocol;

/**
 * 错误码定义
 * @author Unicorn Lien
 * 2016年10月18日 下午11:18:38 创建
 */
public class ApiErrorCode {

    /**
     * 成功
     */
    public static final int OK = 0;

    /**
     *  未登录
     */
    public static final int UNAUTHORIZED = 40001;

    /**
     * 没有权限
     */
    public static final int PERMISSION_DENIED = 41000;

    /**
     * 请求参数错误
     */
    public static final int ILLEGAL_ARGUMENT = 42000;

    /**
     * 未找到资源
     */
    public static final int NOT_FOUND = 43000;

    /**
     * 资源占用
     */
    public static final int OCCUPY = 44000;

    /**
     * 失败
     */
    public static final int FAILED = 45000;

}
