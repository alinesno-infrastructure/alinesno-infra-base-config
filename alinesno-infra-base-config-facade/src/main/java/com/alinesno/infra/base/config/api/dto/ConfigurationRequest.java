package com.alinesno.infra.base.config.api.dto;

import lombok.Data;

@Data
public class ConfigurationRequest {

    /**
     * 应用openId
     */
    private String openId;

    /**
     * 配置名称
     */
    private String identity;

}
