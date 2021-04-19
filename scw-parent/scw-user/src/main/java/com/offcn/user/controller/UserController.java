package com.offcn.user.controller;

import com.offcn.dycommons.response.AppResponse;
import com.offcn.user.component.SmsTemplate;
import com.offcn.user.pojo.TMember;
import com.offcn.user.service.UserService;
import com.offcn.user.vo.req.UserRegisterVo;
import com.offcn.user.vo.resp.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: lhq
 * @Date: 2021/4/13 14:50
 * @Description:
 */
@RestController
@Api(tags = "用户模块（用于登录、注册功能）")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private UserService userService;


    @ApiOperation("发送手机验证码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "phone", value = "手机号", required = true)
    })
    @GetMapping("/sendCode")
    public AppResponse<String> sendCode(String phone) {
        try {
            //1.生成随机数
            String code = UUID.randomUUID().toString().substring(0, 4);

            //2.将验证码放入缓存
            redisTemplate.opsForValue().set(phone, code, 60 * 5, TimeUnit.SECONDS);

            //3.发送验证码
            HttpResponse response = smsTemplate.sendSms(phone, code);
            System.out.println(EntityUtils.toString(response.getEntity()));

            return AppResponse.ok("发送验证码成功");
        } catch (IOException e) {
            e.printStackTrace();
            return AppResponse.fail("发送验证码失败");
        }

    }


    @ApiOperation("注册会员")
    @PostMapping("/register")
    public AppResponse<String> register(UserRegisterVo registerVo) {
        //1.校验验证码
        String code = redisTemplate.opsForValue().get(registerVo.getLoginacct());
        if (StringUtils.isEmpty(code)) {
            return AppResponse.fail("验证码不存在");
        }
        if (!code.equalsIgnoreCase(registerVo.getCode())) {
            return AppResponse.fail("验证码错误！");
        }

        //2.赋值操作   （复制属性）  vo-->pojo
        TMember tMember = new TMember();
        //tMember.setLoginacct(registerVo.getLoginacct());
        BeanUtils.copyProperties(registerVo, tMember);

        //3.完成保存
        userService.register(tMember);

        //4.清空缓存中的验证码
        redisTemplate.delete(registerVo.getLoginacct());

        return AppResponse.ok("注册成功");
    }



    @ApiOperation("用户登录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginacct", value = "手机号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @GetMapping("/login")
    public AppResponse<UserRespVo> login(String loginacct, String password){
        //1.判断用户是否登录成功
        TMember tMember =  userService.login(loginacct,password);
        if(tMember==null){
            AppResponse response = AppResponse.fail(null);
            response.setMessage("用户名和密码不正确");
            return response;
        }
        //2.如果登录成功，生成一个随机令牌，并放入到缓存中
        String accessToken = UUID.randomUUID().toString().replace("-","");
        //设置令牌两小时超时
        //redisTemplate.opsForValue().set(accessToken,tMember.getId()+"",60*2,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(accessToken,tMember.getId()+"");
        //3.复制属性    pojo--->vo
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(tMember,userRespVo);
        userRespVo.setAccessToken(accessToken);
        //4.返回Vo
        return AppResponse.ok(userRespVo);
    }


    @ApiOperation("根据ID查询用户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "主键", required = true)
    })
    @GetMapping("/findMemberById")
    public AppResponse<UserRespVo> findMemberById(Integer id){
        TMember member = userService.findMemberById(id);
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(member,userRespVo);
        return AppResponse.ok(userRespVo);
    }


}
