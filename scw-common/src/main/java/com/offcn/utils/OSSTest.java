package com.offcn.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Auther: lhq
 * @Date: 2021/4/14 13:50
 * @Description:
 */
public class OSSTest {

    public static void main(String[] args) throws Exception{
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tF1t6Nz7rSHLifL6MLj";
        String accessKeySecret = "9FqsHC0rMkAUzdo47Uvt7pq7kUZ59T";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = new FileInputStream("D:\\资料下载\\图片\\yjk6ml.jpg");
        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        ossClient.putObject("offcn20210414-zzq", "pic/test.jpg", inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
