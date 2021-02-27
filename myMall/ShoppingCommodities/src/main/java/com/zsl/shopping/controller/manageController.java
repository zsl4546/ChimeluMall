package com.zsl.shopping.controller;

import com.zsl.RequestAndResponse.*;
import com.zsl.UpdateService;
import com.zsl.SelectService;
import com.zsl.entitys.Commodity;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/14
 * Time: 17:31
 * Description: No Description
 */
@RestController
@RequestMapping("/commodity")
public class manageController {
    @Reference
    private SelectService selectService;

    @Reference
    private UpdateService updateService;

    /**
     * 查找所有商品信息
     * @return
     */
    @GetMapping("/all")
    public List<Commodity> findAllCommodities(){
        AllCommoditiesRequest request = new AllCommoditiesRequest();
        return selectService.findAllCommodities(request).getCommodities();
    }

    @PostMapping("/add")
    public AddCommodityResponse addCommodity(@RequestBody Commodity commodity) throws Exception {
        AddCommodityRequest request = new AddCommodityRequest();
        request.setCommodity(commodity);
        return updateService.addCommodity(request);

    }

}
