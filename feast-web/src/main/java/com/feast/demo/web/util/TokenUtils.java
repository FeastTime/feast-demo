package com.feast.demo.web.util;

import org.bouncycastle.util.encoders.UrlBase64;
import sun.misc.BASE64Decoder;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;


public class TokenUtils {

    // 密钥
    private static String myKeyString = "d4J2MuXCyv1gpySv6vk4mQ==";

    // 分隔符
    private static String splitSymbols = "#====#";

    /**
     * 验证token 正确性
     *
     * @param token token
     * @param deviceID 设备id
     * @param userID 用户id
     * @return 是否正确
     */
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

    /**
     * 获取token
     *
     * @param deviceID 设备ID
     * @param userID 用户ID
     * @return token
     */
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

    /**
     * 获取加密解密的Key
     *
     * @return String key
     */
    private static Key getKey(){

        try {
            return new SecretKeySpec(base64Decode4Key(myKeyString), "AES");
        } catch (Exception ignored) {
        }

        return  null;
    }

    /**
     * 加密
     *
     * @param str 被加密的字符串
     * @return 加密后的字符串
     */
    private static String encrypt(String str) {

        try {
            Cipher cipher = Cipher.getInstance("AES");//算法/模式/补码方式
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            byte[] eb = cipher.doFinal(str.getBytes("utf-8"));
            return base64Encode(eb);

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return null;
    }

    /**
     * 字符串解密
     *
     * @param str 需要解密的字符串
     * @return 解密后的字符串
     */
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

    /**
     * byte 转 url base64 字符串
     *
     * @param bytes 输入的byte数组
     * @return 返回的字符串
     */
    private static String base64Encode(byte[] bytes) {

        byte[] b= UrlBase64.encode(bytes);
        try {
            return new String(b,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
//        return new BASE64Encoder().encode(bytes);
    }

    /**
     *  url base64 字符串 转 byte数组
     *
     * @param base64Code 输入字符串
     * @return 返回的byte数组
     */
    private static byte[] base64Decode(String base64Code) throws Exception {
        return (null == base64Code || base64Code.equals("")) ? null : UrlBase64.decode(base64Code);
    }

    /**
     *  base64 字符串 转 byte数组
     *
     * @param base64Code 输入字符串
     * @return 返回的byte数组
     */
    private static byte[] base64Decode4Key(String base64Code) throws Exception {
        return (null == base64Code || base64Code.equals("")) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * 创建key字符串
     *
     * @return key字符串
     */
    @SuppressWarnings("unused")
    private static String createKey(){

        return "";
    }

    /**
     * 测试方法
     *
     * @param args 入参
     */
    public static void main(String[] args) {

        String token = getToken("999", "18");
        System.out.println("获取  token  ：  " + token);

        boolean result = isValidToken(token, "355301077459561", "10007");
        System.out.println("验证结果  ：  " + result);
    }

}
