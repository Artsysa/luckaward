package com.wxprogram.luckaward.mapper;

import com.wxprogram.luckaward.pojo.Statistic;
import com.wxprogram.luckaward.pojo.User;
import com.wxprogram.luckaward.vo.UserluckSimplify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/*
 * @breif:用于统计信息的管理
 * @Author: ArDaBao
 * @Date: 2020/2/5 16:46
 * @Month:02
 */
@Mapper
public interface StatisticMapper {

    /**
     * 查询统计信息
     * @return
     */
    @Select("SELECT\n" +
            "statistic.usercount,statistic.rewardcount,statistic.scancount\n" +
            "FROM statistic")
    public Statistic getInfo();


    /**
     * 修改统计信息
     * @param column
     * @return
     */

    @Update("update statistic set ${c}=${c}+1 ")
    public Integer updateInfo(@Param("c")String column);

    /**
     * 修改统计信息
     * @param column
     * @return
     */

    @Update("update statistic set ${c}=${c}+#{n} ")
    public Integer updateInfos(@Param("c")String column,@Param("n") int count);

    /**
     * 查询已经集齐卡片的用户，根据用户是否具备抽奖资格判断，即用户是否已经抽过将
     * @return
     */
    @Select("SELECT\n" +
            "\t`user`.nickname,\n" +
            "\t`user`.headimgurl \n" +
            "FROM\n" +
            "\t`user` \n" +
            "WHERE\n" +
            "\t`user`.qualified = 0 order by lucktime desc limit 5")
    public  List<UserluckSimplify> gettogether();
}
