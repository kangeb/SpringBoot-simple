package com.example.simple.core.Exception;

import com.example.simple.core.response.Response;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;


/** 
 * Created by gaozhenbo on 17-12-24. 
 * 利用 @ControllerAdvice + @ExceptionHandler 组合处理Controller层RuntimeException异常 
 */  
  
@ControllerAdvice  
@ResponseBody  
public class GlobalExceptionAspect  {  
  
    private static final Logger log = Logger.getLogger(GlobalExceptionAspect.class);  
    //客户端运行时异常 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomException.class)  
    public Response runtimeCustomExceptionHandler(CustomException ex) {
    	log.error("用户异常...", ex); 
        return new Response().failure(ex.getMsg(),ex.getCode()); 
    }
  //客户端运行时异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)  
    public Response runtimeBusinessExceptionHandler(BusinessException ex) { 
    	log.error("商业异常...", ex); 
        return new Response().failure(ex.getResultCode().toString()); 
    }
    /* 
    *  400-Bad Request 
    */  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler(HttpMessageNotReadableException.class)  
    public Response handleHttpMessageNotReadableException(  
            HttpMessageNotReadableException e) {  
        log.error("无法读取JSON...", e);  
        return new Response().failure("无法读取JSON",400);  
    }  
  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler(MethodArgumentNotValidException.class)  
    public Response handleValidationException(MethodArgumentNotValidException e)  
    {  
        log.error("参数验证异常...", e);  
        return new Response().failure("参数验证异常",400);  
    }  
  
    /** 
     * 404-NOT_FOUND 
     * @param e 
     * @return 
     */  
    @ResponseStatus(HttpStatus.NOT_FOUND)  
    @ExceptionHandler(NoHandlerFoundException.class)  
    public Response handlerNotFoundException(NoHandlerFoundException e)  
    {  
        log.error("请求的资源不可用",e);  
        return new Response().failure("请求的资源不可用",404);  
    }  
  
    /* 
    * 405 - method Not allowed 
    * HttpRequestMethodNotSupportedException 是ServletException 的子类，需要Servlet API 支持 
    * 
    */  
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)  
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){  
        log.error("不合法的请求方法...",e);  
        return new Response().failure("不合法的请求方法",405);  
    }  
  
    /** 
     * 415-Unsupported Media Type.HttpMediaTypeNotSupportedException是ServletException的子类，需要Serlet API支持 
     */  
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)  
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })  
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {  
        log.error("内容类型不支持...", e);  
        return new Response().failure("内容类型不支持",415);  
    }  
  
   /* *//** 
     * 500 - Internal Server Error 
     *//* 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
    @ExceptionHandler(TokenException.class) 
    public Response handleTokenException(Exception e) { 
        log.error("令牌无效...", e); 
        return new Response().failure("令牌无效"); 
    }*/  
  
    /** 
     * 500 - Internal Server Error 
     */  
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
    @ExceptionHandler(Exception.class)  
    public Response handleException(Exception e) {  
        log.error("内部服务错误...", e);  
        return new Response().failure("内部服务错误",500);  
    }  
  
} 
