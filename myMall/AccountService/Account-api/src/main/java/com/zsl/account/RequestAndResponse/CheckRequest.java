package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 19:55
 * Description: No Description
 */
@Slf4j
@Data
public class CheckRequest extends AbstractRequest {
    String token; // 需要在 redis 里面验证的token

    @Override
    public void check() throws Exception {
        if (token == null || token.equals("")){
            log.error("CheckRequest throw Exception:" + "token 为空");
            throw new Exception();
        }
    }
}
