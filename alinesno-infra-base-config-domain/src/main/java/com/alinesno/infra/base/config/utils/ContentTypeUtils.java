package com.alinesno.infra.base.config.utils;

import com.alinesno.infra.base.config.entity.ConfigureEntity;
import com.alinesno.infra.base.config.enums.ConfigTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ContentTypeUtils {

    /**
     * 合并多个YAML文件的内容到一个字符串中。
     *
     * @param filePaths 文件路径的列表。
     * @return 包含所有文件内容的字符串。
     * @throws IOException 如果读取文件时发生错误。
     */
    public static String mergeYamlFiles(List<ConfigureEntity> es) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        for (int i = 0 ; i < es.size() ; i ++) {
            contentBuilder.append(es.get(i).getContents());
            if(i < es.size()-1){
                contentBuilder.append("\n---\n\n");
            }
        }

        return contentBuilder.toString();
    }

    public static List<Long> convertToLongList(String config) {
        // 使用逗号作为分隔符分割字符串
        String[] splitIds = config.split(",");
        // 将字符串数组转换为Long类型的List
        return Arrays.stream(splitIds)
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    public static ConfigTypeEnum checkConfigType(String contents) {
        if (isJsonFormat(contents)) {
            return ConfigTypeEnum.JSON;
        } else if (isYamlFormat(contents)) {
            return ConfigTypeEnum.YAML;
        } else if (isPropertiesFormat(contents)) {
            return ConfigTypeEnum.PROPERTIES;
        }

        return ConfigTypeEnum.YAML;
    }

    public static boolean isPropertiesFormat(String contents) {
        Properties props = new Properties();
        try (StringReader reader = new StringReader(contents)) {
            // 加载Properties内容
            props.load(reader);
            return true; // 成功加载，是Properties格式
        } catch (Exception e) {
            // 加载失败，不是Properties格式
            return false;
        }
    }

    public static boolean isYamlFormat(String contents) {
        try {
            // 创建DumperOptions实例
            LoaderOptions options = new LoaderOptions();

            // 使用DumperOptions创建SafeConstructor实例
            SafeConstructor constructor = new SafeConstructor(options);

            // 使用SafeConstructor创建Yaml实例
            Yaml yaml = new Yaml(constructor);

            // 尝试加载YAML内容
            yaml.loadAs(contents, Object.class); // 注意：这里需要指定一个Class类型

            return true;
        } catch (YAMLException e) {
            // 解析失败，不是YAML格式
            return false;
        }
    }

    public static boolean isJsonFormat(String contents) {
        try {
            // 尝试使用Jackson解析器解析内容
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(contents);
            return true;
        } catch (IOException e) {
            // 解析失败，不是JSON格式
            return false;
        }
    }

}
