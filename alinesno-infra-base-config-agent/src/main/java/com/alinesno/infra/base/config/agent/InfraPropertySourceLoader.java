package com.alinesno.infra.base.config.agent;

import com.alinesno.infra.base.config.agent.properties.AgentProperties;
import com.alinesno.infra.base.config.agent.tools.PropertyLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * TODO 还没有研究透，后面进一步优化
 * 分布式配置中心服务
 * 用于加载远程配置中心的属性并创建对应的 PropertySource
 *
 * @Author luoxiaodong
 * @Version 1.0.0
 */
public class InfraPropertySourceLoader implements PropertySourceLoader {

    private static final Logger log = LoggerFactory.getLogger(InfraPropertySourceLoader.class);

    @Autowired
    private AgentProperties agentProperties;

    /**
     * 获取支持的文件扩展名
     *
     * @return 文件扩展名数组
     */
    @Override
    public String[] getFileExtensions() {
        return agentProperties.getFileType();
    }

    /**
     * 加载属性文件并创建 PropertySource
     *
     * @param name     属性文件名称
     * @param resource 属性文件资源
     * @return PropertySource 列表
     * @throws IOException 加载过程中的 IO 异常
     */
    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {

        log.debug("Config name = {}, resource = {}", name, resource.getFilename());

        Properties props = PropertyLoaderUtils.loadResource(resource, agentProperties);

        PropertySource<?> propertySource = new PropertiesPropertySource(name, props);
        return Collections.singletonList(propertySource);
    }
}
