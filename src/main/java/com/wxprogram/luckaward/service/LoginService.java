package com.wxprogram.luckaward.service;

import com.wxprogram.luckaward.mapper.UserMassageMapper;
import com.wxprogram.luckaward.pojo.User;
import com.wxprogram.luckaward.pojo.Zi;
import com.wxprogram.luckaward.util.AuthUtil;
import com.wxprogram.luckaward.util.IpUtil;
import com.wxprogram.luckaward.vo.UserluckSimplify;
import com.wxprogram.luckaward.vo.Usersimplify;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.security.util.AuthResources_es;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/5 17:50
 * @Month:02
 */
@Service
@Slf4j
public class LoginService {
    @Autowired
    UserMassageMapper userMassageMapper;
    @Autowired
    StatisticService statisticService;
    @Autowired
    ZiService ziService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    SimpleDateFormat sdf;

       public User login(HttpServletRequest req, HttpServletResponse resp) {

           /**
            * 3.获取code
            */
           String code = req.getParameter("code");
           String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APP_ID
                   + "&secret=" + AuthUtil.APP_SECRET
                   + "&code=" + code
                   + "&grant_type=authorization_code";
           JSONObject jsonObject;
           try {
               jsonObject = AuthUtil.doGetJson(url);

               String openid = jsonObject.getString("openid");
               String token = jsonObject.getString("access_token");
               //从缓存中查询是否存在用户
                  String uuid = redisTemplate.opsForValue().get(openid);
                   User user=null;
                   if(uuid==null){
                      user = getUserInfo(openid);
                      //判断数据库是否存在用户，，不存在则创建用户
                       if(user==null){
                           return JSONtranObject(openid,token,IpUtil.getIpAddress(req),req);
                       }
                      redisTemplate.opsForValue().set(user.getOpenid(),user.toString(),30, TimeUnit.MINUTES);
                      req.getSession().setAttribute("uuid",user.getOpenid());
                      return user ;
                  }//缓存中有用户信息
                   else{
                       user= (User) JSONObject.toBean(JSONObject.fromObject(uuid),User.class);
                       req.getSession().setAttribute("uuid",user.getOpenid());
                       return user;
                   }



           }catch (Exception e1){
               System.out.println(e1);
           } return null;
       }

       public Integer exitUser(String id){
           return userMassageMapper.existUser(id);
       }

       public User getUserInfo(String id){
           return userMassageMapper.getUserInfo(id);
       }

       @Transactional(propagation = Propagation.REQUIRES_NEW)
       public User JSONtranObject(String openid,String token,String ip,HttpServletRequest req) throws Exception {
           String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token="+token
                   + "&openid="+openid
                   + "&lang=zh_CN";
               JSONObject userInfo=AuthUtil.doGetJson(infoUrl);
           String s = userInfo.toString();
           s=s.substring(0,s.length()-16)+"}";
           User user = (User) JSONObject.toBean(JSONObject.fromObject(s), User.class);
           user.setIp(ip);
           user.setRegistertime(sdf.format(new Date()));
           log.info("有新注册的用户:"+user.toString());
           userMassageMapper.InsertUser(user);
           userMassageMapper.InsertUserzi(user.getOpenid());
           redisTemplate.opsForValue().set(user.getOpenid(),user.toString(),30, TimeUnit.MINUTES);
           req.getSession().setAttribute("uuid",user.getOpenid());
           return user;
       }

       public void setScanCount(){
           String s = redisTemplate.opsForValue().get("scancount");
           if(s==null){
               initScanCount();
           }
           redisTemplate.opsForValue().increment("scancount",1);
       }
       public Object getScanCount(){
       return  redisTemplate.opsForValue().get("scancount");
       }
       public void reduceScancount(Integer now){
        redisTemplate.opsForValue().set("scancount",String.valueOf((now-now)));
       }
       public synchronized void initScanCount(){ redisTemplate.opsForValue().set("scancount","1",10,TimeUnit.DAYS);}

}
