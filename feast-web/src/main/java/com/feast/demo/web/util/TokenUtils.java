package com.feast.demo.web.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class TokenUtils {


    // 密钥
    private static String mykey = "d4J2MuXCyv1gpySv6vk4mQ==";

    // 分隔符
    private static String splitSymbols = "=====";


    public static void main(String[] args) {

        String token = getToken("aaaa1", "bbbb1");
        System.out.println("获取  token  ：  " + token);

        boolean result = isValidToken(token, "aaaa1", "bbbb1");
        System.out.println("验证结果  ：  " + result);
    }


    public static String getToken(String deviceID, String userID){

        if (null == deviceID
                || deviceID.equals("")
                || null == userID
                || userID.equals("")){

            return null;
        }

        String token =  deviceID + splitSymbols + userID + splitSymbols + System.currentTimeMillis();

        token = encrypt(token);

        return token;
    }

    public static boolean isValidToken(String token, String deviceID, String userID) {

        if (null == token
                || token.equals("")
                || null == deviceID
                || deviceID.equals("")
                || null == userID
                || userID.equals("")) {

            return false;
        }

        try {

            token = decrypt(token);

            if (null == token)
                return false;

            String[] tokens = token.split(splitSymbols);
            return tokens.length == 3
                    && tokens[0].equals(deviceID)
                    && tokens[1].equals(userID);

        } catch (Exception ignored) {
        }

        return false;

    }


    private static Key getKey(){

        try {
            return new SecretKeySpec(base64Decode(mykey), "AES");
        } catch (Exception ignored) {
        }

        return  null;
    }


    private static String encrypt(String str) {

        try {
            Cipher cipher = Cipher.getInstance("AES");//算法/模式/补码方式
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            byte[] eb = cipher.doFinal(str.getBytes("utf-8"));
            return base64Encode(eb);

        } catch (Exception ignored) {
        }

        return null;
    }

    //解密
    private static String decrypt(String str) {

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getKey());
            byte[] by = base64Decode(str);
            byte[] db = cipher.doFinal(by);
            return new String(db);
        } catch (Exception ignored) {
        }
        return null;
    }

    private static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    private static byte[] base64Decode(String base64Code) throws Exception {
        return (null == base64Code || base64Code.equals("")) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

}
