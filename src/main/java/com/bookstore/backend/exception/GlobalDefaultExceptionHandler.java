package com.bookstore.backend.exception;

import com.bookstore.backend.entity.ErrorCode;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.entity.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result errorHandle(ServiceException ex, HttpServletResponse response){
        ErrorCode errorCode = ex.getErrorCode();
        return new Result.ResultBuilder(errorCode.getStatusCode(),ex.getDesc()).build();
    }
}
