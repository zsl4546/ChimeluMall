package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractResponse;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/5
 * Time: 11:51
 * Description: No Description
 */
@Data
public class RegistResponse extends AbstractResponse {
    String token; // 注册成功将账号存储在 redis，将 token 返回
//    String backUrl; // 回调地址
}
