package com.alinesno.infra.base.config.agent.properties;

/**
 * 代理配置常量接口
 * 定义了与代理配置相关的常量
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public interface AgentConstants {

    // 常量定义...

    String[] fileType = {
            "yml",
            "yaml",
            "properties"
    };

    String CONFIGURE_APPCODE = "alinesno.ops.configure.app-code" ;
    String  CONFIGURE_IDENTITY = "alinesno.ops.configure.identity" ;
    String CONFIGURE_ENABLED = "alinesno.ops.configure.enabled" ;
    String CONFIGURE_COVERED = "alinesno.ops.configure.covered" ;
    String CONFIGURE_CONFIG_HOST = "alinesno.ops.configure.config-host" ;
    String CONFIGURE_PUBLIC_KEY = "alinesno.ops.configure.public-key" ;
    String CONFIGURE_JASYPT_KEY = "alinesno.ops.configure.jasypt-key" ;

    String XML_FILE_EXTENSION = ".xml" ;

    String PLACEHOLDER_PREFIX = "${";

    String PLACEHOLDER_SUFFIX = "}";

}
