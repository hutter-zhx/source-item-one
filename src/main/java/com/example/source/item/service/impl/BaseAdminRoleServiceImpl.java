package com.example.source.item.service.impl;

import com.example.source.item.entity.BaseAdminPermission;
import com.example.source.item.entity.BaseAdminRole;
import com.example.source.item.mapper.BaseAdminPermissionMapper;
import com.example.source.item.mapper.BaseAdminRoleMapper;
import com.example.source.item.response.PageDataResult;
import com.example.source.item.service.BaseAdminRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BaseAdminRoleServiceImpl implements BaseAdminRoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseAdminRoleMapper roleMapper;
    @Autowired
    private BaseAdminPermissionMapper permissionMapper;

    @Override
    public BaseAdminRole selectById(Integer roleId) {
        logger.info("根据角色id查询角色信息");
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<BaseAdminRole> getRoles() {
        logger.info("获取角色列表");
        return roleMapper.getRoles();
    }

    @Override
    public PageDataResult getRoleList(Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            logger.info("获取角色列表");
            List<BaseAdminPermission> permissionList = permissionMapper.getPermissionList();
            PageHelper.startPage(pageNum, pageSize);
            List<BaseAdminRole> roles = roleMapper.getRoles();
            for (BaseAdminRole role : roles) {
                String permissions = role.getPermissions();
                if (!StringUtils.isEmpty(permissions)) {
                    String[] ids = permissions.split(",");
                    List<String> list = new ArrayList<>();
                    for (String id : ids) {
                        for (BaseAdminPermission baseAdminPermission : permissionList) {
                            if (Integer.parseInt(id) == baseAdminPermission.getId()) {
                                list.add(baseAdminPermission.getName());
                            }
                        }
                    }
                    role.setPermissions(list.toString());
                }
            }
            if (roles.size() != 0) {
                System.err.println(roles);
                PageInfo<BaseAdminRole> pageInfo = new PageInfo<>(roles);
                pageDataResult.setList(roles);
                pageDataResult.setTotals((int) pageInfo.getTotal());
            }
        } catch (NumberFormatException e) {
            logger.info("获取角色列表异常");
            e.printStackTrace();
        }
        return pageDataResult;
    }
    @Override
    public Map<String, Object> addAdminRole(BaseAdminRole role) {
        Map<String, Object> data = new HashMap<>();

        try {
            BaseAdminRole baseAdminRole = roleMapper.selectByAdminRoleName(role.getRoleName());
            if (baseAdminRole != null) {
                logger.info("角色已存在");
                data.put("code", 0);
                data.put("message", "角色已存在");
            } else {
                role.setCreateTime(new Date());
                role.setUpdateTime(new Date());
                role.setRoleStatus(1);
                int result = roleMapper.insert(role);
                if (result == 0) {
                    data.put("code", 0);
                    data.put("message", "新增角色失败");
                    logger.error("新增角色失败");
                    return data;
                }
                data.put("code", 1);
                data.put("message", "新增角色成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("code", 0);
            data.put("message", "添加角色并授权！异常");
            logger.error("添加角色并授权！异常！", e);
            return data;
        }
        return data;
    }

    @Override
    public Map<String, Object> updateAdminRole(BaseAdminRole role) {
        Map<String, Object> data = new HashMap<>();
        try {
            role.setUpdateTime(new Date());
            int result = roleMapper.updateByPrimaryKeySelective(role);
            if (result != 1) {
                data.put("code", 0);
                data.put("message", "角色更改失败");
                logger.error("角色更改失败");
            }
            data.put("code", 1);
            data.put("message", "角色更改成功");
            logger.error("角色更改成功");
        } catch (Exception e) {
            data.put("code", 0);
            data.put("message", "角色更改异常");
            logger.error("角色更改异常", e);
            return data;
        }
        return data;
    }

    @Override
    public Map<String, Object> delAdminRoleStatus(Integer id, Integer status) {
        Map<String, Object> data = new HashMap<>();
        try {
            int result = roleMapper.updateAdminRoleStatus(id, status);
            if (result == 0) {
                data.put("code", 0);
                data.put("message", "删除角色失败");
                logger.info("删除角色失败");
            }
            data.put("code", 1);
            data.put("message", "删除角色成功");
            logger.error("删除角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除角色异常", e);
            data.put("code", 0);
            data.put("message", "删除角色异常");
            return data;
        }
        return data;
    }

    @Override
    public Map<String, Object> recoverAdminRoleStatus(Integer id, Integer status) {
        Map<String, Object> data = new HashMap<>();
        try {
            int result = roleMapper.updateAdminRoleStatus(id, status);
            if (result == 0) {
                logger.error("恢复角色失败");
                data.put("code", 0);
                data.put("message", "恢复角色失败");
            }
            logger.error("恢复角色成功");
            data.put("code", 1);
            data.put("message", "恢复角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("恢复角色异常", e);
            data.put("code", 0);
            data.put("message", "恢复角色异常");
            return data;
        }
        return data;
    }

}
