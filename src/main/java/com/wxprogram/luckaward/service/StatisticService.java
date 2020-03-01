package com.wxprogram.luckaward.service;

import com.alibaba.fastjson.JSON;
import com.wxprogram.luckaward.mapper.StatisticMapper;
import com.wxprogram.luckaward.pojo.Statistic;
import com.wxprogram.luckaward.pojo.User;
import com.wxprogram.luckaward.vo.UserluckSimplify;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import net.sf.json.util.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * @breif:处理统计信息
 * @Author: ArDaBao
 * @Date: 2020/2/5 20:54
 * @Month:02
 */
@Service
public class StatisticService {
    @Autowired
    StatisticMapper statistic;
    @Autowired
    RedisTemplate<String,String> redisTemplate;




    public synchronized Integer setScancount(int number){
       // redisTemplate.delete("st");
        return statistic.updateInfos("scancount",number);
    }
    /**
     * 返回统计的信息
     * @return
     */
    public  Statistic getStatistic(){
        Statistic info=null;
        String s = redisTemplate.opsForValue().get("st");
        if(s==null){
            info = get();
            redisTemplate.opsForValue().set("st",JSON.toJSONString(info),2,TimeUnit.HOURS);
            return info;
        }else{
           info = JSON.parseObject(s, Statistic.class);
           return info;
        }

    }

    public synchronized Statistic get(){
        return statistic.getInfo();
    }

    /**
     * 获取已经抽过奖品的用户列表
     * @return
     */
    public List<UserluckSimplify> gettogether(){
        List<UserluckSimplify> list=null;
        String s = redisTemplate.opsForValue().get("prizelist");
        if(s==null){
           list = getinfoAll();
            redisTemplate.opsForValue().set("prizelist", JSON.toJSONString(list).toString(),4, TimeUnit.HOURS);
            return list;
        }else{
           list = (List<UserluckSimplify>) com.alibaba.fastjson.JSONObject.parseArray(s, UserluckSimplify.class);
           return list;
        }

    }

    public synchronized List<UserluckSimplify> getinfoAll(){
        return statistic.gettogether();
    }
}
