package com.zsl.account;

import com.zsl.account.RequestAndResponse.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 19:43
 * Description: No Description
 */
public interface LoginService {
    public LoginResponse login(LoginRequest request) throws Exception; // 登录
    public LoginOutResponse loginOut(LoginOutRequest request) throws Exception;
    public CheckResponse check(CheckRequest request) throws Exception; // 在 redis 里验证是否存在客户端发来的 token
    public RegistResponse regist(RegistRequest registRequest); // 账号注册
}
