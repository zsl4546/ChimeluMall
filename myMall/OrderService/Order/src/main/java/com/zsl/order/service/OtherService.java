package com.zsl.order.service;

import com.zsl.order.entitys.OrderLog;
import com.zsl.order.persistence.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 15:22
 * Description: No Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OtherService {
    @Autowired
    private Order order;

    public OrderLog queryOrder(String userId, Integer commodityId) {
        OrderLog request = new OrderLog();
        request.setCommodityId(commodityId);
        request.setUserId(userId);
        OrderLog response = order.queryOrder(request);
        return response;
    }

    public void createOrder(OrderLog orderLog) {
        order.createOrder(orderLog);
    }
}
