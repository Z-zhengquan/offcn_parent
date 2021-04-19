package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.project.contants.ProjectContants;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.enums.ProjectStatusEnum;
import com.offcn.project.mapper.*;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: lhq
 * @Date: 2021/4/15 10:19
 * @Description:
 */
@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private TProjectMapper projectMapper;


    @Autowired
    private TProjectImagesMapper projectImagesMapper;


    @Autowired
    private TReturnMapper returnMapper;

    @Autowired
    private TProjectTagMapper projectTagMapper;


    @Autowired
    private TProjectTypeMapper projectTypeMapper;


    /**
     * 项目发起第一步  初始化项目
     *
     * @param memberId
     */
    @Override
    public void initProject(Integer memberId) {
        //1.初始化临时对象
        ProjectRedisStorageVo projectRedisStorageVo = new ProjectRedisStorageVo();
        //2.设置用户ID
        projectRedisStorageVo.setMemberid(memberId);
        //3.将临时对象放入到缓存中
        //生成一个随机项目令牌
        String projectToken =  UUID.randomUUID().toString().replace("-","");
        String jsonStr = JSON.toJSONString(projectRedisStorageVo);

        redisTemplate.opsForValue().set(ProjectContants.TEMP_PROJECT_PREFIX+projectToken,jsonStr);
    }

    /**
     * 保存项目
     *
     * @param projectStatusEnum
     * @param projectRedisStorageVo
     */
    @Override
    public void saveProject(ProjectStatusEnum projectStatusEnum, ProjectRedisStorageVo projectRedisStorageVo) {
        //1.保存项目信息
        TProject tProject = new TProject();
        BeanUtils.copyProperties(projectRedisStorageVo,tProject);
        tProject.setMoney(new Long(projectRedisStorageVo.getMoney()));      //筹资金额
        tProject.setStatus(projectStatusEnum.getCode()+"");                 //审核状态
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tProject.setCreatedate(sdf.format(new Date()));                     //创建日期
        projectMapper.insertSelective(tProject);
        Integer projectId = tProject.getId();       //项目ID
        //2.保存图片信息（头部图片、详情图片）
        TProjectImages headerImage = new TProjectImages(null,projectId,projectRedisStorageVo.getHeaderImage(), ProjectImageTypeEnume.HEADER.getCode().byteValue());
        projectImagesMapper.insertSelective(headerImage);   //头部图片
        if(!CollectionUtils.isEmpty(projectRedisStorageVo.getDetailsImage())){
            for (String imgUrl:projectRedisStorageVo.getDetailsImage()){
                TProjectImages detailsImage = new TProjectImages(null,projectId,imgUrl,ProjectImageTypeEnume.DETAILS.getCode().byteValue());
                projectImagesMapper.insertSelective(detailsImage);          //详情图片
            }
        }

        //3.保存回报信息
        if(!CollectionUtils.isEmpty(projectRedisStorageVo.getProjectReturns())){
            for(TReturn tReturn:projectRedisStorageVo.getProjectReturns()){
                tReturn.setProjectid(projectId);
                returnMapper.insertSelective(tReturn);
            }
        }

        //4.保存标签关系
        for(Integer tagId:projectRedisStorageVo.getTagids()){
            TProjectTag tProjectTag =  new TProjectTag(null,projectId,tagId);
            projectTagMapper.insertSelective(tProjectTag);
        }

        //5.保存类型关系
        for(Integer typeId:projectRedisStorageVo.getTypeids()){
            TProjectType tProjectType =  new TProjectType(null,projectId,typeId);
            projectTypeMapper.insertSelective(tProjectType);
        }
    }
}
