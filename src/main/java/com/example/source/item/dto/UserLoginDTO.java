package com.example.source.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserLoginDTO {

    @ApiModelProperty(value = "管理员登录姓名")
    private String sysUserName;

    @ApiModelProperty(value = "管理员登录密码")
    private String sysUserPwd;
    @ApiModelProperty(value = "验证码")
    private String code;


}
