package com.alinesno.infra.base.config.api.provider;

import com.alinesno.infra.common.facade.response.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查 API
 * 类名: ConfiguraHealthRest
 * @author luoxiaodong
 * @version 1.0.0
 */
@RestController
@RequestMapping("/v1/api/base/config/health")
public class ConfiguraHealthController {

    /**
     * 查询指定实例的健康状态
     *
     * @param instanceId 实例ID
     * @return 健康状态
     */
    @GetMapping("/{instanceId}")
    public AjaxResult getInstanceHealthStatus(@PathVariable String instanceId) {
        // 实现查询指定实例的健康状态的逻辑

        return AjaxResult.success() ;
    }
}
