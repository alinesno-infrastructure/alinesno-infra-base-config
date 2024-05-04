package com.alinesno.infra.base.config.api.provider;

import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.service.IConfigEnvService;
import com.alinesno.infra.common.facade.response.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 环境管理 API
 * 类名: ConfiguraEnvRest
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@RestController
@RequestMapping("/v1/api/base/config/env")
public class ConfiguraEnvController {

    @Autowired
    private IConfigEnvService configEnvService ;

    /**
     * 创建环境
     *
     * @param environmentData 环境数据
     * @return 操作结果
     */
    @PostMapping
    public AjaxResult createEnvironment(@RequestBody String environmentData) {
        // 实现创建环境的逻辑

        return AjaxResult.success() ;
    }

    /**
     * 删除环境
     *
     * @param environmentId 环境ID
     * @return 操作结果
     */
    @DeleteMapping("/{environmentId}")
    public AjaxResult deleteEnvironment(@PathVariable String environmentId) {
        // 实现删除环境的逻辑

        return AjaxResult.success() ;
    }

    /**
     * 查询所有环境列表
     *
     * @return 环境列表
     */
    @GetMapping
    public List<ConfigureEntity> getAllEnvironments() {
        // 实现查询所有环境列表的逻辑


        return null ;
    }
}

