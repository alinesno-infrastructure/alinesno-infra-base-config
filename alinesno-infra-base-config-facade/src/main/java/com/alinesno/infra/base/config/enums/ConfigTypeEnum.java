package com.alinesno.infra.base.config.enums;

import lombok.Getter;

@Getter
public enum ConfigTypeEnum {

    PROPERTIES("properties",1),
    YAML("yaml",2),
    YML("yml",3),
    JSON("json",4);

    private final String type;
    private final int code;

    ConfigTypeEnum(String type, int code){
        this.type = type;
        this.code = code;
    }

}
