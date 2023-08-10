package com.alinesno.infra.base.config.agent;

import com.alinesno.infra.base.config.agent.process.PropertiesLoader;
import com.alinesno.infra.base.config.agent.process.YamlLoader;
import com.alinesno.infra.base.config.agent.properties.AgentConstants;
import com.alinesno.infra.base.config.agent.properties.AgentProperties;
import com.alinesno.infra.base.config.agent.utils.PropertyLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.*;

/**
 * 分布式配置中心服务
 * 用于加载远程配置中心的属性并创建对应的 PropertySource
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class InfraPropertySourceLoader implements PropertySourceLoader {

    private static final Logger log = LoggerFactory.getLogger(InfraPropertySourceLoader.class);

    /**
     * 获取支持的文件扩展名
     *
     * @return 文件扩展名数组
     */
    @Override
    public String[] getFileExtensions() {
        return AgentConstants.fileType;
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
        // 创建属性映射表
        Map<String, Map<String, Object>> propertiesMap = new HashMap<>();
        int propertisLength = 0 ;
        String namefile = resource.getFilename() ;

        // 加载 getProperties 文件
        assert namefile != null;

        if(namefile.endsWith("properties")){
            List<Map<String, ?>> properties = loadProperties(resource);
            propertisLength += properties.size() ;
            if (!properties.isEmpty()) {
                for (int i = 0; i < properties.size(); i++) {
                    String documentNumber = (properties.size() != 1) ? " (document_prop #" + i + ")" : "";
                    Map<String, Object> map = new HashMap<>(Collections.unmodifiableMap(properties.get(i)));
                    propertiesMap.put(documentNumber, map);
                }
            }
        }

        // 加载 YAML 文件
        if(namefile.endsWith("yml") || name.endsWith("yaml")){
            List<Map<String, Object>> loaded = new YamlLoader(resource).load();
            propertisLength += loaded.size() ;
            if (!loaded.isEmpty()) {
                // 添加本地 YAML 文件到属性映射表
                for (int i = 0; i < loaded.size(); i++) {
                    String documentNumber = (loaded.size() != 1) ? " (document_yaml #" + i + ")" : "";
                    Map<String, Object> map = new HashMap<>(Collections.unmodifiableMap(loaded.get(i)));
                    propertiesMap.put(documentNumber, map);
                }
            }
        }

        // 创建属性源列表
        List<PropertySource<?>> propertySources = new ArrayList<>(propertisLength) ;

        // 初始化 AgentProperties
        AgentProperties agentProperties = buildAgentProperties(propertiesMap);
        log.debug("agent Properties = {}", agentProperties.toString());

        if (agentProperties.isEnabled()) {
            // 加载自定义属性
            Map<String, Object> customProperties = PropertyLoaderUtils.loadResource(agentProperties);
            overrideKey(propertiesMap , customProperties , agentProperties) ;

            // 创建自定义属性源
            PropertySource<?> customPropertySource = new OriginTrackedMapPropertySource("customPropertySource", customProperties);
            propertySources.add(customPropertySource);
        }

        // 将属性映射表中的属性创建为属性源
        for (String key : propertiesMap.keySet()) {
            propertySources.add(new OriginTrackedMapPropertySource(name + key, propertiesMap.get(key), true));
        }

        return propertySources;
    }

    /**
     * 覆盖项目配置信息和管理配置信息
     * @param propertiesMap
     * @param customProperties
     * @param agentProperties
     */
    private void overrideKey(Map<String, Map<String, Object>> propertiesMap, Map<String, Object> customProperties, AgentProperties agentProperties) {
        if (agentProperties.isOverride()) {
            // 覆盖属性映射表中的属性
            for (String key : propertiesMap.keySet()) {
                Map<String, Object> keyMaps = propertiesMap.get(key);
                for(String k : keyMaps.keySet()){
                    for (String ck : customProperties.keySet()) {
                        if(k.equals(ck)){
                            keyMaps.put(ck, customProperties.get(ck));
                            customProperties.remove(ck) ;
                        }
                    }
                }
                propertiesMap.put(key, keyMaps);
            }
        }
    }

    /**
     * 根据属性映射表构建 AgentProperties 对象
     *
     * @param propertiesMap 属性映射表
     * @return AgentProperties 对象
     */
    private AgentProperties buildAgentProperties(Map<String, Map<String, Object>> propertiesMap) {
        AgentProperties agentProperties = new AgentProperties();

        for (String key : propertiesMap.keySet()) {

            for (String k : propertiesMap.get(key).keySet()) {
                String value = propertiesMap.get(key).get(k) + "";

                System.out.println("--->>>> value = " + value);

                switch (k) {
                    case AgentConstants.CONFIGURE_APPCODE -> agentProperties.setAppCode(value);
                    case AgentConstants.CONFIGURE_COVERED -> agentProperties.setOverride(Boolean.parseBoolean(value));
                    case AgentConstants.CONFIGURE_IDENTITY -> agentProperties.setIdentity(value);
                    case AgentConstants.CONFIGURE_ENABLED -> agentProperties.setEnabled(Boolean.parseBoolean(value));
                    case AgentConstants.CONFIGURE_CONFIG_HOST -> agentProperties.setConfigHost(value);
                    case AgentConstants.CONFIGURE_PUBLIC_KEY -> agentProperties.setPublicKey(value);
                    case AgentConstants.CONFIGURE_JASYPT_KEY -> agentProperties.setJasyptKey(value);
                }
            }
        }

        return agentProperties;
    }

    private List<Map<String, ?>> loadProperties(Resource resource) throws IOException {
        String filename = resource.getFilename();
        List<Map<String, ?>> result = new ArrayList<>();
        List<PropertiesLoader.Document> documents = new PropertiesLoader(resource).load();
        documents.forEach((document) -> result.add(document.asMap()));
        return result;
    }
}
