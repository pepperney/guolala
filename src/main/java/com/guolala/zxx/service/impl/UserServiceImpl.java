package com.guolala.zxx.service.impl;

import com.guolala.zxx.constant.Const;
import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.constant.UserStatus;
import com.guolala.zxx.constant.UserType;
import com.guolala.zxx.dao.UserMapper;
import com.guolala.zxx.entity.model.User;
import com.guolala.zxx.entity.vo.UserVo;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.UserService;
import com.guolala.zxx.util.BeanUtil;
import com.guolala.zxx.util.GUtil;
import com.guolala.zxx.util.RedisUtil;
import com.guolala.zxx.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public User getUserInfo(UserVo userVo) {
        ValidateUtil.validateParam(userVo);
        User user = userMapper.selectUser(BeanUtil.copyProperties(userVo, User.class));
        return user;
    }

    @Override
    public void userRegist(UserVo userVo) {
        if (null == userVo) {
            throw new GLLException(SysCode.ILLEGAL_PARAM, "用户信息不能为空");
        }
        ValidateUtil.validateParam(userVo);
        User user = BeanUtil.copyProperties(userVo, User.class);
        User existUser = userMapper.selectUser(user);
        if (null != existUser) {
            throw new GLLException(SysCode.USER_REGISTED);
        }
        user.setUserStatus(UserStatus.NORMAL.getCode());
        user.setUserType(UserType.COMMON.getCode());
        user.setPassword(GUtil.getPassWord(userVo.getPassword()));
        this.saveUser(user);
    }

    @Override
    public User userLogin(UserVo userVo, HttpServletResponse response) {
        ValidateUtil.validateParam(userVo);
        userVo.setPassword(GUtil.getPassWord(userVo.getPassword()));
        User user = userMapper.selectUser(BeanUtil.copyProperties(userVo, User.class));
        if (null == userVo) {
            throw new GLLException(SysCode.USER_ERROR);
        }
        String token = GUtil.getTokenStr(user.getId());
        log.info("手机号:{}对应的token={}",user.getId(),token);
        response.addHeader(Const.TOKEN, token);
        return user;
    }


    private void saveUser(User user) {
        if (user == null) return;
        ValidateUtil.validateParam(user);
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
