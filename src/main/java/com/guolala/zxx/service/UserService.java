package com.guolala.zxx.service;

import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.User;
import com.guolala.zxx.entity.vo.UserVo;

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
     * @param userVo
     * @return
     */
    @Deprecated
    User getUserInfo(UserVo userVo);

    /**
     * 用户注册
     *
     * @param userVo
     * @return
     */
    @Deprecated
    void userRegist(UserVo userVo);

    /**
     * 用户登录
     *
     * @param userVo
     * @param response
     * @return
     */
    @Deprecated
    User userLogin(UserVo userVo, HttpServletResponse response);

    /**
     * 微信小程序登录
     *
     * @param jsCode
     * @param response
     * @return
     */
    UserInfo wxLogin(String jsCode, HttpServletResponse response);

    /**
     * 根据小程序wx.getUserInfo更新用户微信信息
     *
     * @param userVo
     * @param userInfo
     * @return
     */
    void updUserInfo(UserVo userVo, UserInfo userInfo);
}
