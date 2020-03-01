package com.wxprogram.luckaward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class LuckawardApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuckawardApplication.class, args);
    }

}
