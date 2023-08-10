package com.alinesno.infra.base.config.api.provider;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.base.config.api.aop.RequestRecord;
import com.alinesno.infra.base.config.api.dto.ConfigureDto;
import com.alinesno.infra.base.config.core.tools.AesEncryptionUtils;
import com.alinesno.infra.base.config.core.tools.AsymmetricEncryption;
import com.alinesno.infra.base.config.core.tools.EnctyptionUtils;
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
     * 根据用户登录信息获取公钥信息。
     *
     * 此方法用于根据用户的登录信息获取公钥。
     *
     * @param configCode 应用代码标识。
     * @return 包含公钥的 AjaxResult 对象。
     */
    @GetMapping("/publicKey/{configCode}")
    public AjaxResult getPublicKey(@PathVariable String configCode) {

        // 根据提供的应用代码标识获取公钥
        String publicKey = configuraService.getPublicKey(configCode);

        return AjaxResult.success("操作成功" , publicKey);
    }

    /**
     * 获取指定配置的值
     *
     * @param configCode 配置ID
     * @return 配置值
     */
    @RequestRecord(desc = "请求配置信息")
    @GetMapping("/configs/{configCode}")
    public AjaxResult getConfig(@PathVariable String configCode) throws Exception {

        // 实现获取指定配置的值的逻辑
        ConfigureDto dto = configuraService.getByCode(configCode) ;

        // 配置加密
        String ENCODE_KEY = "mysecretpassword" ;
        String encryptedData = AesEncryptionUtils.encrypt(JSONObject.toJSONString(dto) , ENCODE_KEY) ;

        return AjaxResult.success("操作成功" , encryptedData) ;
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
