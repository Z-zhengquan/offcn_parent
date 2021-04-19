package com.offcn.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import lombok.ToString;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: lhq
 * @Date: 2021/4/14 14:04
 * @Description:
 */
@Data
@ToString
public class OSSTemplate {

    private String endpoint;    //节点域名
    private String bucketDomain;      //offcn20210414.oss-cn-beijing.aliyuncs.com
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public String uploadFile(InputStream inputStream, String fileName) {
        //1.生成一个时间的目录    2021-04-14
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String folderName = sdf.format(new Date());
        //2.对文件名重新命名
        fileName = UUID.randomUUID().toString().replace("-", "") + "_" + fileName;
        //3.完成上传
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, "pic/"+folderName+"/"+ fileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();

        //https://offcn20210414.oss-cn-beijing.aliyuncs.com/pic/test.jpg
        return "https://"+bucketDomain+"/pic/"+folderName+"/"+fileName;

    }


}
