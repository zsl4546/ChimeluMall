package com.zsl.persistence;

import com.zsl.account.RequestAndResponse.AllUsersRequest;
import com.zsl.account.RequestAndResponse.AllUsersResponse;
import com.zsl.account.RequestAndResponse.DeleteUserRequest;
import com.zsl.account.RequestAndResponse.DeleteUserResponse;
import com.zsl.account.entitys.UserAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 17:25
 * Description: No Description
 */
@Mapper
public interface AccountManage {
    public List<UserAccount> findAllUserAccount();
    public void deleteUserAccount(String account);
    public void decrMoney(UserAccount userAccount);
}
