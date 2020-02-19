package com.example.source.item.controller;

import com.example.source.item.entity.BaseAdminRole;
import com.example.source.item.response.PageDataResult;
import com.example.source.item.service.impl.BaseAdminRoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "角色相关接口")
public class BaseAdminRoleController {

    @Autowired
    private BaseAdminRoleServiceImpl roleService;


    /**
     * 获取角色列表
     * @return
     */
    @GetMapping("/adminRole/getRoles")
    @ResponseBody
    @ApiOperation(value = "获取角色列表接口",response = List.class,httpMethod = "GET")
    public List<BaseAdminRole>  getRoles(){
        List<BaseAdminRole> list = roleService.getRoles();
        return list;
    }

    /**
     * 跳到角色列表
     * @return
     */
    @RequestMapping("/adminRole/adminRoleManage")
    @RequiresPermissions("/adminRole/adminRoleManage")
    public String roleManage(){
        return "adminRole/adminRoleManage";
    }


    /**
     * 分页获取角色列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/adminRole/getAdminRoleList")
    @ResponseBody
    @ApiOperation(value = "分页获取角色列表接口",response = PageDataResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true),
    })
    public PageDataResult getRoleList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

       PageDataResult pageDataResult =  roleService.getRoleList(pageNum,pageSize);

       return pageDataResult;
    }


    /**
     * 新增/修改角色接口
     * @param role
     * @return
     */
    @PostMapping("/adminRole/setAdminRole")
    @ResponseBody
    @ApiOperation(value = "新增/修改角色接口",response = Map.class,httpMethod = "POST")
    @ApiImplicitParam(name = "role",value = "角色",required = true)
    public Map<String,Object> setAdminRole(BaseAdminRole role){
        Map<String, Object> data = new HashMap<>();
        if (role.getId()==null){
            //新增
            data = roleService.addAdminRole(role);
        }else {
            //修改
            data= roleService.updateAdminRole(role);
        }
        return data;
    }

    /**
     * 删除/恢复角色
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/adminRole/updateAdminRoleStatus")
    @ResponseBody
    @ApiOperation(value = "删除/恢复角色的接口",response = Map.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(name = "status", value = "角色状态", required = true),
    })
    public Map<String,Object> updateAdminRoleStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status){
        Map<String, Object> data = new HashMap<>();
        if (status==0){
            //删除角色
            data = roleService.delAdminRoleStatus(id,status);
        }else {
            //恢复角色
            data = roleService.recoverAdminRoleStatus(id,status);
        }
        return data;
    }
}
