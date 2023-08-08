package com.alinesno.infra.base.config.api.aop;

import com.alinesno.infra.base.config.entity.RequestRecordEntity;
import com.alinesno.infra.base.config.service.IRequestRecordService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AOP类，用于记录请求日志和统计接口调用次数
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Aspect
@Component
public class RequestRecordAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequestRecordAspect.class);

    @Autowired
    private IRequestRecordService requestRecordService ;

    private int requestCount = 0;

    @Pointcut("@annotation(com.alinesno.infra.base.config.api.aop.RequestRecord)")
    public void requestRecordPointcut() {

    }

    @Before("requestRecordPointcut() && @annotation(requestRecord)")
    public void beforeRequest(JoinPoint joinPoint , RequestRecord requestRecord) {
        requestCount++;
        String methodName = joinPoint.getSignature().getName();
        logger.info("调用接口方法：{}", methodName) ;

        // 保存用户请求记录
        RequestRecordEntity requestRecordEntity = new RequestRecordEntity();
        requestRecordEntity.setMethod(methodName);

        // 设置其他请求记录的属性
        requestRecordEntity.setCreateTime(new Date());
        requestRecordEntity.setAccountId(getAccountId());
        requestRecordEntity.setLoginName(getLoginName());
        requestRecordEntity.setAccountName(getAccountName());
        // ...

        requestRecordService.save(requestRecordEntity);
        logger.info("调用接口方法：{}", methodName);
    }

    @AfterReturning("requestRecordPointcut() && @annotation(requestRecord)")
    public void afterRequest(JoinPoint joinPoint, RequestRecord requestRecord) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("接口方法 {} 调用完成", methodName);
    }

    @AfterThrowing(value = "requestRecordPointcut() && @annotation(requestRecord)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, RequestRecord requestRecord, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("接口方法 {} 调用发生异常：{}", methodName, ex.getMessage());
    }

    private String getAccountId() {
        // 实现获取账户ID的逻辑
        // ...

        return "";
    }

    private String getLoginName() {
        // 实现获取登录名的逻辑
        // ...

        return null ;
    }

    private String getAccountName() {
        // 实现获取账户名的逻辑
        // ...

        return null ;
    }

    public int getRequestCount() {
        return requestCount;
    }
}
