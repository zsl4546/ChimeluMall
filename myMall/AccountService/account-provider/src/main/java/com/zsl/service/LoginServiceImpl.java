package com.zsl.service;

import com.zsl.account.LoginService;
import com.zsl.account.RequestAndResponse.*;
import com.zsl.account.constant.AccountRedisConstant;
import com.zsl.account.constant.AccountResponseConstant;
import com.zsl.account.constant.CookieConstant;
import com.zsl.account.entitys.UserAccount;
import com.zsl.persistence.Login;
import com.zsl.utils.CookieUtil;
import com.zsl.utils.JedisUtil;
import com.zsl.utils.MD5Util;
import com.zsl.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 20:52
 * Description: 登录相关服务，实现sso单点登录
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Autowired
    private Login login;

    /**
     * request 带有回调地址
     * 如果验证在数据库验证成功了，就可将回调写进 response
     * 如果没有验证成功则会停留在登录页面
     * @param request
     * @return 返回值带有回调地址和 token
     * @throws Exception
     */
    @Override
    public LoginResponse login(LoginRequest request) throws Exception {
        request.check();
        LoginResponse response = new LoginResponse();
//        response.setCallbackUrl(request.getCallbackUrl()); // 请求的回调写入返回响应值
        UserAccount userAccount = request.getUserAccount();
        userAccount.setPassword(MD5Util.getMD5(userAccount.getPassword())); // 对密码进行一次 MD5 加盐和加密
        request.setUserAccount(userAccount);
        System.out.println(userAccount);
        System.out.println(1);
        String account = login.login(request.getUserAccount());
        if (account != null && account.equals(request.getUserAccount().getAccount())){
            // 账号密码验证成功, 将账户写入 redis
            String token = UUIDUtil.getUUID();
            Jedis jedis = JedisUtil.getJedis();
            jedis.set(AccountRedisConstant.ACCOUNT_KEY_PREFIX + token, account);
            jedis.expire(AccountRedisConstant.ACCOUNT_KEY_PREFIX + token, 60 * 60 * 24 * 7); // 设置一周后过期
            JedisUtil.returnJedis();
            response.setCode(AccountResponseConstant.SUCCESS.getCode());
            response.setMsg(AccountResponseConstant.SUCCESS.getMessage());
            response.setToken(token);
            log.info("LoginServiceImpl: " + account + " 登录成功");
        } else {
            response.setCode(AccountResponseConstant.ACCOUNT_PASSWORD_OR_ACCOUNT_ERROR.getCode());
            response.setMsg(AccountResponseConstant.ACCOUNT_PASSWORD_OR_ACCOUNT_ERROR.getMessage());
            log.info("LoginServiceImpl: " + request.getUserAccount().getAccount() + " 登录失败");
        }
        return response;
    }

    /**
     * 退出登录，将 redis 的 token 和 浏览器的 cookie 清除
     * @param request
     * @return
     */
    @Override
    public LoginOutResponse loginOut(LoginOutRequest request) throws Exception {
        LoginOutResponse response = new LoginOutResponse();
        String accessToken = request.getToken();
        if (accessToken != null){
            Jedis jedis = JedisUtil.getJedis();
            jedis.expire(AccountRedisConstant.ACCOUNT_KEY_PREFIX + accessToken, 0);
            JedisUtil.returnJedis();
            response.setCode(AccountResponseConstant.SUCCESS.getCode());
            response.setMsg(AccountResponseConstant.SUCCESS.getMessage());
            return response;
        }

        response.setCode(AccountResponseConstant.ACCOUNT_NOT_FIND.getCode());
        response.setMsg(AccountResponseConstant.ACCOUNT_NOT_FIND.getMessage());

        return response;
    }

    /**
     * 检查 redis 有没有对应的 token，从业务上来说一般验证不成功会跳转到登录界面,但也可以不跳转依具体需求来看
     * @param request
     * @return 验证成功就带上账户，这个账户存储在 redis
     */
    @Override
    public CheckResponse check(CheckRequest request) throws Exception {
        request.check();
        CheckResponse response = new CheckResponse();
        String token = request.getToken();
        // 通过 token 在 redis 查找账户
        String account = (String) JedisUtil.get(AccountRedisConstant.ACCOUNT_KEY_PREFIX + token);
        if (account == null){
            // 没有找到
            response.setCode(AccountResponseConstant.ACCOUNT_CHECK_ERROR.getCode());
            response.setMsg(AccountResponseConstant.ACCOUNT_CHECK_ERROR.getMessage());
            log.info("LoginServiceImpl: "+ request.getToken() + "验证失败");
        } else {
            // 找到了
            // 重新设置过期时间为当前时间的一周后
            Jedis jedis = JedisUtil.getJedis();
            jedis.expire(AccountRedisConstant.ACCOUNT_KEY_PREFIX + token, 60 * 60 * 24 * 7);
            JedisUtil.returnJedis();
            response.setAccount(account);
            response.setCode(AccountResponseConstant.SUCCESS.getCode());
            response.setMsg(AccountResponseConstant.SUCCESS.getMessage());
            log.info("LoginServiceImpl: "+ request.getToken() + "验证成功");
        }
        return response;
    }

    /**
     * 注册账号，如果注册成功会将账号存储在redis
     * @param request
     * @return
     */
    @Override
    public RegistResponse regist(RegistRequest request) {
        RegistResponse response = new RegistResponse();
        UserAccount userAccount = request.getUserAccount();
        userAccount.setPassword(MD5Util.getMD5(userAccount.getPassword())); // MD5 加盐加密
        try {
            login.regist(userAccount);
            String token = UUIDUtil.getUUID();
            // 将账户信息保存到 redis，要设置过期时间为一周
            Jedis jedis = JedisUtil.getJedis();
            jedis.set(AccountRedisConstant.ACCOUNT_KEY_PREFIX + token, userAccount.getAccount());
            jedis.expire(AccountRedisConstant.ACCOUNT_KEY_PREFIX + token, 60 * 60 * 24 * 7);
            JedisUtil.returnJedis();
            // 返回响应
//            response.setBackUrl(request.getBackUrl());
            response.setToken(token);
            response.setCode(AccountResponseConstant.SUCCESS.getCode());
            response.setMsg(AccountResponseConstant.SUCCESS.getMessage());
            log.info(userAccount.getAccount() +" token: "+ token + "注册成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setCode(AccountResponseConstant.ACCOUNT_REGIST_ERROE.getCode());
            response.setMsg(AccountResponseConstant.ACCOUNT_REGIST_ERROE.getMessage());
            log.error(userAccount.getAccount() + "注册失败");
        }
        return response;
    }

}
