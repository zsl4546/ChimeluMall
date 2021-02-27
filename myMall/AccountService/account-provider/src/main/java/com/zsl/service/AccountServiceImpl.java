package com.zsl.service;

import com.zsl.account.RequestAndResponse.*;
import com.zsl.account.UserAccountService;
import com.zsl.account.constant.AccountResponseConstant;
import com.zsl.account.entitys.UserAccount;
import com.zsl.persistence.AccountManage;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 18:24
 * Description: dubbo处理账户信息的服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements UserAccountService {
    @Autowired
    private AccountManage accountManage;

    /**
     * 请求所有的账户
     * 用户后台管理，如果未来业务增长大，这部分数据也大，需要做优化，不能一下子查找很多
     * 目前的练习主要在用户前台，希望有人能用我的吧嘻嘻
     * @param allUsersRequest
     * @return
     */
    @Override
    public AllUsersResponse findAllUserAccount(AllUsersRequest allUsersRequest) {
        allUsersRequest.check();
        AllUsersResponse response = new AllUsersResponse();
        List<UserAccount> userAccountList = accountManage.findAllUserAccount();
        response.setUserAccounts(userAccountList);
        response.setCode(AccountResponseConstant.SUCCESS.getCode());
        response.setMsg(AccountResponseConstant.SUCCESS.getMessage());
        return response;
    }

    /**
     * 删除单个用户账号
     * @param deleteUserRequest
     * @return 用户账号
     */
    @Override
    public DeleteUserResponse deleteUserAccount(DeleteUserRequest deleteUserRequest) throws Exception {
        deleteUserRequest.check();
        DeleteUserResponse response = new DeleteUserResponse();
        accountManage.deleteUserAccount(deleteUserRequest.getAccount());
        response.setCode(AccountResponseConstant.SUCCESS.getCode());
        response.setMsg(AccountResponseConstant.SUCCESS.getMessage());
        return response;
    }

    @Override
    public DecrMoneyResponse decrMoney(DecrMoneyRequest request) throws Exception {
        request.check();
        DecrMoneyResponse response = new DecrMoneyResponse();
        UserAccount userAccount = request.getUserAccount();
        accountManage.decrMoney(userAccount);
        response.setCode(AccountResponseConstant.SUCCESS.getCode());
        response.setMsg(AccountResponseConstant.SUCCESS.getMessage());
        return response;
    }

}
