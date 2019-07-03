package com.guolala.zxx.service;

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
    User getUserInfo(UserVo userVo);

    /**
     * 用户注册
     *
     * @param userVo
     * @return
     */
    void userRegist(UserVo userVo);

    /**
     * 用户登录
     *
     * @param userVo
     * @param response
     * @return
     */
    User userLogin(UserVo userVo, HttpServletResponse response);
}
