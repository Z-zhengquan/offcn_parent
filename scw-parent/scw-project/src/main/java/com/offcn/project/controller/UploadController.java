package com.offcn.project.controller;

import com.offcn.dycommons.response.AppResponse;
import com.offcn.utils.OSSTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lhq
 * @Date: 2021/4/14 14:20
 * @Description:
 */
@Api(tags = "项目模块（文件上传）")
@RestController
public class UploadController {

    @Autowired
    private OSSTemplate ossTemplate;

    @ApiOperation("文件上传")
    @PostMapping("/uploadFile")
    public AppResponse<Map<String,Object>> uploadFile(@RequestParam("file") MultipartFile[] files) {
        Map map = new HashMap();
        List<String> urlList = new ArrayList<>();
        try {
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    //1.获取文件名
                    String fileName = file.getOriginalFilename();
                    String filePath = ossTemplate.uploadFile(file.getInputStream(), fileName);
                    urlList.add(filePath);
                }
            }
            map.put("urlList",urlList);
            return AppResponse.ok(map);
        } catch (IOException e) {
            e.printStackTrace();
            AppResponse response = AppResponse.fail(null);
            response.setMessage("上传失败");
            return response;
        }
    }
}
