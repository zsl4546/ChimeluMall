package com.zsl.account.controller;

import com.zsl.account.RequestAndResponse.AllUsersRequest;
import com.zsl.account.RequestAndResponse.AllUsersResponse;
import com.zsl.account.RequestAndResponse.DeleteUserRequest;
import com.zsl.account.RequestAndResponse.DeleteUserResponse;
import com.zsl.account.UserAccountService;
import com.zsl.account.entitys.UserAccount;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.zookeeper.proto.DeleteRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/5
 * Time: 13:09
 * Description: No Description
 */
@RestController
@RequestMapping("/manage")
public class AccountManageController {

    @Reference
    private UserAccountService userAccountService;

    /**
     * 查找所有用户
     * @return
     */
    @GetMapping("/users")
    public AllUsersResponse findUsers(){
        System.out.println(123);
        AllUsersRequest request = new AllUsersRequest();
        System.out.println(userAccountService);
        AllUsersResponse allUserAccount = userAccountService.findAllUserAccount(request);
        System.out.println(allUserAccount.getUserAccounts());
        return allUserAccount;
    }

    /**
     * 删除单个用户
     * @param account
     * @return
     * @throws Exception
     */
    @DeleteMapping("/users/{account}")
    public DeleteUserResponse deleteUser(@PathVariable("account") String account) throws Exception {
        DeleteUserRequest request = new DeleteUserRequest();
        System.out.println("account: " + account);
        request.setAccount(account);
        DeleteUserResponse response = userAccountService.deleteUserAccount(request);
        return response;
    }
}
