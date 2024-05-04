package com.alinesno.infra.base.config.agent.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 远程属性源工具类，用于处理远程属性源的加载和替换操作。
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
public class RemotePropertySourceUtil {


    private static final String PLACEHOLDER_PREFIX = "${";
    private static final String PLACEHOLDER_SUFFIX = "}";
    private static final String REPLACE_STR = "${";


    public final static String CONFIG_APP_OPEN_ID = "alinesno.config.project.open-id";
    public final static String CONFIG_IDENTITY = "alinesno.config.identity";
    public final static String CONFIG_ENABLED = "alinesno.config.enabled";
    public final static String CONFIG_CENTER_URL = "alinesno.config.center.url";
    public final static String CONFIG_CENTER_GATEWAY_TOKEN_NAME = "alinesno.config.center.gate-token-name";
    public final static String CONFIG_CENTER_GATEWAY_TOKEN_VALUE = "alinesno.config.center.gate-token-value";
    public final static String CONFIG_CENTER_REMOTE_FIRST = "alinesno.config.remote-first";

    private String appOpenId;
    private String configIdentity;
    private String centerUrl;
    private String gatewayTokenName;
    private String gatewayTokenValue;

    private boolean isEnable;
    private boolean remoteFirst;

    private Properties properties;

    @lombok.Getter
    private static RemotePropertySourceUtil instance = new RemotePropertySourceUtil();

    public void init(Properties localProperties) {

        Object enable = localProperties.get(CONFIG_ENABLED);
        Object remoteFirst = localProperties.get(CONFIG_CENTER_REMOTE_FIRST);

        if(enable == null) {
            this.isEnable = false;
            return;
        }
        // 处理 yaml 和 properties 会把true读成不同类型问题
        if (enable instanceof Boolean) {
            this.isEnable = Optional.of((Boolean) enable).orElse(false);
        } else {
            this.isEnable = Optional.of(Boolean.parseBoolean(localProperties.getProperty(CONFIG_ENABLED))).orElse(false);
        }

        if (remoteFirst == null) {
            this.remoteFirst = false;
        } else if (remoteFirst instanceof Boolean){
            this.remoteFirst = Optional.of((Boolean) remoteFirst).orElse(false);
        } else {
            this.remoteFirst = Optional.of(Boolean.parseBoolean(localProperties.getProperty(CONFIG_CENTER_REMOTE_FIRST))).orElse(false);
        }


        if (!this.isEnable) {
            return;
        }

        this.appOpenId = localProperties.getProperty(CONFIG_APP_OPEN_ID);
        this.configIdentity = localProperties.getProperty(CONFIG_IDENTITY);
        this.centerUrl = localProperties.getProperty(CONFIG_CENTER_URL);
        this.gatewayTokenName = localProperties.getProperty(CONFIG_CENTER_GATEWAY_TOKEN_NAME);
        this.gatewayTokenValue = localProperties.getProperty(CONFIG_CENTER_GATEWAY_TOKEN_VALUE);
    }

    public Properties fetchConfig(Properties localProperties) {
        if (!isEnable) {
            return localProperties;
        }
        Properties remoteProperties = this.getRemoterConfig();

        Properties rProperties = null;

        //考虑是否远程配置优先
        if(remoteFirst){
           localProperties.putAll(remoteProperties);
           rProperties = localProperties;
        } else {
            assert remoteProperties != null;
            remoteProperties.putAll(localProperties);
           rProperties = remoteProperties;
        }
        this.properties = rProperties;

        // 替换 ${}
        return replaceVariable(rProperties);

    }

    private Properties getRemoterConfig() {
        if (centerUrl == null || appOpenId == null || configIdentity == null) {
            return null;
        }
        String errorMsg = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime = new Date().getTime();

        if (this.centerUrl.endsWith("/")) {
            centerUrl = centerUrl.substring(0, centerUrl.length() - 1);
        }
        String url = this.centerUrl + "/v1/api/base/config/getConfig" ;
        Map<String, Object> params = new HashMap<>();
        params.put("openId", appOpenId);
        params.put("identity", configIdentity);

        System.out.println("获取远程配置: " + configIdentity);
        HttpResponse response = HttpRequest.get(url)
                .header(this.gatewayTokenName, this.gatewayTokenValue)
                .form(params)
                .execute();

        if (response.getStatus() >= 400) {
            errorMsg = "获取远程配置出错: 请求出错";
        }

        JSONObject jsonObject = new JSONObject(response.body());
        int code = (int) jsonObject.get("code");
        if (code >= 400) {
            errorMsg = "获取远程配置出错： " + (String) jsonObject.get("msg");
        }

        JSONObject configInfo = jsonObject.getJSONObject("data");
        String type = configInfo.getStr("type");
        String content = configInfo.getStr("content");
        Properties props = null;
        try {
            if ("properties".equals(type)) {
                props = loadProperties(new ByteArrayInputStream(content.getBytes()));
            }
            if ("yaml".equals(type)) {
                Map<String, Object> map = new Yaml().loadAs(new ByteArrayInputStream(content.getBytes()), Map.class);
                Map<String, Object> parsedMap = generateMap(map);
                props = new Properties();
                props.putAll(parsedMap);
            }
        } catch (Exception e) {
            errorMsg = e.getMessage();
            System.out.println("解析配置出错: " + e.getMessage());
        }
        if (StrUtil.isNotEmpty(errorMsg)) {
            System.out.println(errorMsg);
            return null;
        } else {
            long endTime = new Date().getTime();
            double currentTimeInSeconds = (endTime - startTime) / 1000.0; // 将毫秒数转换为秒数
            DecimalFormat df = new DecimalFormat("0.00"); // 创建一个保留两位小数的 DecimalFormat 对象
            String formattedTime = df.format(currentTimeInSeconds);

            System.out.println("获取远程配置成功, 所用时间:  " + formattedTime + " 秒");
        }
        return props;
    }


    public Properties loadProperties(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        return properties;
    }

    public Map<String, Object> generateMap(Map<String, Object> map) {
        return innerGenerate("", map);
    }

    private Map<String, Object> innerGenerate(String pattern, Map<String, Object> map) {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                resultMap.putAll(innerGenerate(pattern + "." + key, (Map<String, Object>) value));
            } else if(value instanceof List){
                List<Object> rList = new ArrayList();
                List list = (List)value;
                for(Object o : list){
                    if(o instanceof Map){
                        Map<String, Object> itemResult = generateMap((Map<String, Object>)o);
                        rList.add(itemResult);
                    } else {
                        rList.add(o);
                    }
                }
                resultMap.put((pattern + "." + key).substring(1), rList);
            } else {
                if(value == null){
                    continue;
                }
                resultMap.put((pattern + "." + key).substring(1), value);
            }
        }
        return resultMap;
    }

    private Properties replaceVariable(Properties properties) {
        Set<Map.Entry<Object, Object>> set = properties.entrySet();

        for (Map.Entry<Object, Object> entry : set) {
            Object key = entry.getKey();
            Object oValue = entry.getValue();
            if (oValue instanceof String && ((String) oValue).contains(PLACEHOLDER_PREFIX)) {
                String realValue = innerReplace((String) oValue);
                properties.setProperty((String) key, realValue);
                this.properties.setProperty((String) key, realValue);
            }
        }

        return properties;
    }

    private String innerReplace(String value) {
        if (!value.contains(PLACEHOLDER_PREFIX)) {
            return value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder checkStr = new StringBuilder(value);

        int index;
        while ((index = checkStr.indexOf(PLACEHOLDER_PREFIX)) != -1) {
            stringBuilder.append(checkStr.substring(0, index));
            checkStr.delete(0, index);

            int nextPrefix = checkStr.indexOf(PLACEHOLDER_PREFIX, 2);
            int nextSuffix = checkStr.indexOf(PLACEHOLDER_SUFFIX);

            if (nextSuffix == -1) {
                stringBuilder.append(checkStr);
                System.out.print("警告: 配置中存在不配对的 \"${\" 和 \"}\" ，请确认你的配置");
                return stringBuilder.toString();
            }

            if(nextPrefix != -1 && nextPrefix < nextSuffix) {
                // 迭代 , 返回的可能还有 ${ ,所以最后面还会进行一次迭代
                String nextReplace = checkStr.substring(2, nextSuffix + 1);
                checkStr = checkStr.delete(0, nextSuffix + 1);
                String realString = innerReplace(nextReplace);
                stringBuilder.append(PLACEHOLDER_PREFIX);
                stringBuilder.append(realString);
            } else {
                String nextReplace = checkStr.substring(2, nextSuffix);
                checkStr.delete(0, nextSuffix + 1);
                String realString = doReplace(nextReplace);
                stringBuilder.append(realString);
            }
        }
        stringBuilder.append(checkStr);
        return innerReplace(stringBuilder.toString());
    }

    private String doReplace(String replaceStr) {

        replaceStr = replaceStr.trim();
        String rString = null;
        String defaultString = "";
        boolean isHasDefault = replaceStr.contains(":");

        if (isHasDefault) {
            rString = replaceStr.split(":")[0].trim();
            if(!replaceStr.endsWith(":")){
                 defaultString = replaceStr.split(":")[1].trim();
            }
        } else {
            rString = replaceStr;
        }

        String realString = System.getenv(rString);
        if (StrUtil.isEmpty(realString)) {
            realString = System.getProperty(rString);
        }
        if (StrUtil.isEmpty(realString)) {
            Object realObject = properties.get(rString);
            if (ObjectUtil.isNotEmpty(realObject)) {
                realString = realObject.toString();
                if (realString.contains(PLACEHOLDER_PREFIX)) {
                    realString = innerReplace(realString);
                }
            }
        }
        if (isHasDefault && StrUtil.isEmpty(realString)) {
            realString = defaultString;
        }
        return realString;
    }
}
