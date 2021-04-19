package com.offcn.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: lhq
 * @Date: 2021/4/13 10:36
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.offcn.user.mapper")
public class ScwUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwUserApplication.class);
    }
}
