package com.alinesno.infra.base.config.api.dto;

import com.alinesno.infra.common.facade.base.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigureDto extends BaseDto {

    // 环境
    private String env;

    // 名称
    private String name;

    // 类型
    private int type;

    // 内容
    private String contents;

    // 标识
    private String identity;

    // 备注
    private String remarks;

    // 版本
    private String version;

    // 是否选中
    private boolean selected ;

}
