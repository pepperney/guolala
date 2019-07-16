package com.guolala.zxx.service.impl;

import com.guolala.zxx.constant.*;
import com.guolala.zxx.dao.UserMapper;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.User;
import com.guolala.zxx.entity.req.UserReq;
import com.guolala.zxx.entity.wx.WxLoginResp;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.UserService;
import com.guolala.zxx.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: pei.nie
 * @Date:2019/4/9
 * @Description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${wx.appid}")
    private String appId;
    @Value("${wx.appsecret}")
    private String appSecret;
    @Value("${wx.loginUrl}")
    private String loginUrl;



    @Override
    public User getUserInfo(UserReq userReq) {
        ValidateUtil.validateParam(userReq);
        User user = userMapper.selectUser(BeanUtil.copyProperties(userReq, User.class));
        return user;
    }

    @Override
    public void userRegist(UserReq userReq) {
        if (null == userReq) {
            throw new GLLException(SysCode.ILLEGAL_PARAM, "用户信息不能为空");
        }
        ValidateUtil.validateParam(userReq);
        User user = BeanUtil.copyProperties(userReq, User.class);
        User existUser = userMapper.selectUser(user);
        if (null != existUser) {
            throw new GLLException(SysCode.USER_REGISTED);
        }
        user.setUserStatus(UserStatus.NORMAL.getCode());
        user.setUserType(UserType.COMMON.getCode());
        user.setPassword(GUtil.getPassWord(userReq.getPassword()));
        this.saveUser(user);
    }

    @Override
    public User userLogin(UserReq userReq, HttpServletResponse response) {
        ValidateUtil.validateParam(userReq);
        userReq.setPassword(GUtil.getPassWord(userReq.getPassword()));
        User user = userMapper.selectUser(BeanUtil.copyProperties(userReq, User.class));
        if (null == userReq) {
            throw new GLLException(SysCode.USER_ERROR);
        }
        String token = GUtil.getTokenStr(user.getId());
        log.info("手机号:{}对应的token={}", user.getId(), token);
        response.addHeader(Const.TOKEN, token);
        return user;
    }

    @Override
    public UserInfo wxLogin(String jsCode, HttpServletResponse response) {
        String url = loginUrl.replace("JSCODE", jsCode);
        String result = HttpUtil.get(url, null, null);
        log.info("调用微信登录接口{}返回{}", url, result);
        if (StringUtils.isEmpty(result)) {
            throw new GLLException(SysCode.WX_LOGIN_FAIL);
        }
        WxLoginResp wxLoginResp = JsonUtil.jsonToObject(result, WxLoginResp.class);
        if (!Const.ZERO_STR.equals(wxLoginResp.getErrcode())) {
            throw new GLLException(SysCode.WX_LOGIN_FAIL);
        }
        String openId = wxLoginResp.getOpenid();
        String sessionKey = wxLoginResp.getSession_key();
        // 缓存sessionKey
        redisUtil.setex(RedisKey.WX_SESSION.key + openId, RedisKey.WX_SESSION.expireTime, sessionKey);
        String userToken = GUtil.getTokenStr(openId, sessionKey);
        // 根据openId查询用户，不存在时注册用户
        User user = User.builder().wxOpenid(openId).build();
        User exsitUser = userMapper.selectUser(user);
        if (null == exsitUser) {
            user.setUserStatus(UserStatus.NORMAL.getCode());
            user.setUserType(UserType.COMMON.getCode());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userMapper.insert(user);
            exsitUser = user;
        }
        // 缓存token和用户关系
        redisUtil.setex(RedisKey.TOKEN.key + userToken, RedisKey.TOKEN.expireTime, JsonUtil.toJson(exsitUser));
        // 返回token给客户端
        response.addHeader(Const.TOKEN, userToken);
        return BeanUtil.copyProperties(exsitUser,UserInfo.class);
    }

    @Override
    public void updUserInfo(UserReq userReq, UserInfo userInfo) {
        userReq.setId(userInfo.getId());
        userReq.setWxOpenid(userInfo.getWxOpenid());
        this.saveUser(BeanUtil.copyProperties(userReq,User.class));
    }


    private void saveUser(User user) {
        if (user == null) return;
        if (null == user.getId()) {
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userMapper.insert(user);
        } else {
            user.setUpdateTime(new Date());
            userMapper.updateByPrimaryKey(user);
        }
    }


}
