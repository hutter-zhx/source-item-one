package com.example.source.item.mapper;

import com.example.source.item.dto.UserDTO;
import com.example.source.item.dto.UserSeekDTO;
import com.example.source.item.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<UserDTO> getUserList( UserSeekDTO userSeekDTO);

    User getUserByName(@Param("username") String username);

    int updateUserStatus(@Param("id") Integer id, @Param("userStatus") Integer status);
}
