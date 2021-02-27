package com.zsl.account.controller;

import com.zsl.account.LoginService;
import com.zsl.account.RequestAndResponse.*;
import com.zsl.account.constant.CookieConstant;
import com.zsl.account.entitys.UserAccount;
import com.zsl.utils.CookieUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/5
 * Time: 15:45
 * Description: No Description
 */
@RestController
@RequestMapping("/users")
public class LoginController {

    @Reference
    private LoginService loginService;

    /**
     * 登录
     * @param userAccount
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserAccount userAccount,HttpServletResponse httpServletResponse) throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUserAccount(userAccount);
        LoginResponse response = loginService.login(request);
        response.setAccount(userAccount.getAccount());
        if (response.getToken() != null) {
            // 添加 cookie 到主域名
            Cookie cookie = CookieUtil.genCookieWithDomain(CookieConstant.ACCESS_TOKEN, response.getToken(), 60 * 60 * 24 *7, CookieConstant.DO_MAIN);
//            Cookie cookie = CookieUtil.genCookie(CookieConstant.ACCESS_TOKEN, response.getToken(), "/", 60 * 60 * 24 * 7);
            httpServletResponse.addCookie(cookie);
            System.out.println("cookie: "+ response.getToken());
        }
        return response;
    }

    /**
     * 退出登录，将浏览器的 token 和 redis 的 token 清除，
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/loginOut")
    public LoginOutResponse loginOut(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        Cookie accessToken = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), CookieConstant.ACCESS_TOKEN)) {
                    accessToken = cookie;
                    break;
                }
            }
        }
        LoginOutRequest request = new LoginOutRequest();
        if (accessToken != null){
            accessToken.setMaxAge(0);
            response.addCookie(accessToken); // 删除浏览器的 cookie
            request.setToken(accessToken.getValue());
        }
        return loginService.loginOut(request);
    }

    /**
     * 检查 redis 里面有没有当前 token 对应的用户
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/check")
    public CheckResponse check(HttpServletRequest request) throws Exception {
        String token = CookieUtil.getCookieValue(request, CookieConstant.ACCESS_TOKEN);
        if (token == null){
            return new CheckResponse(); // 没有 token 直接返回空回复
        }
        System.out.println(token);
        CheckRequest checkRequest = new CheckRequest();
        checkRequest.setToken(token);
        CheckResponse checkResponse = loginService.check(checkRequest); // 在 redis 检查是否存在这样的 token
        return checkResponse;
    }

    /**
     * 注册账号
     * @param userAccount
     * @return
     */
    @PostMapping("/regist")
    public RegistResponse regist(@RequestBody UserAccount userAccount, HttpServletResponse httpServletResponse){
        RegistRequest request = new RegistRequest();
        request.setUserAccount(userAccount);
        System.out.println(1);
        System.out.println(userAccount);
        RegistResponse response = loginService.regist(request);
        Cookie cookie = CookieUtil.genCookieWithDomain(CookieConstant.ACCESS_TOKEN, response.getToken(), 60 * 60 * 24 * 7, CookieConstant.DO_MAIN);
        httpServletResponse.addCookie(cookie); // 重新写 token 在浏览器
        return response;
    }
}
