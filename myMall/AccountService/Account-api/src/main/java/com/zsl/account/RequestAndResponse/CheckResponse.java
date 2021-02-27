package com.zsl.account.RequestAndResponse;

import com.zsl.ResultMode.AbstractResponse;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 19:59
 * Description: No Description
 */
@Data
public class CheckResponse extends AbstractResponse {
    String account; // 如果 redis 里的 token 验证成功，就返回对应的账户
}
