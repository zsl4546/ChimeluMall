package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import com.zsl.account.entitys.UserAccount;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/5
 * Time: 11:47
 * Description: No Description
 */
@Data
@Slf4j
public class RegistRequest extends AbstractRequest {
    UserAccount userAccount;
//    String backUrl;

    @Override
    public void check() throws Exception {
        if (userAccount.getAccount() == null || userAccount.getAccount().equals("")
        || userAccount.getPassword() == null || userAccount.getPassword().equals("")){
            log.error("账户名或密码为空");
            throw new Exception();
        }

    }
}
