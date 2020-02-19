package com.example.source.item.service;

import com.example.source.item.dto.UserSeekDTO;
import com.example.source.item.entity.User;
import com.example.source.item.entity.UserInfo;
import com.example.source.item.response.PageDataResult;

import java.util.Map;

public interface UserService {


    PageDataResult getUserList(Integer pageNum, Integer pageSize,  UserSeekDTO userSeekDTO);

    Map<String, Object> addUser(User user) throws Exception;

    Map<String, Object> delUser(Integer id, Integer status);

    Map<String, Object> recoverUser(Integer id, Integer status);

    Map<String, Object> setUser(UserInfo userInfo) throws Exception;
}
