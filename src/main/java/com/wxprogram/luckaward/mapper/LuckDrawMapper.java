package com.wxprogram.luckaward.mapper;

import com.wxprogram.luckaward.pojo.Reward;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/5 17:18
 * @Month:02
 */
@Mapper
public interface LuckDrawMapper {


    /**
     * 向reward表中插入一条抽奖信息
     * @param reward
     * @return
     */
    @Insert("insert into rewardlist values(#{re.openid},#{re.name},#{re.time})")
    public Integer luckdraw(@Param("re") Reward reward);

    /**
     * 抽完将后将库存减一 此过程需要加锁
     * @param column
     * @return
     */
    @Update("update prize set ${c}=${c}-1;update statistic set rewardcount=rewardcount-1")
    public Integer reduceCount(@Param("c")String column);


    /**
     * 查询某一个奖品的剩余数量
     * @param column
     * @return
     */
    @Cacheable(cacheNames = "prizecount",key = "#column")
    @Select("select ${c} from prize")
    public Integer prizecount(@Param("c")String column);
}
