package com.guolala.zxx.service;

import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.User;
import com.guolala.zxx.entity.req.UserReq;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: pei.nie
 * @Date:2019/4/9
 * @Description:
 */
public interface UserService {

    /**
     * 获取用户信息
     *
     * @param userReq
     * @return
     */
    @Deprecated
    User getUserInfo(UserReq userReq);

    /**
     * 用户注册
     *
     * @param userReq
     * @return
     */
    @Deprecated
    void userRegist(UserReq userReq);

    /**
     * 用户登录
     *
     * @param userReq
     * @param response
     * @return
     */
    @Deprecated
    User userLogin(UserReq userReq, HttpServletResponse response);

    /**
     * 微信小程序登录
     *
     * @param jsCode
     * @param response
     * @return
     * @see https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/wx.login.html
     */
    UserInfo wxLogin(String jsCode, HttpServletResponse response);

    /**
     * 根据小程序wx.getUserInfo更新用户微信信息
     *
     * @param userReq
     * @param userInfo
     * @return
     */
    void updUserInfo(UserReq userReq, UserInfo userInfo);
}
