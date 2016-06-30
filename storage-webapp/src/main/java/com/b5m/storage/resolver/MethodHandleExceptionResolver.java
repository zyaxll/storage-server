package com.b5m.storage.resolver;

import com.b5m.core.entity.Msg;
import com.b5m.core.entity.MsgCode;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

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

    /**
     * 异常消息拦截
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handlerMethod HandlerMethod
     * @param exception Exception
     * @return ModelAndView
     */
    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {

        if (null == handlerMethod || null == handlerMethod.getMethod()) {
            return null;
        }

        logger.error("server error", exception);

        Msg msg;
        if (exception instanceof Errors) { //数据校验失败
            msg = Msg.failed((Errors) exception);
        } else if (exception instanceof StorageException) { //自定义异常
            msg = Msg.failed(MsgCode.SERVER_HANDLE_ERROR);
        } else { //其他异常
            msg = Msg.failed(MsgCode.SERVER_INTERNAL_ERROR);
        }

        handleResponseBody(msg, handlerMethod, response);

        return super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
    }

    /**
     * 根据方法是否有ResponseBody进行特殊处理
     * @param msg 返回消息内容
     * @param handlerMethod 拦截的方法
     * @param response response
     * @return ModelAndView
     */
    private ModelAndView handleResponseBody(Msg msg, HandlerMethod handlerMethod, HttpServletResponse response) {
        Method method = handlerMethod.getMethod();
        ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        if (null != responseBody) {
            try {
                messageConverter.write(msg, MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse((response)));
                return new ModelAndView();
            } catch (IOException e) {
                logger.error("can't write error msg", e);
            }
        }
        return null;
    }

}
