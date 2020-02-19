package com.example.source.item.controller.api;

import com.example.source.item.dto.UserSeekDTO;
import com.example.source.item.entity.User;
import com.example.source.item.entity.UserInfo;
import com.example.source.item.response.PageDataResult;
import com.example.source.item.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
@Api(tags = "用户管理相关接口")
public class UserController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    /**
     * 跳到用户列表
     * @return
     */
    @RequestMapping("/user/userManage")
    @RequiresPermissions("/api/user/userManage")
    public String userManage(){
        return "user/userManage";
    }


    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @param userSeekDTO
     * @return
     */
    @PostMapping("/user/getUserList")
    @ResponseBody
    @ApiOperation(value = "分页获取用户列表接口",response = PageDataResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true),
    })
    public PageDataResult getUserList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                      UserSeekDTO userSeekDTO){

       return userService.getUserList(pageNum,pageSize,userSeekDTO);

    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/user/addUser")
    @ResponseBody
    @ApiOperation(value = "新增用户接口",response = Map.class,httpMethod = "POST")
    @ApiImplicitParam(name = "user", value = "用户", required = true)
    public Map<String,Object> setUser(User user){

        Map<String, Object> data = new HashMap<>();

        try {
            logger.info("开始新增用户");
         data = userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("事务回滚成功，新增异常");
            data.put("code",0);
            data.put("message","新增异常");
        }
        return data;
    }


    /**
     * 完善/修改用户信息
     * @param userInfo
     * @return
     */
    @PostMapping("/user/setUser")
    @ResponseBody
    @ApiOperation(value = "完善/修改用户信息接口",response = Map.class,httpMethod = "POST")
    @ApiImplicitParam(name = "userInfo", value = "用户详情", required = true)
    public Map<String,Object> setUer(UserInfo userInfo){

        Map<String, Object> data = new HashMap<>();

        try {
            logger.info("开始完善/修改用户信息");
            data = userService.setUser(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("完善/修改用户信息异常");
            data.put("code",0);
            data.put("message","完善/修改用户信息异常");
        }
        return data;
    }

    @PostMapping("/user/updateUserStatus")
    @ResponseBody
    @ApiOperation(value = "删除/恢复接口",response = Map.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",required = true),
            @ApiImplicitParam(name = "status",value = "用户状态",required = true)
    })
    public Map<String,Object> updateUserStatus(@RequestParam("id") Integer id,@RequestParam("status") Integer status){
        Map<String, Object> data = new HashMap<>();
        if (status==0){
            logger.info("删除用户");
            data = userService.delUser(id,status);
        }else {
            logger.info("恢复用户");
            data = userService.recoverUser(id,status);
        }

        return data;

    }

}
