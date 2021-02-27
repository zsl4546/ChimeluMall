package com.zsl.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 10:09
 * Description: No Description
 */
@Data
@Slf4j
public class FindCommodityRequest extends AbstractRequest {
    Integer id; // 商品 id
    @Override
    public void check() throws Exception {
        if (id == null || id < 0) {
            log.error("商品 id 为空或者小于0， id = {}", id);
            throw new Exception("商品id有误");
        }
    }
}
