package com.zsl.commodityprovider.service;

import com.zsl.RequestAndResponse.*;
import com.zsl.SelectService;
import com.zsl.commodityprovider.persistence.SelectDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/14
 * Time: 16:02
 * Description: No Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SelectServiceImpl implements SelectService {

    @Autowired
    private SelectDao selectDao;

    @Override
    public AllCommoditiesResponse findAllCommodities(AllCommoditiesRequest request) {
        AllCommoditiesResponse response = new AllCommoditiesResponse();
        System.out.println("findAll");
        response.setCommodities(selectDao.findAllCommodities());
        return response;
    }

    @Override
    public FindCommodityResponse findCommodityById(FindCommodityRequest request) {
        FindCommodityResponse response = new FindCommodityResponse();
        System.out.println("byId");
        response.setCommodity(selectDao.findCommodityById(request.getId()));
        return response;
    }
}
