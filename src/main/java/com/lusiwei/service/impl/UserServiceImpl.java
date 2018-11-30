package com.lusiwei.service.impl;

import com.lusiwei.common.ResponseCode;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dao.UserMapper;
import com.lusiwei.dto.UserDto;
import com.lusiwei.exception.MyException;
import com.lusiwei.param.RegisterParam;
import com.lusiwei.pojo.User;
import com.lusiwei.service.UserService;
import com.lusiwei.util.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 10:18
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User test() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public UserDto login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new MyException(ResponseCode.NUll_USERNAME_PASSWORD.getDesc());
        }
        User user = userMapper.queryByUserName(username);
        UserDto userDto = new UserDto();
        if (user == null) {
            throw new MyException(ResponseCode.USERNAME_ERROR.getDesc());
        } else if (!user.getPassword().equals(Md5Utils.getMD5(password))) {
            throw new MyException(ResponseCode.PASSWORD_ERROR.getDesc());
        }
        BeanUtils.copyProperties(user, userDto);
        System.out.println(userDto);
        return userDto;
    }

    @Override
    public ResponseResult register(RegisterParam registerParam) {
        User user = checkUserName(registerParam.getUsername());
        if (user != null) {
            throw new MyException("该用户名已存在");
        }
        User registerUser = User.builder().username(registerParam.getUsername()).email(registerParam.getEmail()).phone(registerParam.getPhone())
                .question(registerParam.getQuestion()).answer(registerParam.getAnswer()).password(Md5Utils.getMD5(registerParam.getPassword())).build();
        registerUser.setCreateTime(new Date());
        registerUser.setUpdateTime(new Date());
        registerUser.setRole(0);
        userMapper.insert(registerUser);
        return ResponseResult.createSuccessResponse("校验成功");
    }

    @Override
    public ResponseResult queryQuestion(String username) {
        User user = userMapper.queryByUserName(username);
        if (user != null) {
            if (user.getQuestion() != null) {
                return ResponseResult.createSuccessResponse(user.getQuestion());
            } else {
                return ResponseResult.createFailResponse("该用户未设置找回密码问题");
            }
        }
        return ResponseResult.createFailResponse("用户名不存在");
    }

    @Override
    public ResponseResult checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            String token = UUID.randomUUID().toString();
            return ResponseResult.createSuccessResponse(token);
        }
        return ResponseResult.createFailResponse("问题答案错误");
    }

    @Override
    public ResponseResult resetPassword(String passwordOld, String passwordNew, String currentUserName) {
        //验证旧密码
        User user = userMapper.queryByUserName(currentUserName);
        //获取旧密码
        String password = user.getPassword();
        //判断旧密码是否输入正确
        if (!password.equals(Md5Utils.getMD5String(passwordOld))) {
            return ResponseResult.createFailResponse("旧密码输入错误");
        }
        //把新密码设置到user中
        user.setPassword(Md5Utils.getMD5String(passwordNew));
        userMapper.updateByPrimaryKey(user);
        return ResponseResult.createSuccessResponse("修改密码成功");
    }

    @Override
    public ResponseResult updateInformation(String email, String phone, String question, String answer, UserDto userDto) {
        User user = User.builder().email(email).phone(phone).question(question).answer(answer).id(userDto.getId()).build();
        userMapper.updateByPrimaryKeySelective(user);
        return ResponseResult.createSuccessResponse("更新个人信息成功");
    }

    @Override
    public User getInformation(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    private User checkUserName(String name) {
        //用户名或者邮箱都可以登录
        if (name.contains("@")) {
            return userMapper.queryByEmail(name);
        } else {
            return userMapper.queryByUserName(name);
        }
    }
}
