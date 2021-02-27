package com.zsl.entitys;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/14
 * Time: 15:29
 * Description: No Description
 */
@Data
public class Commodity implements Serializable {
    Integer id;
    String titleName;
    String fileName;
    float price;
    Integer stock;
}
