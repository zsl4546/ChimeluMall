package com.zsl.account.constant;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:58
 * Description: No Description
 */
public enum  AccountResponseConstant {
    // 系统公用
    SUCCESS                             ("000000", "成功"),

    ACCOUNT_PASSWORD_OR_ACCOUNT_ERROR   ("000020", "账号或者密码错误"),

    ACCOUNT_CHECK_ERROR                 ("000021",  "账户验证有误"),

    ACCOUNT_REGIST_ERROE                ("000022", "注册未知错误"),

    ACCOUNT_NOT_FIND                    ("000030", "未找到token"),

    REQUISITE_PARAMETER_NOT_EXIST       ("000001", "必要的参数不能为空"),


    PIPELINE_RUN_EXCEPTION              ("000002","系统异常"),
    SHIPPING_DB_SAVED_FAILED            ("000003","物流信息保存数据库失败"),
    DB_SAVE_EXCEPTION                   ("000004","数据保存异常"),
    DB_EXCEPTION                        ("000005", "数据库异常"),
    SYSTEM_TIMEOUT                      ("000006", "系统超时"),
    SYSTEM_ERROR                        ("000007", "系统错误");
    private String code;
    private String message;

    AccountResponseConstant(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
