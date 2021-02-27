package com.zsl.commodityprovider.persistence;

import com.zsl.entitys.Commodity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/18
 * Time: 15:49
 * Description: No Description
 */
@Mapper
public interface UpdateDao {
    public void addCommodity(Commodity commodity);
    public void deleteCommodity(Integer id);
    public void decrCommodity(Commodity commodity);
}
