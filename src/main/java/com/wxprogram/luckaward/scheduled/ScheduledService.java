package com.wxprogram.luckaward.scheduled;

import com.wxprogram.luckaward.mapper.ZiInfoMapper;
import com.wxprogram.luckaward.service.LoginService;
import com.wxprogram.luckaward.service.StatisticService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @breif:定期清理缓存，以及保持浏览人数一致性问题
 * @Author: ArDaBao
 * @Date: 2020/2/6 11:26
 * @Month:02
 */
@Component
@Slf4j
public class ScheduledService {

    @Autowired
    LoginService loginService;
    @Autowired
    StatisticService statisticService;
    @Autowired
    SimpleDateFormat sdf;
    @Autowired
    ZiInfoMapper ziInfoMapper;
    /**
     * 每5分钟执行一次
     */
    @Transactional
    @Scheduled(cron = "0 0/1 * * * *")
    @Async
    //@Scheduled(fixedDelay = 60000)
    public void UpdateScancount(){
       try {
           Integer scanCount = Integer.valueOf((String) loginService.getScanCount());
           log.info("当前新增浏览人数："+scanCount);
           if(scanCount!=0){
               loginService.reduceScancount( scanCount);
               statisticService.setScancount(scanCount);
               //statisticService.getStatistic();
               //  System.out.println("["+sdf.format(new Date())+"]当前新增浏览人数："+scanCount);
               return;
           }
           //System.out.println("["+sdf.format(new Date())+"]当前暂无访问人数放弃执行缓存持久化操作");
       } catch (Exception e) {

       }
    }




    /**
     * 每天晚上定时设置用户抽奖数量
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void cleandata(){
         ziInfoMapper.updateinfoall();
         log.info("定时任务执行，已将所有用户抽奖信息重置");

    }
}
