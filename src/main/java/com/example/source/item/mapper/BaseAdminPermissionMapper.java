package com.example.source.item.mapper;

import com.example.source.item.dto.PermissionDTO;
import com.example.source.item.entity.BaseAdminPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseAdminPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BaseAdminPermission record);

    int insertSelective(BaseAdminPermission record);

    BaseAdminPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseAdminPermission record);

    int updateByPrimaryKey(BaseAdminPermission record);

    List<PermissionDTO> getPermissionListByPId(Integer id);

    List<BaseAdminPermission> getPermissionList();

    List<BaseAdminPermission> getParentPermissionList();

    List<PermissionDTO> getPermissionDTOList();

    BaseAdminPermission selectByPermissionName(@Param("name") String name);
}
