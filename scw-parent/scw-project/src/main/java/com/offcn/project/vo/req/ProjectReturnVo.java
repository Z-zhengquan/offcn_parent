package com.offcn.project.vo.req;

import com.offcn.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: lhq
 * @Date: 2021/4/15 11:13
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReturnVo extends BaseVo {

    @ApiModelProperty("项目之前的临时token")
    private String projectToken;

    @ApiModelProperty(value = "回报类型：0-虚拟回报 1-实物回报",required = true)
    private String type;

    @ApiModelProperty(value = "支持金额",required = true,example = "0")
    private Integer supportmoney;

    @ApiModelProperty(value = "回报内容",required = true)
    private String content;

    @ApiModelProperty(value = "单笔限购",required = true,example = "0")
    private Integer signalpurchase;

    @ApiModelProperty(value = "限购数量",required = true,example = "0")
    private Integer purchase;

    @ApiModelProperty(value = "运费",required = true,example = "0")
    private Integer freight;

    @ApiModelProperty(value = "发票 0-不开发票 1-开发票",required = true)
    private String invoice;

    @ApiModelProperty(value = "回报时间，天数",required = true,example = "0")
    private Integer rtndate;

}
