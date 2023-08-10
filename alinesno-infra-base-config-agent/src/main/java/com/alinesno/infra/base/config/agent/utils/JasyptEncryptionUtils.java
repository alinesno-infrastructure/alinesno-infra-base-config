package com.alinesno.infra.base.config.agent.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Jasypt加密工具类，用于解密jasypt-spring-boot加密的信息。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class JasyptEncryptionUtils {

    /**
     * 解密jasypt-spring-boot加密的信息。
     *
     * @param encryptedValue 加密的值。
     * @param password       加密密码。
     * @return 解密后的原始值。
     */
    public static String decrypt(String encryptedValue, String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password); // 设置加密密码
        return encryptor.decrypt(encryptedValue);
    }
}
