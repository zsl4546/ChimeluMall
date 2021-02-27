package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import com.zsl.account.entitys.UserAccount;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 18:53
 * Description: No Description
 */
@Data
@Slf4j
public class DecrMoneyRequest extends AbstractRequest {
    UserAccount userAccount;
    @Override
    public void check() throws Exception {
        if (userAccount.getAccount() == null || userAccount.equals("") ||
        userAccount.getMoney() == null || userAccount.getMoney() < 0) {
            log.info("参数错误");
            throw new Exception("参数错误");
        }
    }
}
