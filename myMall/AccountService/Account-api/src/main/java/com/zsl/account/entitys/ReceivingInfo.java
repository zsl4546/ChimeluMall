package com.zsl.account.entitys;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:43
 * Description: 用户收货信息
 */
@Data
public class ReceivingInfo implements Serializable {
    String address; // 地址
    String phone; // 手机号

    @Override
    public String toString() {
        return "ReceivingInfo{" +
                "address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
