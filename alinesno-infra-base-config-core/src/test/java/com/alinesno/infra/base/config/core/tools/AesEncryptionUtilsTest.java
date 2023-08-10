package com.alinesno.infra.base.config.core.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

public class AesEncryptionUtilsTest {
    private static final String PASSWORD = "mysecretpassword";

    @Test
    public void testEncryptAndDecrypt() throws Exception {

        String originalData = "{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"id\":1689634742128873473,\"fieldProp\":null,\"addTime\":null,\"deleteTime\":null,\"hasStatus\":0,\"updateTime\":null,\"operatorId\":null,\"lastUpdateOperatorId\":null,\"env\":\"dev\",\"name\":\"example\",\"type\":\"config\",\"contents\":\"nNO4z8etLUZY0rkk5WF+4JRK/pHRx/uzEHZ9a07PHcpDXR/+lDwRlI55PtKPXmQM8Wgd0N1klGsigZ7jhoDEfPqYSUVyntorOT95DvZrNhT4ymnZpmmTEk7MpKT1LyNzlmIr6S31hJnJlnU85cV/yvuQjfRrkqPTAAUi8FceDup23TIrCL6dLT1R7nuy2a6I6dUHNWLnC8n98q+20ZscupTjoLTFMRcGiVpiFlfIzK3R4Qws5GAVzbFHtUeSijrRHUNFhDPud+Nnm3yv+F/52jX31iOSjvLJsAHo/mxyclcC/mz4Vaep91X/VIOaHqydYaYl8jKrKtfBN/1yXd68HMI+NlzxLN7zrUG02LFt9/srucONpNoibEC7YcHS/KOiaxNuqAtphQMnNO7cz9vFXCSkbBbH3Odv6i7rU6rSfGM7GmG9o15OLV2+94za7EmIUxmblL67uae/TT+vJdH9zscfSOl42NBPgoZkuMSv+N8dk+a0O4Juq62RvEzXKWguMo30KIMaEDu6CiW0TukLIAwBp8xTxWv7S0LU+QBlFfNxcQF6zIGutCaxBidj1GZbHrj7RPD4RV4V6OC+Q1taBuvFtSmnvS/CXpYSHhuXJn7zukd2Wg5kJoFeYuvV9Fzn8oi1FnZvpjVzro6zeAv4Vat+aaRZhO12vFN6pw1rZVQ=\",\"identity\":\"alinesno-infra-base-config-dev\",\"remarks\":\"This is a test configuration\",\"version\":\"1.0\"}}";

        String encryptedData = AesEncryptionUtils.encrypt(originalData, PASSWORD);
        String decryptedData = AesEncryptionUtils.decrypt(encryptedData, PASSWORD);

        Assertions.assertEquals(originalData, decryptedData);
    }

    @Test
    public void testGenerateRandomKey() throws NoSuchAlgorithmException {
        String randomKey = AesEncryptionUtils.generateRandomKey();
        Assertions.assertNotNull(randomKey);
        Assertions.assertFalse(randomKey.isEmpty());
    }
}
