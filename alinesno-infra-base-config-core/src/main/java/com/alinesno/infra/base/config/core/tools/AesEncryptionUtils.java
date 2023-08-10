package com.alinesno.infra.base.config.core.tools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES 加密工具类
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class AesEncryptionUtils {
    private static final String AES_ALGORITHM = "AES";

    /**
     * 生成随机的AES密钥。
     *
     * @return Base64编码的随机AES密钥。
     * @throws NoSuchAlgorithmException 如果生成密钥时发生错误。
     */
    public static String generateRandomKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(256); // 设置密钥长度为256位
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 使用AES算法对数据进行加密。
     *
     * @param data     要加密的数据。
     * @param password 加密密码。
     * @return Base64编码的加密结果。
     * @throws Exception 如果加密数据时发生错误。
     */
    public static String encrypt(String data, String password) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 使用AES算法对数据进行解密。
     *
     * @param encryptedData Base64编码的加密数据。
     * @param password      解密密码。
     * @return 解密后的原始数据。
     * @throws Exception 如果解密数据时发生错误。
     */
    public static String decrypt(String encryptedData, String password) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
