package com.wxprogram.luckaward.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/6 10:55
 * @Month:02
 */
@Configuration
public class ApplicationConfig  {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(stringRedisSerializer);
        return template;
    }

    @Bean
    public SimpleDateFormat getSimpleDateFormat(){
      return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }


}
