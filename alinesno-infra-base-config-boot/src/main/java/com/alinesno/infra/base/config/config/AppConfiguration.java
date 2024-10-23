package com.alinesno.infra.base.config.config;

import com.alinesno.infra.base.config.service.IInitDataService;
import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import com.alinesno.infra.common.web.log.aspect.LogAspect;
import com.dtflys.forest.springboot.annotation.ForestScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置中心
 */
@EnableActable
@EnableInfraSsoApi
@MapperScan("com.alinesno.infra.base.config.mapper")
@Configuration
@ForestScan({
        "com.alinesno.infra.common.web.adapter.base.consumer"
})
@ComponentScan({
        "com.alinesno.infra.base.config",
        "com.alinesno.infra.common.extend.datasource"
})
public class AppConfiguration implements CommandLineRunner {

    @Autowired
    private IInitDataService initDataService ;

    @Bean
    public LogAspect getLogAspect(){
        return new LogAspect() ;
    }

    @Override
    public void run(String... args) throws Exception {
       initDataService.initEnv(0L);
       initDataService.initCatalog(0L);
    }
}
