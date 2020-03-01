package com.wxprogram.luckaward.controller;

import com.wxprogram.luckaward.pojo.*;
import com.wxprogram.luckaward.service.LuckDrawService;
import com.wxprogram.luckaward.service.ZiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/6 13:57
 * @Month:02
 */
@Api(value = "抽字抽奖api",description = "抽字抽奖api",tags = "抽字抽奖api")
@Controller
@RequestMapping("/lk")
@CrossOrigin
public class LuckDrawController {

    @Autowired
    ZiService ziService;
    @Autowired
    LuckDrawService luckDrawService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    /**
     * 进行抽字
     * @param openid
     * @param remain剩余次数
     * @param total抽奖已经抽的次数
     * @return
     */
    @ApiOperation(value = "抽字",notes = "抽字")
    @ResponseBody
    @PostMapping("/zi")
    public Massage luckzi(
                               @RequestParam("remain") String remain,
                               @RequestParam("total")  String total,
                        @RequestParam("token")  String token
    ){
        if(token==null){
            return new Massage().error();
        }
        String s = redisTemplate.opsForValue().get(token);
        if(s==null){
            return new Massage().error().setMsg("用户信息异常");
        }
       User user= (User) JSONObject.toBean(JSONObject.fromObject(s),User.class);
       LuckZi zi = ziService.getZi(user.getOpenid(),remain,Integer.parseInt(total));
        Zi ziinfo = ziService.getZiinfo(user.getOpenid());
        if(ziinfo==null){
            return new Massage().error().setMsg("用户字信息不存在");
        }
        return new Massage().setToken(token).success().setObj(Arrays.asList(zi,ziinfo));
    }

    /**
     * 抽奖接口
     * @param openid
     * @return
     */
    @ApiOperation(value = "抽奖",notes = "抽奖")
    @ResponseBody
    @RequestMapping("/draw")
    public Massage luckdraw(
                            @RequestParam("token")  String token){
        if(token==null){
            return new Massage().error();
        }
        String s = redisTemplate.opsForValue().get(token);
        if(s==null){
            return new Massage().error().setMsg("用户信息异常");
        }
        User user= (User) JSONObject.toBean(JSONObject.fromObject(s),User.class);
        String luckdraw = luckDrawService.luckdraw(user.getOpenid());
        return new Massage().setToken(token).success().setObj(luckdraw);
    }

    /**
     * 抽奖接口测试
     * @param openid
     * @return
     */
    @ApiOperation(value = "抽奖测试",notes = "抽奖测试")
    @ResponseBody
    @RequestMapping("/testdraw")
    public Massage luckdrawtest(@RequestParam("id") String openid){
        String luckdraw = luckDrawService.luckdrawtest(openid);
        return new Massage().success().setObj(luckdraw);
    }
    /**
     * 测试抽字的接口，必须先提供openid
     * @param openid
     * @return
     */
    @ApiOperation(value = "抽字测试",notes = "抽字测试")
    @ResponseBody
    @RequestMapping("/testzi")
    public Massage luckzitest(@RequestParam("openid") String openid){
        LuckZi zi = ziService.getZi(openid);
        Zi ziinfo = ziService.getZiinfo(openid);
        return new Massage().success().setObj(Arrays.asList(zi,ziinfo));
    }

    /**
     * 分享增加次数
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping("/share")
    public Massage share(
                               @RequestParam("token")  String token){
        if(token==null){
            return new Massage().error();
        }
        String s = redisTemplate.opsForValue().get(token);
        if(s==null){
            return new Massage().error().setMsg("用户信息异常");
        }
        User user= (User) JSONObject.toBean(JSONObject.fromObject(s),User.class);
        return ziService.share(user.getOpenid())==true?new Massage().setToken(s).success():new Massage().error();

    }

    /**
     * 获取用户抽奖记录
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping("/reward")
    public Massage getRewardbyid(
            @RequestParam("token")  String token
    ){
        if(token==null){
            return new Massage().error();
        }
        String s = redisTemplate.opsForValue().get(token);
        if(s==null){
            return new Massage().error().setMsg("用户信息异常");
        }
        Reward byid = luckDrawService.getByid(token);
        if(byid==null){
            return new Massage().error().setMsg("当前用户暂无抽奖记录");
        }
        return new Massage().success().setToken(token).setObj(byid);
    }

    /**测试接口！！
     * 获取用户抽奖记录
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping("/testreward")
    public Massage getRewardbyids(
            @RequestParam("token")  String token
    ){
        if(token==null){
            return new Massage().error();
        }
        Reward byid = luckDrawService.getByid(token);
        if(byid==null){
            return new Massage().error().setMsg("当前用户暂无抽奖记录");
        }
        return new Massage().success().setToken(token).setObj(byid);
    }

}
