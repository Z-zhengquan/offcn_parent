package com.offcn.project.conifg;

import com.offcn.utils.OSSTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: lhq
 * @Date: 2021/4/14 14:30
 * @Description:
 */
@Configuration
public class ProjectConfig {

    @ConfigurationProperties(prefix = "oss")
    @Bean
    public OSSTemplate getOSSTemplate(){
        return new OSSTemplate();
    }
}
