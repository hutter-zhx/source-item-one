package com.example.source.item.mapper;

import com.example.source.item.entity.BaseAdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseAdminRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BaseAdminRole record);

    int insertSelective(BaseAdminRole record);

    BaseAdminRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseAdminRole record);

    int updateByPrimaryKey(BaseAdminRole record);

    List<BaseAdminRole> getRoles();

    BaseAdminRole selectByAdminRoleName(String roleName);

    int updateAdminRoleStatus(@Param("id") Integer id, @Param("roleStatus") Integer status);
}
