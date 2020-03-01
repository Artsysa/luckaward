package com.wxprogram.luckaward.controller;

import com.wxprogram.luckaward.pojo.Massage;
import com.wxprogram.luckaward.pojo.Statistic;
import com.wxprogram.luckaward.pojo.User;
import com.wxprogram.luckaward.pojo.Zi;
import com.wxprogram.luckaward.service.LoginService;
import com.wxprogram.luckaward.service.StatisticService;
import com.wxprogram.luckaward.service.ZiService;
import com.wxprogram.luckaward.util.AuthUtil;
import com.wxprogram.luckaward.util.IpUtil;
import com.wxprogram.luckaward.vo.UserluckSimplify;
import com.wxprogram.luckaward.vo.Usersimplify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/5 15:35
 * @Month:02
 */
@Api(value = "微信登录",description = "微信登录",tags = "微信登录")
@Controller
@RequestMapping("/wx")
@CrossOrigin
@Slf4j
public class WxloginController {
    @Autowired
    LoginService loginService;
    @Autowired
    StatisticService statisticService;
    @Autowired
    ZiService ziService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;


    @ApiOperation(value = "微信登录接口",notes = "微信登录接口")
    @RequestMapping("/wxlogin")
    public void wxlogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //回调地址（必须在公网进行访问）
        String backUrl="http://49.235.43.59/wx/callback";
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ AuthUtil.APP_ID
                + "&redirect_uri="+ URLEncoder.encode(backUrl,"utf-8")
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";
        //重定向
        resp.sendRedirect(url);
    }

    @ApiOperation(value = "微信回调",notes = "微信回调")
    @GetMapping("/callback")
    public String callback(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException {
        User user = loginService.login(req, rep);
        //如果为空说明当前没有用户信息，用户需要重新登陆
        if(user==null){
            String urls="http://49.235.43.59/wx/wxlogin";
            rep.sendRedirect(urls);
            return null;
        }
        return "/assert/kaifa.html";

    }

    /**
     *
     *
     * @param req
     * @param rep
     * @return
     */
    @ApiOperation(value = "获取页面信息",notes = "获取页面信息")
    @ResponseBody
    @PostMapping("/all")
    public Massage getInfoAll(
            HttpServletRequest req,HttpServletResponse rep,
           @RequestParam(required = false) String token){
         if(token==null){
             token = (String) req.getSession().getAttribute("uuid");
             if(token==null){
                 return new Massage().error().setMsg("用户token信息不存在");
             }
         }
        String info = redisTemplate.opsForValue().get(token);
        User user = (User) JSONObject.toBean(JSONObject.fromObject(info), User.class);
        Usersimplify usersimplify = new Usersimplify();
        BeanUtils.copyProperties(user,usersimplify);
        Zi ziinfo = ziService.getZiinfo(user.getOpenid());
        if(ziinfo==null){
            return new Massage().error().setMsg("服务异常，用户字信息不存在");
        }
        Statistic statistic = statisticService.getStatistic();
        List<UserluckSimplify> gettogether = statisticService.gettogether();

        loginService.setScanCount();
        log.info("用户登入服务器:"+user.toString());

        return new Massage().setToken(token).success().setObj(Arrays.asList(usersimplify,ziinfo,statistic,gettogether));

    }

    @ApiOperation(value = "获取页面信息测试",notes = "获取页面信息测试")
    @ResponseBody
    @RequestMapping("/alltest")
    public Massage getInfoAltestl(HttpServletRequest req,HttpServletRequest rep){
        String id="oTZhLxH80vLXI15BluOE2UZlqVCE";
        User user = loginService.getUserInfo(id);
        String id1 = req.getSession().getId();
        Usersimplify userluckSimplify = new Usersimplify();
        BeanUtils.copyProperties(user,userluckSimplify);
        Zi ziinfo = ziService.getZiinfo(user.getOpenid());
        Statistic statistic = statisticService.getStatistic();
        List<UserluckSimplify> gettogether = statisticService.gettogether();

        loginService.setScanCount();
        log.info("用户登入服务器:"+user.toString());


        return new Massage().success().setObj(Arrays.asList(userluckSimplify,ziinfo,statistic,gettogether,id1));
    }




}
