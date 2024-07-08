package com.alinesno.infra.base.config.enums;

import lombok.Getter;

@Getter
public enum ConfigTypeEnum {

    PROPERTIES("properties",1),
    YAML("yaml",2),
    JSON("json",4);

    private final String type;
    private final int code;

    ConfigTypeEnum(String type, int code){
        this.type = type;
        this.code = code;
    }

    /**
     * 根据code获取type
     *
     * @param code 枚举中的code值
     * @return 与code相对应的type，如果没有找到匹配项则返回null
     */
    public static String getTypeByCode(int code) {
        for (ConfigTypeEnum configType : ConfigTypeEnum.values()) {
            if (configType.getCode() == code) {
                return configType.getType();
            }
        }
        return null;
    }
}
