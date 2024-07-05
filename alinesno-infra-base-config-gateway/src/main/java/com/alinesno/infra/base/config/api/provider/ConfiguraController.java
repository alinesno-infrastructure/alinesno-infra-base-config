package com.alinesno.infra.base.config.api.provider;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.base.config.api.dto.ConfigurationRequest;
import com.alinesno.infra.base.config.api.dto.ConfigureDto;
import com.alinesno.infra.base.config.core.tools.AesEncryptionUtils;
import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.entity.ProjectEntity;
import com.alinesno.infra.base.config.enums.ConfigTypeEnum;
import com.alinesno.infra.base.config.service.IConfigureService;
import com.alinesno.infra.base.config.service.IProjectService;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
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
public class ConfiguraController {

    @Autowired
    private IConfigureService configuraService;

    @Resource
    private IConfigureService appConfigsService;

    @Resource
    private IProjectService appsService;

    @GetMapping("/getConfig")
    public AjaxResult getConfig(ConfigurationRequest configurationRequest){
        if(ObjectUtil.isNull(configurationRequest) || ObjectUtil.isNull(configurationRequest.getIdentity())
                || ObjectUtil.isNull(configurationRequest.getOpenId())){
            return AjaxResult.error("请求参数缺失");
        }

        String openId = configurationRequest.getOpenId();
        List<ProjectEntity> appEntityList = appsService.list(new LambdaQueryWrapper<ProjectEntity>().eq(ProjectEntity::getOpenKey,openId));

        if(CollectionUtil.isEmpty(appEntityList)){
            return AjaxResult.error("不存在的应用");
        }

        ProjectEntity app = appEntityList.get(0);

        LambdaQueryWrapper<ConfigureEntity> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(ConfigureEntity::getProjectId , app.getId());
        wrapper.eq(ConfigureEntity::getIdentity, configurationRequest.getIdentity());

        List<ConfigureEntity> entityList = appConfigsService.list(wrapper);

        if(CollectionUtil.isEmpty(entityList)){
            return AjaxResult.error("无此配置或者该配置已被清理");
        }

        ConfigureEntity entity = entityList.get(0);
        if(entity.getType() != 1 && entity.getType() != 2) {
            return AjaxResult.error("数据错误，不存在的类型");
        }

        cn.hutool.json.JSONObject jsonObject = new cn.hutool.json.JSONObject();
        jsonObject.set("type", Arrays.stream(ConfigTypeEnum.values()).filter(item -> item.getCode() == entity.getType()) .toList().get(0).getType());
        jsonObject.set("content", entity.getContents());


        return AjaxResult.success("获取远程配置成功", jsonObject);
    }

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
