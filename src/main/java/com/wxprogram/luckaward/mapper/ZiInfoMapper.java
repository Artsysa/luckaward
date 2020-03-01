package com.wxprogram.luckaward.mapper;

import com.wxprogram.luckaward.pojo.Zi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;


/*
 * @breif:关于zi表的一些基本操作
 * @Author: ArDaBao
 * @Date: 2020/2/5 16:05
 * @Month:02
 */
@Mapper
public interface ZiInfoMapper {

    /**
     * 根据用户id查询用户zi的数量
     * @param id
     * @return
     */
    @Select("SELECT " +
            "zi.openid,zi.remain,zi.t1,zi.t2,zi.t3,zi.t4,\n" +
            "zi.t5,zi.t6,zi.t7,zi.t8,zi.t9,zi.t10,zi.total FROM\n" +
            "zi WHERE zi.openid=#{id}")
    public Zi getZiinfo(@Param("id") String id);

    /**
     * 抽到某个字并将其数量加1
     * @param id
     * @param column
     * @return
     */

    @Update("update zi set ${c}=${c}+1 ,total=total+1 ,remain=remain-1 where openid=#{id}")
    public Integer RandomZi(@Param("id")String id,@Param("c")String column);

    @Select("select total from zi where openid=#{id}")
    public Integer getZitotal(@Param("id")String id);

    /**
     * 集齐字进行抽奖前，先将所有子数量减一
     * @param id
     * @return
     */

    @Update("update zi set t1=t1-1, t2=t2-1, t3=t3-1, t4=t4-1,t5=t5-1, t6=t6-1, t7=t7-1, t8=t8-1, t9=t9-1, t10=t10-1 where" +
            " openid=#{id}")
    public Integer AllReduceone(@Param("id") String id);

    /**
     * 更新当前剩余抽奖次数
     * @param id
     * @param number
     * @return
     */
    @CacheEvict(allEntries = true,cacheNames = "zi",key = "#id")
    @Update("update zi set remain=#{n} where openid=#{id}")
    public Integer updatenowRemain(@Param("id") String id,@Param("n") String number);

    /**
     * 更新当前剩余抽奖次数
     * @param id
     * @param number
     * @return
     */

    @Update("update zi set remain=remain+1 where openid=#{id}")
    public Integer updateincreaseRemain(@Param("id") String id);


    /**
     * 每个用户每天五次抽奖机会
     * @return
     */
    @Update("update zi set remain=5,total=0")
    public Integer updateinfoall();

}
