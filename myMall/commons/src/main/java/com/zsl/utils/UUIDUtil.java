package com.zsl.utils;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/5
 * Time: 10:44
 * Description: No Description
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
