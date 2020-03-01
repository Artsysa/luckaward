package com.wxprogram.luckaward.service;

import com.alibaba.fastjson.JSON;
import com.wxprogram.luckaward.enumlist.Ziname;
import com.wxprogram.luckaward.mapper.UserMassageMapper;
import com.wxprogram.luckaward.mapper.ZiInfoMapper;
import com.wxprogram.luckaward.pojo.LuckZi;
import com.wxprogram.luckaward.pojo.Massage;
import com.wxprogram.luckaward.pojo.Zi;
import com.wxprogram.luckaward.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/5 21:02
 * @Month:02
 */
@Service
@Slf4j
public class ZiService  {
    @Autowired
    ZiInfoMapper ziInfoMapper;
    @Autowired
    UserMassageMapper userMassageMapper;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    SimpleDateFormat sdf;

    public Zi getZiinfo(String id){
        Zi ziinfo=null;

        try {
            String s = redisTemplate.opsForValue().get("zi" + id);
            if(s==null){
                ziinfo = ziInfoMapper.getZiinfo(id);
                //如果用户不存在数据则1新建
                if(ziinfo==null){
                    log.info("用户异常，zi表信息不存在，系统自动添加！");
                    userMassageMapper.InsertUserzi(id);
                   ziinfo= ziInfoMapper.getZiinfo(id);
                   redisTemplate.opsForValue().set("zi"+id, JSON.toJSONString(ziinfo),30, TimeUnit.MINUTES);
                   return ziinfo;
                }
                redisTemplate.opsForValue().set("zi"+id, JSON.toJSONString(ziinfo),30, TimeUnit.MINUTES);
                return ziinfo;
            }else{
                 ziinfo = JSON.parseObject(s, Zi.class);
                 return ziinfo;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
            return  ziinfo;
    }


    /**
     * 进行抽字
     * @param openid
     * @return
     */
    @Transactional
    public LuckZi getZi(String openid,String remain,Integer total){
        LuckZi luckZi = new LuckZi();
        if(Integer.parseInt(remain)>=1&&total<10){
            redisTemplate.delete("zi"+openid);
            String zis = RandomUtil.getRandom();
            luckZi.setMsg(Ziname.getziName(zis).getZi());
           ziInfoMapper.RandomZi(openid,zis);
           return luckZi;
        }
        if(total>=10){
            luckZi.setMsg("今日已经达到最大抽奖限度10次");
            System.out.println("["+sdf.format(new Date())+",openid=:"+openid+"]今日已经达到最大抽奖限度10次");
            return luckZi;
        }
        if(Integer.parseInt(remain)==0) {
            luckZi.setMsg("抽奖次数不足");
            System.out.println("[" + sdf.format(new Date()) + ",openid=:" + openid + "]抽奖机会不足");
        }
        return luckZi;
    }

    /**
     * 进行抽字测试接口
     * 没有条件限制
     * @param openid
     * @return
     */
    @Transactional
    public LuckZi getZi(String openid){
        redisTemplate.delete("zi"+openid);
        LuckZi luckZi = new LuckZi();
        String zis = RandomUtil.getRandom();
        luckZi.setMsg(Ziname.getziName(zis).getZi());
        ziInfoMapper.RandomZi(openid,zis);
        return luckZi;

    }


    /**
     * 成功分享后增加次数
     * @param openid
     * @return
     */
    public boolean share(String openid){
      redisTemplate.delete("zi"+openid);
        return ziInfoMapper.updateincreaseRemain(openid)>0?true:false;
    }


}
