package com.zsl.constant;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:58
 * Description: No Description
 */
public enum CommodityResponseConstant {
    // 系统公用
    SUCCESS                             ("000000", "成功");

    private String code;
    private String message;

    CommodityResponseConstant(String code, String msg) {
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
