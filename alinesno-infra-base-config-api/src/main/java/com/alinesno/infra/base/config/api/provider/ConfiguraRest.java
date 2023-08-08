package com.alinesno.infra.base.config.api.provider;

import com.alinesno.infra.base.config.api.aop.RequestRecord;
import com.alinesno.infra.base.config.api.dto.ConfigureDto;
import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.service.IConfigureService;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 配置管理 API
 * 类名: ConfiguraRest
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@RestController
@RequestMapping("/v1/api/base/config")
public class ConfiguraRest {

    @Autowired
    private IConfigureService configuraService;

    /**
     * 获取指定配置的值
     *
     * @param configCode 配置ID
     * @return 配置值
     */
    @RequestRecord
    @GetMapping("/configs/{configCode}")
    public AjaxResult getConfig(@NotNull @PathVariable String configCode) {
        // 实现获取指定配置的值的逻辑

        ConfigureDto dto = configuraService.getByCode(configCode) ;

        return AjaxResult.success(dto) ;
    }

    /**
     * 发布或更新配置
     *
     * @param configData 配置数据
     * @return 操作结果
     */
    @RequestRecord
    @PostMapping("/configs")
    public AjaxResult publishConfig(@Validated @RequestBody ConfigureDto configData) {
        // 实现发布或更新配置的逻辑

        configuraService.publishConfig(configData) ;

        return AjaxResult.success() ;
    }

    /**
     * 删除指定配置
     *
     * @param configCode 配置ID
     * @return 操作结果
     */
    @DeleteMapping("/configs/{configId}")
    public AjaxResult deleteConfig(@NotNull @PathVariable String configCode) {

        // 实现删除指定配置的逻辑
        LambdaQueryWrapper<ConfigureEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConfigureEntity::getIdentity, configCode);

        configuraService.remove(queryWrapper);

        return AjaxResult.success() ;
    }

    /**
     * 查询指定环境下的配置列表
     *
     * @param environmentId 环境ID
     * @return 配置列表
     */
    @GetMapping("/configs/list")
    public List<ConfigureEntity> getConfigList(@RequestParam String environmentId) {
        // 实现查询指定环境下的配置列表的逻辑

        LambdaQueryWrapper<ConfigureEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConfigureEntity::getIdentity, environmentId);

        return configuraService.list(queryWrapper);
    }

}
