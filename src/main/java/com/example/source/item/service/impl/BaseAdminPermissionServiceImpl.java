package com.example.source.item.service.impl;

import com.example.source.item.dto.PermissionDTO;
import com.example.source.item.entity.BaseAdminPermission;
import com.example.source.item.entity.BaseAdminRole;
import com.example.source.item.entity.BaseAdminUser;
import com.example.source.item.mapper.BaseAdminPermissionMapper;
import com.example.source.item.mapper.BaseAdminRoleMapper;
import com.example.source.item.response.PageDataResult;
import com.example.source.item.service.BaseAdminPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BaseAdminPermissionServiceImpl implements BaseAdminPermissionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseAdminPermissionMapper permissionMapper;

    @Autowired
    private BaseAdminRoleMapper roleMapper;

    @Override
    public BaseAdminPermission getById(Integer pid) {
        return permissionMapper.selectByPrimaryKey(pid);
    }

    @Override
    public Map<String, Object> getUserPerms(BaseAdminUser user) {
        Map<String, Object> data = new HashMap<>();
        Integer roleId = user.getRoleId();

        BaseAdminRole role = roleMapper.selectByPrimaryKey(roleId);
        if (null != role) {
            String permissions = role.getPermissions();

            String[] ids = permissions.split(",");
            List<PermissionDTO> permissionList = new ArrayList<>();
            for (String id : ids) {
                logger.info("获取角色对应的权限数据");
                BaseAdminPermission perm = (BaseAdminPermission) permissionMapper.selectByPrimaryKey(Integer.parseInt(id));
                if (null != perm) {
                    logger.info("授权角色下所有权限");
                    PermissionDTO permissionDTO = new PermissionDTO();
                    BeanUtils.copyProperties(perm, permissionDTO);
                    logger.info("获取子权限");
                    List<PermissionDTO> childrens = permissionMapper.getPermissionListByPId(perm.getId());
                    permissionDTO.setChildrens(childrens);
                    permissionList.add(permissionDTO);
                }
            }
            data.put("perm", permissionList);
        }

        return data;
    }

    @Override
    public List<BaseAdminPermission> getParentPermission() {
        logger.info("获取根权限数据");
       return permissionMapper.getParentPermissionList();

    }

    @Override
    public PageDataResult getAdminPermissionList(Integer pageNum, Integer pageSize) {
        //分页查询
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<PermissionDTO> permissionList = permissionMapper.getPermissionDTOList();
        if (permissionList.size() != 0) {
            PageInfo<PermissionDTO> pageInfo = new PageInfo<>(permissionList);
            pageDataResult.setList(permissionList);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public Map<String, Object> addAdminPermission(BaseAdminPermission permission) {
        Map<String,Object> data = new HashMap<>();

        try {
            logger.info("开始新增权限");

            BaseAdminPermission baseAdminPermission = permissionMapper.selectByPermissionName(permission.getName());
            if (baseAdminPermission!=null){
                data.put("code",0);
                data.put("message","增加失败,该权限已存在");
                logger.info("增加失败,该权限已存在");
            }else {
                permission.setCreateTime(new Date());
                permission.setUpdateTime(new Date());
                permission.setDelFlag(1);
                int result  =  permissionMapper.insertSelective(permission);
                if (result==0){
                    data.put("code",0);
                    data.put("message","增加失败");
                    logger.info("权限增加失败");
                }
                data.put("code",1);
                data.put("message","增加成功");
                logger.info("权限增加成功");
            }

        }catch(Exception e){
            e.printStackTrace();
            data.put("code",0);
            data.put("message","权限增加异常");
            logger.error("权限增加异常",e);
            return data;
        }
        return data;
    }

    @Override
    public Map<String, Object> updateAdminPermisson(BaseAdminPermission permission) {
        Map<String,Object> data = new HashMap<>();
        try {
            logger.info("开始修改权限");
            permission.setUpdateTime(new Date());
            int result = permissionMapper.updateByPrimaryKeySelective(permission);
            if (result==0){
                data.put("code", 0);
                data.put("message","修改失败");
                logger.info("权限修改失败");
            }
            data.put("code", 1);
            data.put("message","修改成功");
            logger.error("权限修成功");
        } catch (Exception e) {
            e.printStackTrace();
            data.put("code", 0);
            data.put("message","权限修改异常");
            logger.error("权限修改异常",e);
            return data;
        }
        return data;
    }


}
