package com.example.source.item.service.impl;

import com.example.source.item.mapper.UserInfoMapper;
import com.example.source.item.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

}
