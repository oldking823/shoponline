package com.oldking.mall.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class AESUtil {
    /**
     * AES加密解密
     * @param buffer 密文/明文
     * @param appsecret 密钥
     * @param mode 模式 1：加密，2：解密
     * @return
     */
    public static byte[] encryptAndDecrypt(byte[] buffer, String appsecret,Integer mode) throws Exception{
//        加载加密解密算法处理对象（包含算法、密钥管理）
        Security.addProvider(new BouncyCastleProvider());
//        根据不同算法创建密钥
        SecretKeySpec secretKeySpec = new SecretKeySpec(appsecret.getBytes(), "AES");
//        设置加密模式,设置算法（AES,ECB,KPCS7padding）
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
//        初始化加密配置
        cipher.init(mode, secretKeySpec);
//        执行加密或解密
        return cipher.doFinal(buffer);
    }
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception{
        String txt = "SpringCloud Alibaba";
        String appsecret = "aaaaaaaaaaaaaaaa";
        Integer mode = 1;

        byte[] bytes = encryptAndDecrypt(txt.getBytes("UTF-8"), appsecret, mode);
        String encode = Base64Util.encode(bytes);
        System.out.println(encode);

//        解码-》解密
        byte[] bytes1 = encryptAndDecrypt(Base64Util.decode(encode), appsecret, 2);
        System.out.println(new String(bytes1,"UTF-8"));

//        System.out.println(new String(bytes, "UTF-8"));
    }
}
