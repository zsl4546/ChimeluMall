package com.zsl.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import com.zsl.entitys.Commodity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/19
 * Time: 9:45
 * Description: No Description
 */
@Slf4j
@Data
public class AddCommodityRequest extends AbstractRequest {
    Commodity commodity;

    @Override
    public void check() throws Exception {
        if (commodity.getFileName() == null || commodity.getFileName().equals("") ||
        commodity.getPrice() <= 0 || commodity.getTitleName() == null || commodity.getTitleName().equals("")) {
            log.error("commodity 参数错误！");
            throw new Exception();
        }
    }
}
