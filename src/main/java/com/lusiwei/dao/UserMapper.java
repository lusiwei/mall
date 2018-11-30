package com.lusiwei.dao;

import com.lusiwei.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User queryByUserName(String username);

    User queryByEmail(String name);

    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);
}