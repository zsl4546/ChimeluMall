package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 16:46
 * Description: No Description
 */
@Slf4j
@Data
public class DeleteUserRequest extends AbstractRequest {

    String account; // 要删除的账户

    @Override
    public void check() throws Exception {
        if (account == null || account.equals("")){
            log.error("DeleteUserRequest throw Exception:"+"账户为空");
            throw new Exception();
        }
    }
}
