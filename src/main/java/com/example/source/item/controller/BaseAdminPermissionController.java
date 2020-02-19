package com.example.source.item.controller;

import com.example.source.item.entity.BaseAdminPermission;
import com.example.source.item.entity.BaseAdminUser;
import com.example.source.item.response.PageDataResult;
import com.example.source.item.service.BaseAdminPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "权限相关接口")
public class BaseAdminPermissionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseAdminPermissionService permissionService;


    /**
     * 获取登录用户权限菜单
     * @return
     */
    @GetMapping("/adminPermission/getUserPerms")
    @ResponseBody
    @ApiOperation(value = "获取登录用户权限接口",response = Map.class,httpMethod = "GET")
    public Map<String,Object> getUserPerms(){
        logger.info("获取登录用户的权限菜单");
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> data =  permissionService.getUserPerms(user);

        return data;
    }

    /**
     * 获取权限根数据
     * @return
     */
    @GetMapping("/adminPermission/adminParentPermissionList")
    @ResponseBody
    @ApiOperation(value = "获取权限跟数据",response = List.class,httpMethod = "GET")
    public List<BaseAdminPermission> getParentPermission(){
       return permissionService.getParentPermission();
    }


    /**
     * 跳到权限菜单
     * @return
     */
    @RequestMapping("/adminPermission/adminPermissionManage")
    @RequiresPermissions("/adminPermission/adminPermissionManage")
    public String permissionManage(){
        return "adminPermission/adminPermissionManage";
    }

    /**
     * 分页查询权限列表接口
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/adminPermision/adminPermissionList")
    @ResponseBody
    @ApiOperation(value = "分页获取权限列表接口",response = PageDataResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true),
    })
    public PageDataResult getAdminPermissionList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        try {
            pageDataResult = permissionService.getAdminPermissionList(pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageDataResult;
    }

    /**
     * 新增/修改权限
     * @param permission
     * @return
     */
    @PostMapping("/adminPermission/setAdminPermission")
    @ResponseBody
    @ApiOperation(value = "新增/修改权限接口",response = Map.class,httpMethod = "POST")
    @ApiImplicitParam(name = "permission",value = "权限",required = true)
    public Map<String,Object> setAdminPermission(BaseAdminPermission permission){
        Map<String, Object> data = new HashMap<>();
        if (permission.getId()==null){
            //新增
            data = permissionService.addAdminPermission(permission);
        }else {
            //修改
            data = permissionService.updateAdminPermisson(permission);
        }
        return data;
    }

}
