package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractResponse;
import com.zsl.account.entitys.UserAccount;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:33
 * Description: 管理员查询所有用户的账户信息
 */
@Data
public class AllUsersResponse extends AbstractResponse {
    List<UserAccount> userAccounts; // 所有用户的账户信息

}
