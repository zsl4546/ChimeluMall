package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:28
 * Description: 管理员查看所有的账户，不需要其他参数
 */
public class AllUsersRequest extends AbstractRequest {
    @Override
    public void check() {
        // nothing to do
    }
}
