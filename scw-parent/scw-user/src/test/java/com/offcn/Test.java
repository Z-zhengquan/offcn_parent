package com.offcn;

/**
 * @Auther: lhq
 * @Date: 2021/4/13 10:45
 * @Description:
 */

import com.offcn.user.ScwUserApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@ContextConfiguration
@SpringBootTest(classes = {ScwUserApplication.class})
public class Test {

    @Autowired   //五种数据格式
    private RedisTemplate redisTemplate;
    @Autowired   //相比redisTemplate结构更加简单  但是注意：与redisTemplate存储的数据不能互通
    private StringRedisTemplate stringRedisTemplate;

    @org.junit.Test
    public void save(){
        stringRedisTemplate.opsForValue().set("name","欢迎来到优就业学习！");
    }

}
