package com.zsl.ResultMode;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:22
 * Description: 各种函数的返回
 */
@Data
public abstract class AbstractResponse implements Serializable {
    /**
     * 返回参数的状态码
     */
    String code;
    /**
     * 状态码对应的描述
     */
    String msg;
}
