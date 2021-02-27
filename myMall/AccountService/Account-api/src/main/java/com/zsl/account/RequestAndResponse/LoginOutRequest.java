package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/10
 * Time: 14:25
 * Description: No Description
 */
@Data
public class LoginOutRequest extends AbstractRequest {
    String token; // 将要删除的 token
    @Override
    public void check() throws Exception {
        // nothing to do
    }
}
