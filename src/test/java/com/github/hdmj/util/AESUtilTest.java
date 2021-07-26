package com.github.hdmj.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
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
        for (int i = 0; i < 1; i++) {
            map = new HashMap<>(3);
            map.put("money", 500);
            map.put("mobile", "15210268130");
            map.put("type", 0);
            list.add(map);
        }
        String data = JSONObject.toJSONString(list);
        System.out.println("data:" + data);
        String secret = "1IsuefXUzi9xeUYr";
        data = AESUtil.encryptIntoHexString(data, secret);
        System.out.println("encrypt data:" + data);
        data = AESUtil.decryptByHexString(data, secret);
        System.out.println("decrypt data:" + data);
        JSONArray jsonObject = JSONObject.parseArray(data);
        System.out.println(jsonObject);
    }

    @Test
    public void test2() {
        JSONObject obj = new JSONObject();
        obj.put("oElectric", 434.01234563);
        System.out.println(MathUtil.div(obj.getDoubleValue("oElectric"), 100, 2));
    }

    @Test
    public void test3() {
        BigDecimal one = new BigDecimal(1);
        BigDecimal zero = new BigDecimal(0);
        System.out.println(BigDecimal.ZERO.compareTo(one));
        System.out.println(one.compareTo(BigDecimal.ZERO));
        System.out.println(zero.compareTo(BigDecimal.ZERO));
    }

    @Test
    public void test4() {
        double lng_b = 0;
        double lng_a = 0;
        double lat_b = 1;
        double lat_a = 1;
        double y = Math.sin(lng_b - lng_a) * Math.cos(lat_b);
        double x = Math.cos(lat_a) * Math.sin(lat_b) - Math.sin(lat_a) * Math.cos(lat_b) * Math.cos(lng_b - lng_a);
        double bearing = Math.atan2(y, x);
        // debugger;
        bearing = 180 * bearing / Math.PI;
        if (bearing < 0) {
            bearing = bearing + 360;
        }
        System.out.println(bearing);

    }

    @Test
    public void test5() {
        double x1 = 116.404107;

        double y1 = 39.910912;

        double x2 = 114.525445;


        double y2 = 38.02334;
        String a = "116.408414,39.900381";
        String b = "116.334257,39.685123";
        String[] p = a.split(",");
        String[] n = b.split(",");
        System.out.println(duShu(Double.parseDouble(p[0]),
                Double.parseDouble(p[1]),
                Double.parseDouble(n[0]),
                Double.parseDouble(n[1])));

    }

    public static double duShu(double x1, double y1, double x2, double y2) {
        return (180 / Math.PI) * Math.atan2(x2 - x1, y2 - y1);
    }

    @Test
    public void test6() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("vinno", "LNBSCB3F4FD111795");
        jsonObject.put("format", "json");
        String result = HttpRequest.post("http://wechatapi-iov.bjev.com.cn/api/QueryAutoCheckingInfo")
                .body(jsonObject.toJSONString()).execute().body();
        System.out.println(result);
    }

    @Test
    public void test7() {
        double a = 123213;
        System.out.println(BigDecimal.valueOf(a).divide(BigDecimal.valueOf(100), 3, BigDecimal.ROUND_UP));
    }

}
