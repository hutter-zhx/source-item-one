package com.example.source.item.mapper;

import com.example.source.item.dto.AdminUserDTO;
import com.example.source.item.dto.UserSearchDTO;
import com.example.source.item.entity.BaseAdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseAdminUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(BaseAdminUser record);

    int insertSelective(BaseAdminUser record);

    BaseAdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseAdminUser record);

    int updateByPrimaryKey(BaseAdminUser record);

    BaseAdminUser getRoles(String name);

    BaseAdminUser selectByRoleName(String name);

    List<AdminUserDTO> getAdminUserList(UserSearchDTO userSearchDTO);

    BaseAdminUser selectByAdminUserName(@Param("sysUserName") String sysUserName);

    int updateAdminUserStatus(@Param("id") Integer id, @Param("userStatus") Integer status);
}
