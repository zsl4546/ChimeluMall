package com.zsl.commodityprovider.persistence;

import com.zsl.entitys.Commodity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/14
 * Time: 16:43
 * Description: No Description
 */
@Mapper
public interface SelectDao {
    public List<Commodity> findAllCommodities();
    public Commodity findCommodityById(Integer id);
}
