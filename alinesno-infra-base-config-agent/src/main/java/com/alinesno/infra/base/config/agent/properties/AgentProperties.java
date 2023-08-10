package com.alinesno.infra.base.config.agent.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 代理配置属性类
 * 用于配置代理相关的属性
 *
 * <p>可以通过@ConfigurationProperties注解和@Configuration注解将该类作为配置类，并通过前缀"alinesno.ops.configure"绑定属性值。</p>
 *
 * <p>示例：</p>
 * <pre>
 * {@literal @}Configuration
 * {@literal @}ConfigurationProperties(prefix = "alinesno.ops.configure")
 * public class AgentProperties {
 *     // 属性和方法
 * }
 * </pre>
 *
 * <p>通过@ConfigurationProperties注解将该类与配置文件中的属性进行绑定，可以通过setter和getter方法来访问属性值。</p>
 *
 * <p>示例：</p>
 * <pre>
 * private String appCode;
 *
 * public String getAppCode() {
 *     return appCode;
 * }
 *
 * public void setAppCode(String appCode) {
 *     this.appCode = appCode;
 * }
 * </pre>
 *
 * <p>注意：在使用该类时，需要在配置文件中添加对应的属性。</p>
 *
 * <p>示例：</p>
 * <pre>
 * alinesno.ops.configure.appCode=your-app-code
 * </pre>
 *
 * <p>该类包含了代理配置的各个属性，例如文件类型、应用程序代码、身份标识、是否启用远程配置和配置主机地址。</p>
 *
 * <p>文件类型用于指定允许加载的文件扩展名数组。</p>
 * <p>应用程序代码用于标识当前应用程序的代码。</p>
 * <p>身份标识用于标识当前代理的身份。</p>
 * <p>是否启用远程配置用于指示是否启用远程配置加载。</p>
 * <p>配置主机地址用于指定远程配置文件的主机地址。</p>
 *
 * <p>通过setter和getter方法可以对这些属性进行设置和获取。</p>
 *
 * <p>示例：</p>
 * <pre>
 * public String[] getFileType() {
 *     return fileType;
 * }
 *
 * public void setFileType(String[] fileType) {
 *     this.fileType = fileType;
 * }
 * </pre>
 *
 * <p>注意：该类需要在Spring容器中进行注入才能使用。</p>
 *
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @see org.springframework.context.annotation.Configuration
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "alinesno.ops.configure")
public class AgentProperties {

    // 属性和方法
    private String appCode;
    private String identity;
    private boolean enabled = true;
    private String configHost;
    private String profile ;
    private boolean override = false ;
    private String publicKey ;
    private String jasyptKey ;

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public String getJasyptKey() {
        return jasyptKey;
    }

    public void setJasyptKey(String jasyptKey) {
        this.jasyptKey = jasyptKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * 获取应用程序代码
     *
     * @return 应用程序代码
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * 设置应用程序代码
     *
     * @param appCode 应用程序代码
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * 获取身份标识
     *
     * @return 身份标识
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * 设置身份标识
     *
     * @param identity 身份标识
     */
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    /**
     * 获取是否启用远程配置
     *
     * @return 如果启用远程配置，则返回true；否则返回false
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置是否启用远程配置
     *
     * @param enabled 是否启用远程配置
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取配置主机地址
     *
     * @return 配置主机地址
     */
    public String getConfigHost() {
        return configHost;
    }

    /**
     * 设置配置主机地址
     *
     * @param configHost 配置主机地址
     */
    public void setConfigHost(String configHost) {
        this.configHost = configHost;
    }

}
