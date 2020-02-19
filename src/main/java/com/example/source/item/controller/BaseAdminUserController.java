package com.example.source.item.controller;

import com.example.source.item.dto.UserLoginDTO;
import com.example.source.item.dto.UserSearchDTO;
import com.example.source.item.entity.BaseAdminUser;
import com.example.source.item.response.PageDataResult;
import com.example.source.item.service.BaseAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@Api(tags = "管理员相关接口")
public class BaseAdminUserController {

    @Autowired
    private BaseAdminUserService baseAdminUserService;

    /**
     * 管理员登录
     *
     * @param user    登录信息
     * @param session
     * @param request
     * @return
     */
    @PostMapping("/adminUser/login")
    @ResponseBody
    @ApiOperation(value = "登录接口", httpMethod = "POST")
    public Map<String, Object> login(UserLoginDTO user, HttpSession session, HttpServletRequest request) {

        Map<String, Object> data = baseAdminUserService.login(user, session, request);

        return data;
    }


    /**
     * 修改密码
     *
     * @param pwd
     * @param isPwd
     * @return
     */
    @PostMapping("/adminUser/setPwd")
    @ResponseBody
    @ApiOperation(value = "修改密码接口", response = Map.class, httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "pwd", value = "密码", required = true), @ApiImplicitParam(name = "isPwd", value = "确认密码", required = true)})
    public Map<String, Object> setPwd(@RequestParam(required = false) String pwd, @RequestParam(required = false) String isPwd) {
        Map<String, Object> data = baseAdminUserService.setPwd(pwd, isPwd);
        return data;
    }

    /**
     * 跳到系统用户列表
     *
     * @return
     */
    @RequestMapping("/adminUser/adminUserManage")
    @RequiresPermissions("/adminUser/adminUserManage")
    public String userManage() {
        return "adminUser/adminUserManage";
    }

    /**
     * 分页查询管理员用户列表
     *
     * @param pageNum
     * @param pageSize
     * @param userSearchDTO
     * @return
     */
    @RequestMapping(value = "/adminUser/getAdminUserList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取管理员用户列表", response = PageDataResult.class,httpMethod = "POST")
    @ApiImplicitParams({
                    @ApiImplicitParam(name = "pageNum", value = "页数", required = true),
                    @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true),
                    @ApiImplicitParam(name = "userSearchDTO", value = "搜索框数据", required = false)
    })
    public PageDataResult getAdminUserList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, UserSearchDTO userSearchDTO) {
        PageDataResult pageDataResult = baseAdminUserService.getAdminUserList(pageNum, pageSize, userSearchDTO);

        return pageDataResult;

    }

    /**
     * 新增/修改管理员用户
     * @param user
     * @return
     */
    @PostMapping("/adminUser/setAdminUser")
    @ResponseBody
    @ApiOperation(value = "新增/修改管理员用户接口", response = Map.class, httpMethod = "POST")
    @ApiImplicitParam(name = "user",value = "管理员用户",required = true)
    public Map<String, Object> setAdminUser(BaseAdminUser user) {
        Map<String, Object> data = new HashMap<>();
        if (user.getId() == null) {
            //新增
            data = baseAdminUserService.addAdminUser(user);
        } else {
            //修改
            data = baseAdminUserService.updateAdminUser(user);
        }
        return data;
    }


    /**
     * 删除/恢复管理员用户
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/adminUser/updateAdminUserStatus")
    @ResponseBody
    @ApiOperation(value = "删除/恢复管理员用户的接口",response = Map.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员用户id", required = true),
            @ApiImplicitParam(name = "status", value = "管理员用户状态", required = true),
    })
    public Map<String,Object> updateUserStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status){
        Map<String, Object> data = new HashMap<>();
        if (status == 0) {
            //删除用户
            data = baseAdminUserService.delAdminUser(id, status);
        } else {
            //恢复用户
            data = baseAdminUserService.recoverAdminUser(id, status);
        }
        return data;
    }


}
