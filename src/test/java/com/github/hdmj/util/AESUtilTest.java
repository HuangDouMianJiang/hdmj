package com.github.hdmj.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class AESUtilTest {

    @Test
    public void test() {
        List<Map<String, Object>> list = new ArrayList<>(3);
        Map<String, Object> map = null;
        for (int i = 0; i < 3; i++) {
            map = new HashMap<>(3);
            map.put("mobile", "18512345678");
            map.put("money", 1000.00D);
            map.put("type", i % 2);
            list.add(map);
        }
        String data = JSONObject.toJSONString(list);
        System.out.println("data:" + data);
        String secret = "e143914d1e3b42do";
        data = AESUtil.encryptIntoHexString(data, secret);
        System.out.println("encrypt data:" + data);
        data = AESUtil.decryptByHexString(data, secret);
        System.out.println("decrypt data:" + data);
    }
}
