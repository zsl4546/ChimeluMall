package com.zsl.ResultMode;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:12
 * Description: 各种请求的参数
 */
public abstract class AbstractRequest implements Serializable {

    /**
     * 对参数校验
     */
    public abstract void check() throws Exception;

    @Override
    public String toString() {
        return "AbstractRequest toString";
    }
}
