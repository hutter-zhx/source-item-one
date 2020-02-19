package com.example.source.item.service;

import com.example.source.item.entity.BaseAdminPermission;
import com.example.source.item.entity.BaseAdminUser;
import com.example.source.item.response.PageDataResult;

import java.util.List;
import java.util.Map;

public interface BaseAdminPermissionService {

    BaseAdminPermission getById(Integer pid);

    Map<String, Object> getUserPerms(BaseAdminUser user);

    List<BaseAdminPermission> getParentPermission();

    PageDataResult getAdminPermissionList(Integer pageNum, Integer pageSize);

    Map<String, Object> addAdminPermission(BaseAdminPermission permission);

    Map<String, Object> updateAdminPermisson(BaseAdminPermission permission);
}
