package com.zsl.account.entitys;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:38
 * Description: No Description
 */
@Data
@ToString
public class UserAccount implements Serializable {
    String name; // 姓名
    String account; // 账号
    String password; // 密码
    List<ReceivingInfo> receivingInfos; // 收货信息
    Float money;
}
