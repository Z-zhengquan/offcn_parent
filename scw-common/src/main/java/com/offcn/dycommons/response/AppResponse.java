package com.offcn.dycommons.response;

import com.offcn.dycommons.enums.ResponseCodeEnum;

/**
 * @Auther: lhq
 * @Date: 2021/4/13 14:23
 * @Description: 响应请求结果的封装类
 */
public class AppResponse<T> {

    private Integer code;    //返回状态码
    private String message;    //返回信息
    private T data;         //返回的数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //返回成功信息
    public static<T>  AppResponse<T> ok(T data){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        response.setData(data);
        return  response;
    }


    //返回失败信息

    public static<T>  AppResponse<T> fail(T data){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResponseCodeEnum.FAIL.getCode());
        response.setMessage(ResponseCodeEnum.FAIL.getMessage());
        response.setData(data);
        return  response;
    }


}
