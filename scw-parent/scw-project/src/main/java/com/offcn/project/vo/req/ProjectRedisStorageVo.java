package com.offcn.project.vo.req;

import com.offcn.project.pojo.TReturn;
import com.offcn.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Auther: lhq
 * @Date: 2021/4/15 10:11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRedisStorageVo extends BaseVo {

    private String projectToken;//项目的临时token
    private Integer memberid;//会员id
    private List<Integer> typeids; //项目的分类id
    private List<Integer> tagids; //项目的标签id
    private String name;//项目名称
    private String remark;//项目简介
    private Integer money;//筹资金额
    private Integer day;//筹资天数
    private String headerImage;//项目头部图片
    private List<String> detailsImage;//项目详情图片
    private List<TReturn> projectReturns;//项目回报

}
