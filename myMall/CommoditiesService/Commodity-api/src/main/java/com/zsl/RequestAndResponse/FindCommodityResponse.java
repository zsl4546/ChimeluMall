package com.zsl.RequestAndResponse;

import com.zsl.ResultMode.AbstractResponse;
import com.zsl.entitys.Commodity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 10:16
 * Description: No Description
 */
@Data
@Slf4j
public class FindCommodityResponse extends AbstractResponse {
    Commodity commodity;
}
