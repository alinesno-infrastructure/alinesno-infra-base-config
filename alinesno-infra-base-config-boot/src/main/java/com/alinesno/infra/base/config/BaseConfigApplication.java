package com.alinesno.infra.base.config;

import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 集成一个Java开发示例工具
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@EnableInfraSsoApi
@SpringBootApplication
@MapperScan("com.alinesno.infra.base.config.mapper")
public class BaseConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseConfigApplication.class, args);
	}

}