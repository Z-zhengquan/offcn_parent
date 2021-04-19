package com.offcn.user.exception;

import com.offcn.user.enums.UserExceptionEnum;

/**
 * @Auther: lhq
 * @Date: 2021/4/14 09:26
 * @Description:
 */
public class UserException extends RuntimeException{

    public UserException(UserExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
    }

}
