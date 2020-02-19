package com.example.source.item.controller;

import com.example.source.item.util.VerifyCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@Api(tags = "验证码相关接口")
public class VerifyCodeController {


    @GetMapping("/verCode")
    @ResponseBody
    @ApiOperation(value = "获取验证码接口", httpMethod = "GET")
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        HttpSession session = req.getSession();
        session.setAttribute("code", text);
        VerifyCode.output(image, resp.getOutputStream());

    }


}
