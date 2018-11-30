package com.lusiwei.service;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.dto.UserDto;
import com.lusiwei.param.RegisterParam;
import com.lusiwei.pojo.User;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 10:18
 * @Description:
 */
public interface UserService {
    User test();

    UserDto login(String username, String password);

    ResponseResult register(RegisterParam registerParam);

    ResponseResult queryQuestion(String username);

    ResponseResult checkAnswer(String username, String question, String answer);

    ResponseResult resetPassword(String passwordOld, String passwordNew, String currentUserName);

    ResponseResult updateInformation(String email, String phone, String question, String answer, UserDto currentUserName);

    User getInformation(int id);
}
