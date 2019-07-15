package com.guolala.zxx.controller.app;

import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.User;
import com.guolala.zxx.entity.vo.UserVo;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
     * @param userVo
     * @return
     */
    @PostMapping("/v1/getUser")
    @ApiOperation(value = "获取用户信息", httpMethod = "POST",hidden = true)
    public User getUser(@RequestBody UserVo userVo) {
        User user = userService.getUserInfo(userVo);
        if (user == null) {
            throw new GLLException(SysCode.USER_NOT_EXIST);
        }
        return user;
    }

    /**
     * 用户注册
     *
     * @param userVo
     * @return
     */
    @PostMapping("/v1/regist")
    @ApiOperation(value = "用户注册", httpMethod = "POST",hidden = true)
    public void saveUser(@RequestBody UserVo userVo) {
        userService.userRegist(userVo);
    }

    /**
     * 用户登录
     *
     * @param userVo
     * @param response
     * @return
     */
    @PostMapping("/v1/login")
    @ApiOperation(value = "用户登录", httpMethod = "POST",hidden = true)
    public User login(@RequestBody UserVo userVo, @ApiIgnore HttpServletResponse response) {
        return userService.userLogin(userVo, response);
    }

    @GetMapping("/v1/wxLogin")
    @ApiOperation(value = "微信小程序登录", httpMethod = "GET",response = User.class)
    public UserInfo wxLogin(@ApiParam("jsCode") @RequestParam("jsCode") String jsCode, @ApiIgnore HttpServletResponse response) {
        return userService.wxLogin(jsCode, response);
    }

    @PostMapping("/v1/updUserInfo")
    @ApiOperation(value = "更新用户信息", httpMethod = "POST",response = User.class)
    public void updUserInfo(@RequestBody UserVo userVo,@ApiIgnore UserInfo userInfo) {
        userService.updUserInfo(userVo, userInfo);
    }




}
