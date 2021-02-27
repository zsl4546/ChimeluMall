package com.zsl.account;

import com.zsl.account.RequestAndResponse.*;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 17:05
 * Description: No Description
 */
public interface UserAccountService {
    public AllUsersResponse findAllUserAccount(AllUsersRequest allUsersRequest);
    public DeleteUserResponse deleteUserAccount(DeleteUserRequest deleteUserRequest) throws Exception;
    public DecrMoneyResponse decrMoney(DecrMoneyRequest request) throws Exception;
}
