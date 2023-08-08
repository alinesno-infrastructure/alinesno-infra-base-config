package com.alinesno.infra.base.config.api.dto;

import com.alinesno.infra.common.facade.base.BaseDto;

public class ConfigureDto extends BaseDto {

    // 环境
    private String env;

    // 名称
    private String name;

    // 类型
    private String type;

    // 内容
    private String contents;

    // 标识
    private String identity;

    // 备注
    private String remarks;

    // 版本
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
