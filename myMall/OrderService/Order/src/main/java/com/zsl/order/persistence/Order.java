package com.zsl.order.persistence;

import com.zsl.order.entitys.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 14:58
 * Description: No Description
 */
@Mapper
public interface Order {
    public OrderLog queryOrder(OrderLog orderLog);
    public void createOrder(OrderLog orderLog);
}
