package com.example.source.item.service.impl;

import com.example.source.item.dto.UserDTO;
import com.example.source.item.dto.UserSeekDTO;
import com.example.source.item.entity.BaseAdminUser;
import com.example.source.item.entity.User;
import com.example.source.item.entity.UserInfo;
import com.example.source.item.mapper.UserInfoMapper;
import com.example.source.item.mapper.UserMapper;
import com.example.source.item.response.PageDataResult;
import com.example.source.item.service.UserService;
import com.example.source.item.util.DigestUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public PageDataResult getUserList(Integer pageNum, Integer pageSize, UserSeekDTO userSeekDTO) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            logger.info("分页查询用户列表");
            PageHelper.startPage(pageNum, pageSize);
            List<UserDTO> userDTOList = userMapper.getUserList(userSeekDTO);

            if (userDTOList != null) {
                PageInfo<UserDTO> pageInfo = new PageInfo<>(userDTOList);
                pageDataResult.setList(pageInfo.getList());
                pageDataResult.setTotals((int) pageInfo.getTotal());
            }
            logger.info("查询用户列表成功  userList:" + userDTOList);

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询用户列表异常");
        }
        return pageDataResult;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> addUser(User user) throws Exception {
        Map<String, Object> data = new HashMap<>();
        BaseAdminUser baseAdminUser = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        User oldUser = userMapper.getUserByName(user.getUsername());
        if (oldUser != null) {
            logger.info(user.getUsername() + "用户已存在");
            data.put("code", 0);
            data.put("message", "用户名已存在");
        } else {
            user.setModifierId(baseAdminUser.getId());
            user.setModifierName(baseAdminUser.getSysUserName());
            user.setCreateTime(new Date());
            user.setPassword(DigestUtils.Md5(user.getUsername(), user.getPassword()));
            user.setUserStatus((byte) 1);
            int result = userMapper.insertSelective(user);
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(user.getId());
            userInfo.setModifierId(baseAdminUser.getId());
            userInfo.setModifierName(baseAdminUser.getSysUserName());
            userInfo.setCreateTime(new Date());
            int resultTwo = userInfoMapper.insertSelective(userInfo);
            if (result == 0 || resultTwo == 0) {
                logger.info("新增失败");
                data.put("code", 0);
                data.put("message", "新增失败");
                return data;
            }
            logger.info("新增成功");
            data.put("code", 1);
            data.put("message", "新增成功");
        }

        return data;
    }

    @Override
    @Transactional
    public Map<String, Object> setUser(UserInfo userInfo) throws Exception{
        Map<String, Object> data = new HashMap<>();
        userInfo.setUpdateTime(new Date());
        int result = userInfoMapper.updateUserByUid(userInfo);
        if (result==0){
            logger.info("完善/修改用户信息失败");
            data.put("code", 0);
            data.put("message", "完善/修改用户信息失败");
            return data;
        }
        logger.info("完善/修改用户信息成功");
        data.put("code", 1);
        data.put("message", "完善/修改用户信息成功");

        return data;
    }

    @Override
    public Map<String, Object> delUser(Integer id, Integer status) {
        Map<String, Object> data = new HashMap<>();
        int result = userMapper.updateUserStatus(id, status);
        if (result == 0) {
            logger.info("删除失败");
            data.put("code", 0);
            data.put("message", "删除失败");
        } else {
            logger.info("删除成功");
            data.put("code", 1);
            data.put("message", "删除成功");
        }
        return data;
    }

    @Override
    public Map<String, Object> recoverUser(Integer id, Integer status) {
        Map<String, Object> data = new HashMap<>();
        int result = userMapper.updateUserStatus(id, status);
        if (result == 0) {
            logger.info("恢复失败");
            data.put("code", 0);
            data.put("message", "恢复失败");
        } else {
            logger.info("恢复成功");
            data.put("code", 1);
            data.put("message", "恢复成功");
        }
        return data;
    }


}
