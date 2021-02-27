package com.zsl.order.entitys;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 15:01
 * Description: 生成的订单
 */
@Data
public class OrderLog {
    Integer id;
    String orderNo;
    String userId;
    Integer commodityId;
    Integer count;
    Float amount;
}
