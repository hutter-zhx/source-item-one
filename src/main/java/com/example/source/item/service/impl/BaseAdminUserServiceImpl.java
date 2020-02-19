package com.example.source.item.service.impl;

import com.example.source.item.dto.AdminUserDTO;
import com.example.source.item.dto.UserLoginDTO;
import com.example.source.item.dto.UserSearchDTO;
import com.example.source.item.entity.BaseAdminUser;
import com.example.source.item.mapper.BaseAdminUserMapper;
import com.example.source.item.response.PageDataResult;
import com.example.source.item.service.BaseAdminUserService;
import com.example.source.item.util.DigestUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseAdminUserServiceImpl implements BaseAdminUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseAdminUserMapper userMapper;

    @Override
    public BaseAdminUser getRoles(String name) {

        return userMapper.getRoles(name);
    }

    @Override
    public BaseAdminUser selectByRoleName(String name) {

        return userMapper.selectByRoleName(name);
    }

    @Override
    public Map<String, Object> login(UserLoginDTO user, HttpSession session, HttpServletRequest request) {
        logger.info("进行登录");
        Map<String, Object> data = new HashMap<>();
        //shiro登录
        Subject subject = SecurityUtils.getSubject();
        String username = user.getSysUserName().trim();
        String password = user.getSysUserPwd().trim();
        String host = request.getRemoteAddr();

        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            if (!((String)session.getAttribute("code")).toLowerCase().equals(user.getCode().toLowerCase())){
                data.put("code", 0);
                data.put("message", username + "验证码错误");
                logger.error(user.getCode() + "验证码错误");
                return data;
            }
            subject.login(token);
            BaseAdminUser adminUser = (BaseAdminUser) subject.getPrincipal();

            data.put("code", 1);
            data.put("url", "/home");
            session.setAttribute("username", adminUser.getSysUserName());
            logger.info(adminUser.getSysUserName() + "登录成功");
        } catch (UnknownAccountException e) {
            data.put("code", 0);
            data.put("message", username + "账号或密码错误");
            logger.error(username + "账号不存在");
            return data;
        } catch (DisabledAccountException e) {
            data.put("code", 0);
            data.put("message", username + "账号异常");
            logger.error(username + "账号异常");
            return data;
        } catch (AuthenticationException e) {
            data.put("code", 0);
            data.put("message", username + "密码错误");
            logger.error(password + "密码错误");
            return data;
        }
        return data;

    }

    @Override
    public Map<String, Object> setPwd(String pwd, String isPwd) {
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        System.err.println(principals.toString());
        Map<String, Object> data = new HashMap<>();
        if (!pwd.equals(isPwd)) {
            logger.error("两次输入密码不一致");
            data.put("code", 0);
            data.put("message", "输入密码不一致");
            return data;
        }
        user.setSysUserPwd(DigestUtils.Md5(user.getSysUserName(), pwd));
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i == 1) {
            data.put("code", 1);
            data.put("message", "修改成功");
        } else {
            data.put("code", 0);
            data.put("message", "修改异常");
        }

        return data;
    }

    @Override
    public PageDataResult getAdminUserList(Integer pageNum, Integer pageSize, UserSearchDTO userSearchDTO) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        logger.info("查询管理员用户列表");
        List<AdminUserDTO> list = userMapper.getAdminUserList(userSearchDTO);
        if (list.size() != 0 && list != null) {
            PageInfo<AdminUserDTO> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;
    }

    @Override
    public Map<String, Object> addAdminUser(BaseAdminUser user) {
        Map<String, Object> data = new HashMap<>();
        try {
            BaseAdminUser baseAdminUser = userMapper.selectByAdminUserName(user.getSysUserName());
            if (baseAdminUser != null) {
                data.put("code", 0);
                data.put("message", "u用户名已存在");
                logger.error("用户[新增]，结果=用户名已存在！");
                return data;
            }
            String userPhone = user.getUserPhone();
            if (userPhone.length() != 11) {
                data.put("code", 0);
                data.put("message", "手机号位数错误");
                logger.info("用户[新增]，结果=手机号位数错误");
                return data;
            }
            String userName = user.getSysUserName();
            if (StringUtils.isEmpty(user.getSysUserPwd())) {
                String password = DigestUtils.Md5(userName, "123456");
                user.setSysUserPwd(password);
            } else {
                String password = DigestUtils.Md5(userName, user.getSysUserPwd());
                user.setSysUserPwd(password);
            }
            user.setCreateTime(new Date());
            user.setUserStatus(1);
            int result = userMapper.insertSelective(user);
            if (result == 0) {
                data.put("code", 0);
                data.put("message", "新增失败！");
                logger.error("用户[新增]，结果=新增失败！");
                return data;
            }
            data.put("code", 1);
            data.put("message", "新增成功！");
            logger.info("用户[新增]，结果=新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            data.put("code", 0);
            data.put("message", "新增异常！");
            logger.error("用户[新增]异常！", e);
            return data;
        }
        return data;
    }

    @Override
    public Map<String, Object> updateAdminUser(BaseAdminUser user) {
        Map<String, Object> data = new HashMap();
        try {
            String username = user.getSysUserName();
            if (StringUtils.isEmpty(user.getSysUserPwd())) {
                String password = DigestUtils.Md5(username, "123456");
                user.setSysUserPwd(password);
            } else {
                String password = DigestUtils.Md5(username, user.getSysUserPwd());
                user.setSysUserPwd(password);
            }
            user.setUpdateTime(new Date());
            int result = userMapper.updateByPrimaryKeySelective(user);
            if (result == 0) {
                data.put("code", 0);
                data.put("message", "更新失败！");
                logger.error("用户[更新]，结果=更新失败！");
                return data;
            }
            data.put("code", 1);
            data.put("message", "更新成功！");
            logger.info("用户[更新]，结果=更新成功！");

        } catch (Exception e) {
            e.printStackTrace();
            data.put("code", 0);
            data.put("message", "修改异常！");
            logger.error("修改异常");
            return data;
        }
        return data;
    }

    @Override
    public Map<String, Object> delAdminUser(Integer id, Integer status) {
        Map<String, Object> data = new HashMap<>();
        try {

            int result = userMapper.updateAdminUserStatus(id, status);
            if (result == 0) {
                data.put("code", 0);
                data.put("message", "删除用户失败");
                logger.error("删除用户失败");
                return data;
            }
            data.put("code", 1);
            data.put("message", "删除用户成功");
            logger.info("删除用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            data.put("code", 0);
            data.put("message", "删除用户失败");
            logger.error("删除用户异常！", e);
            return data;
        }
        return data;

    }

    @Override
    public Map<String, Object> recoverAdminUser(Integer id, Integer status) {
        Map<String, Object> data = new HashMap<>();
        try {
            int result = userMapper.updateAdminUserStatus(id, status);
            if (result == 0) {
                data.put("code", 0);
                data.put("message", "恢复用户失败");
            }
            data.put("code", 1);
            data.put("message", "恢复用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            data.put("code", 0);
            data.put("message", "恢复用户异常");
            logger.error("恢复用户异常！", e);
            return data;
        }
        return data;
    }


}
