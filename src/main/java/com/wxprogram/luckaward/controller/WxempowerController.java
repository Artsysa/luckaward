package com.wxprogram.luckaward.controller;

import com.wxprogram.luckaward.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * @breif:微信接口授权
 * @Author: ArDaBao
 * @Date: 2020/2/5 14:23
 * @Month:02
 */
@Api(value = "微信接口授权api",description = "微信接口授权api",tags = "微信接口授权api")
@RequestMapping("/wxpower")
@Controller
@CrossOrigin
public class WxempowerController {
    @ApiOperation(value = "微信接口授权",notes = "微信接口授权")
    @GetMapping("/empower")
    public void wx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }



}
