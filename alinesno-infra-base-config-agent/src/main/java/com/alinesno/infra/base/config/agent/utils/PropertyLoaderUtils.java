package com.alinesno.infra.base.config.core.tools;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import com.alinesno.infra.base.config.agent.properties.AgentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 服务加载工具类对象
 * 用于加载配置文件的工具类
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class PropertyLoaderUtils {

    private static final Logger log = LoggerFactory.getLogger(PropertyLoaderUtils.class);

    /**
     * 加载资源文件
     *
     * @param resource 资源文件
     * @param agentProperties 代理配置属性
     * @return 加载的配置属性
     * @throws IOException 如果发生IO异常
     */
    public static Properties loadResource(Resource resource, AgentProperties agentProperties) throws IOException {

        String filename = resource.getFilename();
        Assert.isTrue(containsFileExtension(agentProperties.getFileType(), filename) , "文件类型不匹配");

        Properties props = new Properties();

        // 加载本地配置文件
        if (filename != null) {
            if (filename.endsWith(".properties")) {
                try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                    props.load(reader);
                }
            } else if (filename.endsWith(".yml") || filename.endsWith(".yaml")) {
                Map<String, Object> map = new Yaml().loadAs(resource.getInputStream(), Map.class);
                props.putAll(map);
            }
        }

        // 加载远程配置文件
        if(agentProperties.isEnabled()) {
            Properties remoteProp = fetchRemoteConfig(agentProperties);
            props.putAll(remoteProp);
        }

        return props;
    }

    /**
     * 获取远程配置文件
     *
     * @param agentProperties 代理配置属性
     * @return 远程配置文件的属性
     */
    private static Properties fetchRemoteConfig(AgentProperties agentProperties) {

        Properties props = new Properties();

        String hostPath = agentProperties.getConfigHost();
        String fetchPath = (hostPath.endsWith("/")? hostPath : hostPath+"/") + "v1/api/base/config";

        Map<String, Object> params = new HashMap<>();
        params.put("appCode", agentProperties.getAppCode());
        params.put("identity", agentProperties.getIdentity());

        HttpResponse resp = HttpRequest.post(fetchPath)
                .form(params)
                .execute();

        if(resp.getStatus() != 200){
            log.debug("error");
        }else{
            JSONObject jsonObject = new JSONObject(resp.body());
            jsonObject.forEach((key, value) -> props.put(key , value.toString()));
        }

        return props;
    }

    /**
     * 检查文件名是否包含指定的文件扩展名
     *
     * @param fileType 文件扩展名数组
     * @param filename 文件名
     * @return 如果文件名包含指定的文件扩展名，则返回true；否则返回false
     */
    private static boolean containsFileExtension(String[] fileType, String filename) {
        for (String extension : fileType) {
            if (filename.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

}
