package com.github.hdmj.util;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtil {

    /**
     * 加密使用KEY
     */
    static byte[] KEY = "c084dca9702442feb0f5448ea1f591ba".getBytes(StandardCharsets.UTF_8);

    /**
     * 移动向量
     */
    static byte[] IV = "d9dcfa2909134731".getBytes(StandardCharsets.UTF_8);

    static char PADDING_CHAR = '\034';

    /**
     * 数据加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(KEY, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
        int paddingSize = 16 - data.length() % 16;
        String padding = String.format("%0" + paddingSize + "d", 0).replace('0', PADDING_CHAR);
        String padded = data + padding;
        byte[] encrypted = cipher.doFinal(padded.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 数据解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(KEY, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
        byte[] encrypted = Base64.getDecoder().decode(data);
        String padded = new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        return padded.replaceAll(PADDING_CHAR + "+$", "");
    }

    public static void main(String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("msg", "操作成功");
        JSONObject data = new JSONObject();
        data.put("accessToken", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiMTUzYjViYTAtOThiYS00NmE3LTliMzQtMTk3YjFjNDEzMmMzIiwiMjU3MDYyIl0sImV4cCI6MTYxOTA5ODMzN30.We0wUZaZeaShP37rXn7i4MILNcSISvluUPuq_jlGjjY");
        data.put("expireTime", 7200);
        String dataStr = encrypt(data.toJSONString());
        jsonObject.put("data", dataStr);
        System.out.println(jsonObject);
        System.out.println(decrypt(dataStr));

    }
}
