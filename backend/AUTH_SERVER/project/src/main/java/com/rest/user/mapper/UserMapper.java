package com.rest.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.rest.user.model.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    public User getUserByEmail(String email);
    public Boolean insertUser(User user);
}
