package com.zsl.commodityprovider;

import com.zsl.commodityprovider.persistence.SelectDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommodityProviderApplicationTests {

    @Autowired
    private SelectDao selectDao;

    @Test
    void contextLoads() {
        System.out.println(selectDao.findAllCommodities());
    }

}
