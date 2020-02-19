package com.example.source.item.shiro;

import com.example.source.item.entity.BaseAdminPermission;
import com.example.source.item.entity.BaseAdminRole;
import com.example.source.item.entity.BaseAdminUser;
import com.example.source.item.service.BaseAdminPermissionService;
import com.example.source.item.service.BaseAdminRoleService;
import com.example.source.item.service.BaseAdminUserService;
import com.example.source.item.util.DigestUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private BaseAdminUserService baseAdminUserService;

    @Autowired
    private BaseAdminRoleService roleService;

    @Autowired
    private BaseAdminPermissionService permissionService;


    /**
     * 角色权限和应用权限的添加
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户
        BaseAdminUser user = (BaseAdminUser) principalCollection.getPrimaryPrincipal();
        //添加角色信息 权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //根据用户名去数据库查所需用户信息
        BaseAdminUser adminUser = baseAdminUserService.selectByRoleName(user.getSysUserName());
        //超级管理员
        if (adminUser.getSysUserName().equals("admin")) {
            simpleAuthorizationInfo.addRole("*");
            simpleAuthorizationInfo.addStringPermission("*");
        } else {
            BaseAdminRole role = roleService.selectById(adminUser.getRoleId());

            if (null != role) {
                String permissions = role.getPermissions();
                for (String pid : permissions.split(",")) {
                    simpleAuthorizationInfo.addRole(role.getRoleName());
                    BaseAdminPermission permission = permissionService.getById(Integer.parseInt(pid));
                    //权限为系统管理
                    if (Integer.parseInt(pid) == 1) {
                        simpleAuthorizationInfo.addStringPermission("*");
                    }
                    if (null != permission && Integer.parseInt(pid) != 1) {
                        simpleAuthorizationInfo.addStringPermission(permission.getUrl());
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户身份验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //加这一步的目的是post请求会先进入验证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
//        String username = authenticationToken.getPrincipal().toString();
        String password = new String((char[]) authenticationToken.getCredentials());

        BaseAdminUser baseAdminUser = baseAdminUserService.selectByRoleName(username);
        if (baseAdminUser == null || !DigestUtils.Md5(username,password).equals(baseAdminUser.getSysUserPwd())) {

            return null;

        }
        //这里验证authenticationToken和simpleAuthenticationInfo的信息
        return new SimpleAuthenticationInfo(baseAdminUser, DigestUtils.Md5(username,password), ByteSource.Util.bytes(username), getName());


    }
}
