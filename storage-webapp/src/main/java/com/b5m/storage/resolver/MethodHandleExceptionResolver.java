package com.b5m.storage.resolver;

import com.b5m.storage.exception.StorageException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用于统一处理Controller层异常处理
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-24
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-24       Leo.li          1.0             TODO
 */
@Order(-1)
public class MethodHandleExceptionResolver extends ExceptionHandlerExceptionResolver {

    @Autowired
    private HttpMessageConverter<Object> messageConverter;

    private Logger logger = LogManager.getLogger(MethodHandleExceptionResolver.class);

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {

        if (null == handlerMethod || null == handlerMethod.getMethod()) {
            return null;
        }

        if (exception instanceof Errors) { //TODO 数据校验失败
            Map<String, Object> returnValue = handleReturnValue(exception);
            if (null != returnValue) {
                logger.error("params validator error", exception);
                return handleResponseBody(returnValue, handlerMethod, response);
            }
        } else if (exception instanceof StorageException) { //TODO 自定义异常
            logger.error("project error", exception);
        } else { //TODO 其他异常
            logger.error("project other error", exception);
        }

        return super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
    }

    /**
     * 当异常类型为校验错误时, 对返回结果进行处理.
     * @param exception 异常类型
     * @return 返回消息
     */
    private Map<String, Object> handleReturnValue(Exception exception) {
        Map<String, Object> returnValue = null;
        Errors error = ((Errors) exception);
        if (error.hasErrors()) {
            returnValue = new HashMap<>();
            returnValue.put("code", 200);
            returnValue.put("msg", "params error");
            List<ObjectError> errors = error.getAllErrors();
            List<String> el = new ArrayList<>(errors.size());
            for (ObjectError e : errors) {
                el.add(e.getDefaultMessage());
            }
            returnValue.put("error", el);
        }
        return returnValue;
    }

    /**
     * 根据方法是否有ResponseBody进行特殊处理
     * @param returnValue
     * @param handlerMethod
     * @param response
     * @return
     */
    private ModelAndView handleResponseBody(Map<String, Object> returnValue, HandlerMethod handlerMethod, HttpServletResponse response) {
        Method method = handlerMethod.getMethod();
        ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        if (null != responseBody) {
            try {
                messageConverter.write(returnValue, MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse((response)));
                return new ModelAndView();
            } catch (IOException e) {
                logger.error("can't write error msg", e);
            }
        }
        return null;
    }

}
