package com.zukxu.shiro.handler;

import com.zukxu.common.result.R;
import com.zukxu.common.result.CommREnum;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author xupu
 * @Description 自定义异常处理类
 * @Date 2021-09-24 9:44
 */
@ControllerAdvice
public class MyExceptionHandler {
    /**
     * 没有授权异常处理
     *
     * @param e unauthorizedException
     * @return R
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<?> UnauthorizedExceptionHandler(UnauthorizedException e) {
        return R.fail(CommREnum.UNAUTHORIZED, null);
    }
}
