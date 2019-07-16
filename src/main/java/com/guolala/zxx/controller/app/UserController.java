package com.guolala.zxx.controller.app;

import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.User;
import com.guolala.zxx.entity.req.UserReq;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;


/**
 * @Author: pei.nie
 * @Date:2019/4/9
 * @Description:
 */

@RestController
@RequestMapping("/app/user")
@Api(value = "【app】 用户相关接口", tags = {"UserController"})
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @param userReq
     * @return
     */
    @PostMapping("/v1/getUser")
    @ApiOperation(value = "获取用户信息", httpMethod = "POST",hidden = true)
    public User getUser(@RequestBody UserReq userReq) {
        User user = userService.getUserInfo(userReq);
        if (user == null) {
            throw new GLLException(SysCode.USER_NOT_EXIST);
        }
        return user;
    }

    /**
     * 用户注册
     *
     * @param userReq
     * @return
     */
    @PostMapping("/v1/regist")
    @ApiOperation(value = "用户注册", httpMethod = "POST",hidden = true)
    public void saveUser(@RequestBody UserReq userReq) {
        userService.userRegist(userReq);
    }

    /**
     * 用户登录
     *
     * @param userReq
     * @param response
     * @return
     */
    @PostMapping("/v1/login")
    @ApiOperation(value = "用户登录", httpMethod = "POST",hidden = true)
    public User login(@RequestBody UserReq userReq, @ApiIgnore HttpServletResponse response) {
        return userService.userLogin(userReq, response);
    }

    @GetMapping("/v1/wxLogin")
    @ApiOperation(value = "微信小程序登录", httpMethod = "GET",response = User.class)
    public UserInfo wxLogin(@ApiParam(name = "jsCode",value = "小程序调用wx.login获取的凭证") @RequestParam("jsCode") String jsCode, @ApiIgnore HttpServletResponse response) {
        return userService.wxLogin(jsCode, response);
    }

    @PostMapping("/v1/updUserInfo")
    @ApiOperation(value = "小程序登录后更新用户信息", httpMethod = "POST",response = User.class)
    public void updUserInfo(@RequestBody UserReq userReq, @ApiIgnore UserInfo userInfo) {
        userService.updUserInfo(userReq, userInfo);
    }




}
