package com.dliyun.platform.web.controller;

import com.alibaba.fastjson.JSON;
import com.dliyun.platform.common.ServletContext;
import com.dliyun.platform.common.exception.NoLoginException;
import com.dliyun.platform.common.exception.PermissionDeniedException;
import com.dliyun.platform.web.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import java.util.Objects;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/7 17:38
 */
@Slf4j
@Controller
@ControllerAdvice
public class FundaErrorController implements ErrorController {


    @Override
    public String getErrorPath() {
        return "/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HandlerMethod handlerMethod) {
        log.error("system error", ex);
        return returnString(handlerMethod, "系统忙，请稍后再试", "error/500");

    }

    @ExceptionHandler(NoLoginException.class)
    public String bindExceptionHnadler(NoLoginException exp, HandlerMethod handlerMethod) {
        return returnString(handlerMethod, "您还没有登录", "redirect:" + exp.getLoginUrl());
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public String permissionDeniedExceptionHnadler(PermissionDeniedException exp, HandlerMethod handlerMethod) {
        return returnString(handlerMethod, "你没有权限访问此页面", "error/403");
    }

    /**
     * 校验错误拦截处理
     *
     * @param exp           错误信息集合
     * @param handlerMethod 处理方法类型
     * @return 错误信息
     */
    @ExceptionHandler(BindException.class)
    public String bindExceptionHnadler(BindException exp, HandlerMethod handlerMethod) {

        BindingResult result = exp.getBindingResult();
        String message = "请填写正确信息";
        if (result.hasErrors()) {
            message = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
        }

        return returnString(handlerMethod, message, "error/500");
    }


    private String returnString(HandlerMethod handlerMethod, String message, String templatePath) {
        boolean isJsonRequest = false;
        ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (responseBody != null) {
            isJsonRequest = true;
        }

        ServletContext.getRequest().setAttribute("errorInfo", isJsonRequest ? JSON.toJSONString(AjaxResult.instance(message)) : message);

        return isJsonRequest ? "error/ajax_error" : templatePath;
    }

}
