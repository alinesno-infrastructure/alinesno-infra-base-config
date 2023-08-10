package com.alinesno.infra.base.config.agent.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import com.alinesno.infra.base.config.agent.properties.AgentConstants;
import com.alinesno.infra.base.config.agent.properties.AgentProperties;
import com.alinesno.infra.base.config.core.tools.AsymmetricEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务加载工具类对象
 * 用于加载配置文件的工具类
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class PropertyLoaderUtils {

    private static final Logger log = LoggerFactory.getLogger(PropertyLoaderUtils.class);

    private AsymmetricEncryption encryption = new AsymmetricEncryption() ;

    /**
     * 加载资源文件
     *
     * @param agentProperties 代理配置属性
     * @return 加载的配置属性
     * @throws IOException 如果发生IO异常
     */
    public static Map<String, Object> loadResource(AgentProperties agentProperties) {
        try {
            return fetchRemoteConfig(agentProperties);
        }catch (Exception e){
           log.error("message = {}" , e.getMessage());
        }

        Map<String , Object> map = new HashMap<>();
        map.put("spring.application.name", "hello-config");

        return map ;
    }

    /**
     * 获取远程配置文件
     *
     * @param agentProperties 代理配置属性
     * @return 远程配置文件的属性
     */
    private static Map<String, Object> fetchRemoteConfig(AgentProperties agentProperties) throws Exception {

        if(agentProperties.getConfigHost() == null){

            Map<String , Object> map = new HashMap<>();
            map.put("spring.application.name", "hello-config-2");

            return map ;
        }

        Map<String , Object> props = new HashMap<String , Object>();

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

            String encBody = resp.body() ;
            String decBody = descBody(encBody , agentProperties.getPublicKey()) ;

            log.debug("decBody = {}" , decBody);

            JSONObject json = new JSONObject(decBody);

            for(String key: json.keySet()){
                Object value = json.get(key) ;

                if(value != null && value.toString().startsWith(AgentConstants.PLACEHOLDER_PREFIX)){
                    if(agentProperties.getJasyptKey() != null){
                        value = JasyptEncryptionUtils.decrypt((String) value , agentProperties.getJasyptKey()) ;
                    }
                }

                log.debug("key = {} , value = {}" , key , value);

                props.put(key , value) ;
            }
        }

        return props;
    }

    /**
     * Remote Http Data dec
     * @param encBody
     * @param publicKey
     * @return
     */
    private static String descBody(String encBody, String publicKey) throws Exception {
        AsymmetricEncryption.getInstance().loadPublicKey(publicKey);
        return AsymmetricEncryption.getInstance().decrypt(encBody);
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
