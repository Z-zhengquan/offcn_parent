package com.offcn.dycommons.enums;

/**
 * @Auther: lhq
 * @Date: 2021/4/13 14:19
 * @Description:
 */

//注意：  枚举类 不能使用Lombok
public enum ResponseCodeEnum {
    SUCCESS(0,"操作成功"),
    FAIL(1,"服务器异常"),
    NOT_FOUND(404,"资源未找到"),
    NOT_AUTHED(403,"无权限，访问拒绝"),
    PARAM_INVAILD(400,"提交参数非法");

    private ResponseCodeEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

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
}
