package com.alinesno.infra.base.config.service.impl;

import com.alinesno.infra.base.config.entity.ConfigEnvEntity;
import com.alinesno.infra.base.config.service.IConfigEnvService;
import com.alinesno.infra.base.config.service.IConfigureCatalogService;
import com.alinesno.infra.base.config.service.IConfigureService;
import com.alinesno.infra.base.config.service.IInitDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化数据示例
 */
@Slf4j
@Component
public class InitDataServiceImpl implements IInitDataService {

    @Autowired
    private IConfigEnvService envService ;

    @Autowired
    private IConfigureCatalogService catalogService ;

    @Autowired
    private IConfigureService configureService ;

//        开发环境 (Development Environment, DEV) --> dev
//        这是开发人员编写和测试代码的地方，通常每个开发者都有自己的本地开发环境。
//
//        测试环境 (Testing Environment) --> test
//        有时分为单元测试环境、集成测试环境和系统测试环境，用于确保代码符合功能和性能要求。
//
//        用户验收测试环境 (User Acceptance Testing Environment, UAT) --> uat
//        用户或客户在类似生产环境的条件下测试应用，确认功能满足业务需求。
//
//        预生产环境 (Staging or Pre-production Environment) --> pre
//        也被称为“暂存环境”，用于最终测试和演示即将发布的版本，确保其在生产环境中能正常工作。
//
//        生产环境 (Production Environment) --> prod
//        正式对外提供服务的环境，应用程序在这里运行，供真实用户使用。

    @Override
    public void initEnv(long groupId) {

        long count = envService.count() ;

        List<ConfigEnvEntity> environments = new ArrayList<>();

        // 开发环境
        ConfigEnvEntity devEnv = new ConfigEnvEntity();
        devEnv.setName("开发环境");
        devEnv.setCode("dev");
        devEnv.setRemark("这是开发人员编写和测试代码的地方，通常每个开发者都有自己的本地开发环境。");
        environments.add(devEnv);

        // 测试环境
        ConfigEnvEntity testEnv = new ConfigEnvEntity();
        testEnv.setName("测试环境");
        testEnv.setCode("test");
        testEnv.setRemark("有时分为单元测试环境、集成测试环境和系统测试环境，用于确保代码符合功能和性能要求。");
        environments.add(testEnv);

        // 用户验收测试环境
        ConfigEnvEntity uatEnv = new ConfigEnvEntity();
        uatEnv.setName("用户验收测试环境");
        uatEnv.setCode("uat");
        uatEnv.setRemark("用户或客户在类似生产环境的条件下测试应用，确认功能满足业务需求。");
        environments.add(uatEnv);

        // 预生产环境
        ConfigEnvEntity preEnv = new ConfigEnvEntity();
        preEnv.setName("预生产环境");
        preEnv.setCode("pre");
        preEnv.setRemark("也被称为“暂存环境”，用于最终测试和演示即将发布的版本，确保其在生产环境中能正常工作。");
        environments.add(preEnv);

        // 生产环境
        ConfigEnvEntity prodEnv = new ConfigEnvEntity();
        prodEnv.setName("生产环境");
        prodEnv.setCode("prod");
        prodEnv.setRemark("正式对外提供服务的环境，应用程序在这里运行，供真实用户使用。");
        environments.add(prodEnv);

        if(count < environments.size()){
            envService.remove(new LambdaQueryWrapper<>()) ;
            envService.saveBatch(environments);
        }

    }

//    公共配置文件(存放公共配置)
//    中间件配置文件(存放中间件配置)
//    项目配置文件(用于项目具体配置文件)
//    其它配置文件(用于第三方配置文件)

    @Override
    public void initCatalog(long groupId) {

    }

    @Override
    public void initDemo(long groupId) {

    }

}
