package com.offcn.project.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommons.response.AppResponse;
import com.offcn.project.contants.ProjectContants;
import com.offcn.project.enums.ProjectStatusEnum;
import com.offcn.project.pojo.TReturn;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectBaseInfoVo;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import com.offcn.project.vo.req.ProjectReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lhq
 * @Date: 2021/4/15 10:26
 * @Description:
 */
@Api(tags = "项目模块（创建项目）")
@RestController
@RequestMapping("/project")
public class ProjectCreateController {


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private ProjectCreateService projectCreateService;


    @ApiOperation("项目初始化第一步，同意协议")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "accessToken", value = "登录令牌", required = true)
    })
    @PostMapping("/initProject")
    public AppResponse<String> initProject(String accessToken) {
        //1.根据用户令牌取得用户信息，判断是否已登录
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberId)) {
            AppResponse response = AppResponse.fail(null);
            response.setMessage("登录失败，无此权限操作");
            return response;
        }
        try {
            //2.完成初始化项目
            projectCreateService.initProject(Integer.parseInt(memberId));
            return AppResponse.ok("初始化项目成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AppResponse.fail("初始化项目失败");
        }
    }


    @ApiOperation("项目初始化第二步，收集项目信息")
    @PostMapping("/saveBaseInfo")
    public AppResponse<String> saveBaseInfo(@RequestBody ProjectBaseInfoVo projectBaseInfoVo) {
        //1.根据项目令牌在缓存中取得临时对象
        String jsonStr = redisTemplate.opsForValue().get(ProjectContants.TEMP_PROJECT_PREFIX + projectBaseInfoVo.getProjectToken());
        //JSON类型转换
        ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(jsonStr, ProjectRedisStorageVo.class);

        //2.设置属性    projectBaseInfoVo--->projectRedisStorageVo
        BeanUtils.copyProperties(projectBaseInfoVo, projectRedisStorageVo);
        //3.将临时对象重新放入到缓存中
        redisTemplate.opsForValue().set(ProjectContants.TEMP_PROJECT_PREFIX + projectBaseInfoVo.getProjectToken(), JSON.toJSONString(projectRedisStorageVo));

        return AppResponse.ok("收集项目信息成功");

    }



    @ApiOperation("项目初始化第三步，收集回报项目信息")
    @PostMapping("/saveReturnInfo")
    public AppResponse<String> saveReturnInfo(@RequestBody List<ProjectReturnVo> projectReturnVoList) {
        //1.根据项目令牌在缓存中取得临时对象
        String jsonStr = redisTemplate.opsForValue().get(ProjectContants.TEMP_PROJECT_PREFIX + projectReturnVoList.get(0).getProjectToken());
        //JSON类型转换
        ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(jsonStr, ProjectRedisStorageVo.class);
        //2.设置属性
        List<TReturn> returnList = new ArrayList<>();
        for (ProjectReturnVo projectReturnVo : projectReturnVoList) {
            //     ProjectReturnVo--->TReturn
            TReturn tReturn = new TReturn();
            BeanUtils.copyProperties(projectReturnVo, tReturn);
            returnList.add(tReturn);
        }
        projectRedisStorageVo.setProjectReturns(returnList);

        //3.将临时对象重新放入到缓存中
        redisTemplate.opsForValue().set(ProjectContants.TEMP_PROJECT_PREFIX + projectReturnVoList.get(0).getProjectToken(), JSON.toJSONString(projectRedisStorageVo));
        return AppResponse.ok("收集回报信息成功");

    }


    @ApiOperation("项目初始化第四步，保存项目")
    @PostMapping("/saveProject")
    public AppResponse<String> saveProject(String accessToken,String projectToken,String status){
        //1.判断用户是否登录成功
        String memberId =  redisTemplate.opsForValue().get(accessToken);
        if(StringUtils.isEmpty(memberId)){
            AppResponse.fail("登录失败，无此操作权限");
        }
        //2.根据项目令牌在缓存中取得临时对象
        String jsonStr = redisTemplate.opsForValue().get(ProjectContants.TEMP_PROJECT_PREFIX +projectToken);
        ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(jsonStr,ProjectRedisStorageVo.class);

        //3.判断状态并赋值
        if(StringUtils.isNotEmpty(status)){
            if(status.equals(ProjectStatusEnum.DRAFT.getCode()+"")){
                ProjectStatusEnum draftStatus = ProjectStatusEnum.DRAFT;
                //4.执行保存项目
                projectCreateService.saveProject(draftStatus,projectRedisStorageVo);
            }else if(status.equals(ProjectStatusEnum.SUBMIT_AUTH.getCode()+"")){
                ProjectStatusEnum submitStatus = ProjectStatusEnum.SUBMIT_AUTH;
                projectCreateService.saveProject(submitStatus,projectRedisStorageVo);
            }else{
                return AppResponse.fail("非法状态");
            }
        }else{
            return AppResponse.fail("非法状态");
        }
        //5.清空缓存
        redisTemplate.delete(ProjectContants.TEMP_PROJECT_PREFIX +projectToken);
        return AppResponse.ok("保存项目成功");
    }
}
