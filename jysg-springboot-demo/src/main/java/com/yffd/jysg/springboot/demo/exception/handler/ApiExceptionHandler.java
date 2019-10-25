package com.yffd.jysg.springboot.demo.exception.handler;

import com.yffd.jysg.springboot.demo.exception.LogicException;
import com.yffd.jysg.springboot.demo.exception.result.ApiResult;
import com.yffd.jysg.springboot.demo.exception.result.ApiResultBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ApiExceptionHandler {
    /**
     * 默认统一异常处理方法
     * @param e 默认Exception异常对象
     * @return
     */
    @ExceptionHandler
    @ResponseStatus
    public ApiResult runtimeExceptionHandler(Exception e) {
        return ApiResultBuilder.errorResult(e.getMessage(),e);
    }

    /**
     * 处理业务逻辑异常
     * @param e 业务逻辑异常对象实例
     * @return 逻辑异常消息内容
     */
    @ExceptionHandler(LogicException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResult logicException(LogicException e) {
        return ApiResultBuilder.errorResult(e.getMessage(),e);
    }

}
