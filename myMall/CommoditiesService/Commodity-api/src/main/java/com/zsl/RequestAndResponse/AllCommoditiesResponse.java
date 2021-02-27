package com.zsl.RequestAndResponse;

import com.zsl.ResultMode.AbstractResponse;
import com.zsl.entitys.Commodity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/14
 * Time: 15:35
 * Description: No Description
 */
@Data
public class AllCommoditiesResponse extends AbstractResponse {
    List<Commodity> commodities; // 商品
}
