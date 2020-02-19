package com.example.source.item.service;

import com.example.source.item.dto.UserLoginDTO;
import com.example.source.item.dto.UserSearchDTO;
import com.example.source.item.entity.BaseAdminUser;
import com.example.source.item.response.PageDataResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface BaseAdminUserService {

    BaseAdminUser getRoles(String name);


    BaseAdminUser selectByRoleName(String name);

    Map<String, Object> login(UserLoginDTO user, HttpSession session, HttpServletRequest request);

    Map<String, Object> setPwd(String pwd, String isPwd);

    PageDataResult getAdminUserList(Integer pageNum, Integer pageSize, UserSearchDTO userSearchDTO);

    Map<String, Object> addAdminUser(BaseAdminUser user);

    Map<String, Object> updateAdminUser(BaseAdminUser user);

    Map<String, Object> delAdminUser(Integer id, Integer status);

    Map<String, Object> recoverAdminUser(Integer id, Integer status);
}
