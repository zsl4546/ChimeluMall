package com.zsl.persistence;

import com.zsl.account.entitys.UserAccount;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/5
 * Time: 10:34
 * Description: No Description
 */
public interface Login {
    public String login(UserAccount userAccount);
    public void regist(UserAccount userAccount);
}
