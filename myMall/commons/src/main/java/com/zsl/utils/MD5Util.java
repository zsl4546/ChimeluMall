package com.zsl.utils;

import org.springframework.util.DigestUtils;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/5
 * Time: 11:55
 * Description: No Description
 */
public class MD5Util {
    //盐，用于混交md5
    public static final String SLAT = "&%5123***&&%%$$#@";

    public static String getMD5(String str) {
        String base = str +"/" + SLAT;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
