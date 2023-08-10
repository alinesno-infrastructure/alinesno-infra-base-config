package com.alinesno.infra.base.config.core.tools;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 非对称加密/解密工具类，使用RSA算法进行加密和解密。
 * 支持生成RSA密钥对、加载公钥和私钥、加密和解密数据。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class AsymmetricEncryption {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private static final AsymmetricEncryption encryption = new AsymmetricEncryption();

    public static AsymmetricEncryption getInstance() {
        return encryption ;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 生成RSA密钥对。
     * @throws Exception 如果生成密钥对时发生错误。
     */
    public void generateKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    /**
     * 加载私钥。
     * @param privateKeyStr Base64编码的私钥字符串。
     * @throws Exception 如果加载私钥时发生错误。
     */
    public void loadPrivateKey(String privateKeyStr) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(keySpec);
    }

    /**
     * 加载公钥。
     * @param publicKeyStr Base64编码的公钥字符串。
     * @throws Exception 如果加载公钥时发生错误。
     */
    public void loadPublicKey(String publicKeyStr) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(keySpec);
    }

    /**
     * 使用公钥加密数据。
     * @param data 要加密的数据。
     * @return Base64编码的加密结果。
     * @throws Exception 如果加密数据时发生错误。
     */
    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 使用私钥解密数据。
     * @param encryptedData Base64编码的加密数据。
     * @return 解密后的原始数据。
     * @throws Exception 如果解密数据时发生错误。
     */
    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
