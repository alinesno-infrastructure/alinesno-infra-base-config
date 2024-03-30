package com.alinesno.infra.base.config.api.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

public class EncryptionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            if (method.isAnnotationPresent(Encryption.class)) {
                // 对请求数据进行加密处理
                // ...
            }

            if (method.isAnnotationPresent(Decryption.class)) {
                // 对请求数据进行解密处理
                // ...
            }
        }

        return true;
    }
}
