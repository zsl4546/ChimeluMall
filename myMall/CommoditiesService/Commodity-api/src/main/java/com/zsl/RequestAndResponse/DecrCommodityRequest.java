package com.zsl.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 18:19
 * Description: No Description
 */
@Data
@Slf4j
public class DecrCommodityRequest extends AbstractRequest {
    Integer id;
    Integer stock;
    @Override
    public void check() throws Exception {
        if ( stock == null || stock < 0 || id == null) {
            log.info("参数错误");
            throw new Exception("参数错误");
        }
    }
}
