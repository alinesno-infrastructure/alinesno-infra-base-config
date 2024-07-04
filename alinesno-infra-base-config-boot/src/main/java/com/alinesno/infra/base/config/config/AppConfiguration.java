package com.alinesno.infra.base.config.config;

import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置中心
 */
@EnableActable
@EnableInfraSsoApi
@MapperScan("com.alinesno.infra.base.config.mapper")
@Configuration
public class AppConfiguration {

}
