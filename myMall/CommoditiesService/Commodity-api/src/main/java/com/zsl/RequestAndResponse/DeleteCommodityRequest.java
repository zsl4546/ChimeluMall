package com.zsl.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/19
 * Time: 11:14
 * Description: No Description
 */
@Data
@Slf4j
public class DeleteCommodityRequest extends AbstractRequest {
    Integer id;
    @Override
    public void check() throws Exception {
        if (id == null || id < 0) {
            log.error("商品删除参数错误！");
            throw new Exception();
        }
    }
}
