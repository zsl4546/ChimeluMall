package com.zsl;

import com.zsl.RequestAndResponse.*;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/14
 * Time: 15:37
 * Description: No Description
 */
public interface SelectService {
    public AllCommoditiesResponse findAllCommodities(AllCommoditiesRequest request);
    public FindCommodityResponse findCommodityById(FindCommodityRequest request);
}
