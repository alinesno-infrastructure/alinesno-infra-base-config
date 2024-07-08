package com.alinesno.infra.base.config.agent.loader;

import com.alinesno.infra.base.config.agent.util.RemotePropertySourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 用于加载远程属性源的 PropertySourceLoader 实现。
 *
 * 该类提供了从远程源（如 YAML 文件、properties 文件等）加载属性的功能。
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
public class RemotePropertySourceLoader implements PropertySourceLoader {

    @Override
    public String[] getFileExtensions() {
        // 返回支持的文件扩展名，用于加载属性
        return new String[]{"yml", "yaml", "properties"};
    }

    private RemotePropertySourceUtil remotePropertySourceUtil;

    @Override
    public List load(String name, Resource resource) throws IOException {
        // 从给定的资源加载属性
        String filename = resource.getFilename();
        setUtil(RemotePropertySourceUtil.getInstance());
        PropertySource propertySource = null;
        Properties props = null;

        if (filename != null && filename.endsWith(".properties")) {
            // 从 .properties 文件加载属性
            props = remotePropertySourceUtil.loadProperties(resource.getInputStream());
        }
        if (filename != null && (filename.endsWith(".yml") || filename.endsWith(".yaml"))) {
            // 从 YAML 文件加载属性
            Map<String, Object> map = new Yaml().loadAs(resource.getInputStream(), Map.class);
            Map<String, Object> parsedMap = remotePropertySourceUtil.generateMap(map);
            props = new Properties();
            props.putAll(parsedMap);
        }

        if (props != null) {

            System.out.println("--->> " + props);
            if(props.containsKey(RemotePropertySourceUtil.CONFIG_CENTER_URL)){
                System.out.println("alinesno.configure.remote-first ===>>> " + props.get("alinesno.configure.remote-first"));
                System.out.println("alinesno.configure.enabled ===>>> " + props.get("alinesno.configure.enabled"));
                System.out.println("alinesno.configure.host ===>>> " + props.get("alinesno.configure.host"));
                System.out.println("alinesno.configure.identity ===>>> " + props.get("alinesno.configure.identity"));
                System.out.println("alinesno.configure.env ===>>> " + props.get("alinesno.configure.env"));
            }

            // 检查属性是否包含配置中心 URL，并在存在时获取配置
            if (props.containsKey(RemotePropertySourceUtil.CONFIG_CENTER_URL)
                    && props.get(RemotePropertySourceUtil.CONFIG_CENTER_URL) != null) {
                remotePropertySourceUtil.init(props);
                props = remotePropertySourceUtil.fetchConfig(props);
            }
            propertySource = new PropertiesPropertySource(name, props);
            return Collections.singletonList(propertySource);
        }
        return null;
    }

    /**
     * 如果尚未设置，则设置 RemotePropertySourceUtil 实例。
     *
     * @param util 要设置的 RemotePropertySourceUtil 实例
     */
    private void setUtil(RemotePropertySourceUtil util) {
        if (remotePropertySourceUtil == null) {
            remotePropertySourceUtil = util;
        }
    }

}
