package com.offcn.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: lhq
 * @Date: 2021/4/14 14:33
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.offcn.project.mapper")
public class ScwProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwProjectApplication.class);
    }
}
