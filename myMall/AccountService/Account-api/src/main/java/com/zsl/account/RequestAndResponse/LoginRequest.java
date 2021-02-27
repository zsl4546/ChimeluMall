package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import com.zsl.account.entitys.UserAccount;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 19:44
 * Description: No Description
 */
@Slf4j
@Data
public class LoginRequest extends AbstractRequest {

    public UserAccount userAccount;

    @Override
    public void check() throws Exception {
        if (userAccount.getAccount() == null || userAccount.getAccount().equals("") || userAccount.getPassword() == null || userAccount.getPassword().equals(""))  {
            log.error("LoginRequest throw Exception:" + "用户名或密码为空");
            throw new Exception();
        }
    }
}
