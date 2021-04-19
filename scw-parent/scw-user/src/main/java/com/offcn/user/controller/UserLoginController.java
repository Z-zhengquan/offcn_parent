package com.offcn.user.controller;

import com.offcn.user.bean.User;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


/**
 * @Auther: lhq
 * @Date: 2021/4/13 11:28
 * @Description:
 */
@RestController
@Api(tags = "第一个Swagger测试")
public class UserLoginController {



    @ApiOperation("根据ID查询用户")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="id",value="主键",required = true)
    })
    @GetMapping("/findOne")
    public User findOne(Integer id){
        User user = new User();
        user.setId(id);
        user.setName("zhangsan");
        user.setEmail("ab@abc.com");
        return user;
    }
}
