package com.alinesno.infra.base.config.core.tools;

/**
 * 加密工具类
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class EnctyptionUtils {


    public static String encrypt(String data , String publickKey) throws Exception {
        AsymmetricEncryption.getInstance().loadPublicKey(publickKey) ;
        return AsymmetricEncryption.getInstance().encrypt(data);
    }

}
