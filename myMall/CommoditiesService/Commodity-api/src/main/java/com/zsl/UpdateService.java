package com.zsl;

import com.zsl.RequestAndResponse.*;
import com.zsl.entitys.Commodity;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/18
 * Time: 15:47
 * Description: No Description
 */
public interface UpdateService {
    public AddCommodityResponse addCommodity(AddCommodityRequest request) throws Exception;
    public DeleteCommodityResponse deleteCommodity(DeleteCommodityRequest request) throws Exception;
    public DecrCommodityResponse decrCommodity(DecrCommodityRequest request) throws Exception;
}
