package com.offcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Auther: lhq
 * @Date: 2021/4/13 09:52
 * @Description:
 */
@SpringBootApplication
@EnableEurekaServer
public class ScwRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwRegisterApplication.class);
    }
}
