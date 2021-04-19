package com.offcn.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;


/**
 * @Auther: lhq
 * @Date: 2021/4/13 11:18
 * @Description:
 */
@Configuration
@EnableSwagger2 //开启Swagger配置
public class SwaggerConfig {

    //声明接口文档的信息
    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .description("七易众筹项目说明文档")
                .contact("刘老师")
                .title("七易众筹用户模块接口说明")
                .version("V1.0")
                .termsOfServiceUrl("www.ujiuye.com")
                .build();
    }

    @Bean("用户模块")
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.offcn.user.controller"))
                .paths(PathSelectors.any())
                .build();
    }

}
