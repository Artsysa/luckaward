package com.wxprogram.luckaward.mapper;

import com.wxprogram.luckaward.pojo.Reward;
import com.wxprogram.luckaward.pojo.User;
import com.wxprogram.luckaward.vo.Usersimplify;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

/*
 * @breif:处理用户信息
 * @Author: ArDaBao
 * @Date: 2020/2/5 14:27
 * @Month:02
 */
@Mapper
public interface UserMassageMapper {

      /**
       * 查询用户信息
       * @param id 用户唯一标识
       * @return
       */
      @Select("SELECT " +
              "`user`.openid,`user`.nickname,`user`.sex,`user`.city,`user`.province,`user`.country,`user`.headimgurl,\n" +
              "`user`.qualified FROM `user` WHERE `user`.openid=#{id}")
      public User getUserInfo(@Param("id") String id);

      /**
       * 判断是否存在用户
       * @param id
       * @return
       */
      @Cacheable(cacheNames = "userexit", key="#id")
      @Select("select count(openid) from user where openid=#{id}")
      public Integer existUser(@Param("id") String id);

      /**
       * 插入一条用户信息
       * @param user
       * @return
       */
      @Insert("insert into user values(#{u.openid},#{u.nickname},#{u.sex},#{u.city},#{u.province},#{u.country},#{u.headimgurl},1,#{u.ip},#{u.registertime},'2000-01-01 15:57:11')")
      public void InsertUser(@Param("u") User user);

      /**
       * 新来的用户添加其在zi表中字的信息
       * @param id
       * @return
       */
      @Insert("insert into zi values(#{id},5,0,0,0,0,0,0,0,0,0,0,0)")
      public Integer InsertUserzi(@Param("id") String id );


      /**
       * 修改qualified值改为不可抽奖
       * @param id
       * @return
       */
      @Update("update user set qualified=0 , lucktime=#{d} where openid=#{id}")
      public Integer updatequalified(@Param("id")String id,@Param("d")String date);

    /**
     * 查询用户是否具备抽奖权限
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "qualified",key = "#id")
      @Select("select qualified from user where openid=#{id}")
      public String ifqualified(@Param("id")String id);

      @Select("SELECT rewardlist.openid,rewardlist.`name`,rewardlist.time FROM rewardlist WHERE rewardlist.openid = #{id}")
      public Reward getOnebyid(@Param("id")String id);
}
