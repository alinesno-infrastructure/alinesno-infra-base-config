package com.alinesno.infra.base.config.core.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 *
 */
public class AsymmetricEncryptionTest {

    Logger log = LoggerFactory.getLogger(AsymmetricEncryptionTest.class) ;

    private AsymmetricEncryption encryption;

    String publicKeyStr = "" ;
    String privateKeyStr = "" ;

    @BeforeEach
    public void setup() throws Exception {
        encryption = new AsymmetricEncryption();
        encryption.generateKeys();

        // 生成RSA密钥对
        encryption.generateKeys();

        // 获取公钥和私钥的Base64编码字符串
        publicKeyStr = Base64.getEncoder().encodeToString(encryption.getPublicKey().getEncoded());
        privateKeyStr = Base64.getEncoder().encodeToString(encryption.getPrivateKey().getEncoded());

        System.out.println("public KeyStr = " + publicKeyStr);
        System.out.println("private KeyStr = " + privateKeyStr);
    }

    /**
     * 测试加密和解密过程。
     * @throws Exception 如果加密或解密数据时发生错误。
     */
    @Test
    public void testEncryptDecrypt() throws Exception {
        String data = "Hello, World!";
        String encryptedData = encryption.encrypt(data);
        String decryptedData = encryption.decrypt(encryptedData);

        System.out.println("encryptedData = " + encryptedData);
        System.out.println("decryptedData = " + decryptedData);

        Assertions.assertEquals(data, decryptedData, "Encryption and decryption failed.");
    }

    /**
     * 测试加载私钥的功能。
     * @throws Exception 如果加载私钥或加密解密数据时发生错误。
     */
    @Test
    public void testLoadPrivateKey() throws Exception {
        encryption.loadPrivateKey(privateKeyStr);

        String data = "Hello, World!";
        String encryptedData = encryption.encrypt(data);
        String decryptedData = encryption.decrypt(encryptedData);

        Assertions.assertEquals(data, decryptedData, "Encryption and decryption with loaded private key failed.");
    }

    /**
     * 测试加载公钥的功能。
     * @throws Exception 如果加载公钥或加密解密数据时发生错误。
     */
    @Test
    public void testLoadPublicKey() throws Exception {
        encryption.loadPublicKey(publicKeyStr);

        String data = "Hello, World!";
        String encryptedData = encryption.encrypt(data);
        String decryptedData = encryption.decrypt(encryptedData);

        Assertions.assertEquals(data, decryptedData, "Encryption and decryption with loaded public key failed.");
    }
}
