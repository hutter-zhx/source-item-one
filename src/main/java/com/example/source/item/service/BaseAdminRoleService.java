package com.example.source.item.service;

import com.example.source.item.entity.BaseAdminRole;
import com.example.source.item.response.PageDataResult;

import java.util.List;
import java.util.Map;

public interface BaseAdminRoleService {

    BaseAdminRole selectById(Integer roleId);

    List<BaseAdminRole> getRoles();

    PageDataResult getRoleList(Integer pageNum, Integer pageSize);

    Map<String, Object> addAdminRole(BaseAdminRole role);

    Map<String, Object> updateAdminRole(BaseAdminRole role);

    Map<String, Object> delAdminRoleStatus(Integer id, Integer status);

    Map<String, Object> recoverAdminRoleStatus(Integer id, Integer status);
}
