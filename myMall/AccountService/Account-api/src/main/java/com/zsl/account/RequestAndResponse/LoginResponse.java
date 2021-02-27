package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractResponse;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 19:49
 * Description: No Description
 */
@Data
public class LoginResponse extends AbstractResponse {
    String token; // 返回token
    String account;
}
