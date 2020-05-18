package com.wxprogram.luckaward.service;

import com.alibaba.fastjson.JSON;
import com.wxprogram.luckaward.enumlist.Prizename;
import com.wxprogram.luckaward.mapper.LuckDrawMapper;
import com.wxprogram.luckaward.mapper.UserMassageMapper;
import com.wxprogram.luckaward.mapper.ZiInfoMapper;
import com.wxprogram.luckaward.pojo.LuckZi;
import com.wxprogram.luckaward.pojo.Reward;
import com.wxprogram.luckaward.pojo.Zi;
import com.wxprogram.luckaward.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/6 16:49
 * @Month:02
 */
@Service
public class LuckDrawService {

    @Autowired
    ZiInfoMapper ziInfoMapper;
    @Autowired
    ZiService ziService;
    @Autowired
    UserMassageMapper userMassageMapper;
    @Autowired
    LuckDrawMapper luckDrawMapper;
    @Autowired
    SimpleDateFormat sdf;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    /**
     * 抽奖
     * @param openid
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String luckdraw(String openid){
        //判断是否具备抽资格
        if(ifqualifity(openid)){
            Zi ziinfo = ziService.getZiinfo(openid);
            //判断当前的字数量是否够
            if(ifall(ziinfo)){
                String id = RandomUtil.getRandoms();
                String infoname = Prizename.getPrizeName(id).getName();
                //判断库存
                if(ifcount(id)){
                    if(insertaware(openid,infoname)&&reducecount(id)&&banluckdraw(openid)&&reducezi(openid)){
                        System.out.println("["+sdf.format(new Date())+",openid="+openid+",infoname="+infoname+"]");
                        return infoname;
                    }
                }else{
                    System.out.println("["+sdf.format(new Date())+",openid="+openid+",infoname="+infoname+"]库存不够自动转为精美挂件");
                    infoname=Prizename.t4.getName();
                    if(insertaware(openid,infoname)&&banluckdraw(openid)){
                        return infoname;
                    }
                }
            }
            System.out.println("["+sdf.format(new Date())+",openid="+openid+"]您当前的字还未集齐");
             return "您当前的字还未集齐";
        }
        System.out.println("["+sdf.format(new Date())+",openid="+openid+"]您已经抽过奖品");
        return "您已经抽过奖品";

    }

    /**
     * 抽奖测试
     * @param openid
     * @return
     */

    public String luckdrawtest(String openid){
        String id = RandomUtil.getRandoms();
        String infoname = Prizename.getPrizeName(id).getName();
        insertaware(openid,infoname);
        return infoname;

    }


    /**
     * 将当前字每个减一
     * @param openid
     * @return
     */
    public boolean reducezi(String openid){
        redisTemplate.delete("zi"+openid);
     return   ziInfoMapper.AllReduceone(openid)>0?true:false;
    }

    /**
     * 判断用户是否具备抽奖资格
     * @param openid
     * @return
     */
    public boolean ifqualifity(String openid){
        return "1".equals(userMassageMapper.ifqualified(openid));
    }

    /**
     * 库存减少一个
     * @param name
     * @return
     */
    public synchronized boolean reducecount(String name){

        redisTemplate.delete("prizecount"+name);
        return luckDrawMapper.reduceCount(name)>0? true:false;
    }

    /**
     * 判断是否有货
     * @param name
     * @return
     */
    public boolean ifcount(String name){
      return  luckDrawMapper.prizecount(name)>0?true:false;
    }
    public boolean ifall(Zi zi){
        return zi.getT1()>0&&zi.getT2()>0&&zi.getT3()>0&&zi.getT4()>0&&
                zi.getT5()>0&&zi.getT6()>0&&zi.getT7()>0&&zi.getT8()>0&&
                zi.getT9()>0&&zi.getT10()>0;
    }

    /**
     * 生成获奖信息并插入数据库
     * @param openid
     * @param name
     * @return
     */
    public boolean insertaware(String openid,String name){
        redisTemplate.delete("reward"+openid);
    return luckDrawMapper.luckdraw(
            new Reward(openid,name,sdf.format(new Date())))>0?true:false;
    }

    /**
     * 设置用户不可抽奖
     * @param openid
     * @return
     */
    public boolean banluckdraw(String openid){
        redisTemplate.delete("qualified"+openid);
        redisTemplate.delete("user"+openid);
      return  userMassageMapper.updatequalified(openid,sdf.format(new Date()))>0?true:false;
    }

    /**
     * 获取用户中奖记录
     *
     * @param id
     * @return
     */
    public Reward getByid(String id){
        String s = redisTemplate.opsForValue().get("reward" + id);
        Reward rewad=null;
        if(s==null){
           rewad= userMassageMapper.getOnebyid(id);
           redisTemplate.opsForValue().set("reward"+id, JSON.toJSONString(rewad),30, TimeUnit.MINUTES);
        return rewad;
        }else{
           rewad = JSON.parseObject(s, Reward.class);
           return rewad;
        }

    }
}
