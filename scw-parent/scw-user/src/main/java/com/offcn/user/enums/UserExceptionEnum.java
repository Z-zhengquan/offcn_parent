package com.offcn.user.enums;

/**
 * @Auther: lhq
 * @Date: 2021/4/14 09:24
 * @Description:
 */
public enum UserExceptionEnum {

    LOGINACCT_EXIST(1, "登录账号已经存在"),
    EMAIL_EXIST(2, "邮箱已经存在"),
    LOGINACCT_LOCKED(3, "账号已经被锁定");


    private UserExceptionEnum(Integer code, String message) {
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
