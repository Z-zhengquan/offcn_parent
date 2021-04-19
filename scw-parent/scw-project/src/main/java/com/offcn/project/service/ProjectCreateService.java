package com.offcn.project.service;

import com.offcn.project.enums.ProjectStatusEnum;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

/**
 * @Auther: lhq
 * @Date: 2021/4/15 10:17
 * @Description:  初始化项目接口
 */
public interface ProjectCreateService {


    /**
     * 项目发起第一步  初始化项目
     * @param memberId
     */
    public void initProject(Integer memberId);

    /**
     * 保存项目
     * @param projectStatusEnum
     * @param projectRedisStorageVo
     */
    public void saveProject(ProjectStatusEnum projectStatusEnum, ProjectRedisStorageVo projectRedisStorageVo);
}
