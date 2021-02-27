package com.zsl.order.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 15:29
 * Description: 需要发送到队列的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyMessage {
    String userId;
    Integer commodityId;
    Integer count;
    Float price;
}
