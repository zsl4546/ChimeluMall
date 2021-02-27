package com.zsl.commodityprovider.service;

import com.zsl.RequestAndResponse.*;
import com.zsl.commodityprovider.persistence.UpdateDao;
import com.zsl.constant.CommodityResponseConstant;
import com.zsl.entitys.Commodity;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/18
 * Time: 15:48
 * Description: No Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UpdateServiceImpl implements com.zsl.UpdateService {

    @Autowired
    private UpdateDao updateDao;

    @Override
    public AddCommodityResponse addCommodity(AddCommodityRequest request) throws Exception {
        request.check();
        AddCommodityResponse response = new AddCommodityResponse();
        updateDao.addCommodity(request.getCommodity());
        response.setCode(CommodityResponseConstant.SUCCESS.getCode());
        response.setMsg(CommodityResponseConstant.SUCCESS.getMessage());
        return response;
    }

    @Override
    public DeleteCommodityResponse deleteCommodity(DeleteCommodityRequest request) throws Exception {
        request.check();
        DeleteCommodityResponse response = new DeleteCommodityResponse();
        updateDao.deleteCommodity(request.getId());
        response.setCode(CommodityResponseConstant.SUCCESS.getCode());
        response.setMsg(CommodityResponseConstant.SUCCESS.getMessage());
        return response;
    }

    @Override
    public DecrCommodityResponse decrCommodity(DecrCommodityRequest request) throws Exception {
        request.check();
        DecrCommodityResponse response = new DecrCommodityResponse();
        Commodity commodity = new Commodity();
        commodity.setId(request.getId());
        commodity.setStock(request.getStock());
        updateDao.decrCommodity(commodity);
        response.setCode(CommodityResponseConstant.SUCCESS.getCode());
        response.setMsg(CommodityResponseConstant.SUCCESS.getMessage());
        return response;
    }
}
