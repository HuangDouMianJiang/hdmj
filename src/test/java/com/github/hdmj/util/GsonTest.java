package com.github.hdmj.util;


import com.github.hdmj.entity.SDKOrderInfoModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class GsonTest {


    @Test
    public void test1() {
        SDKOrderInfoModel model = new SDKOrderInfoModel();
        model.setBigOrderNo("12332132131");
        model.setSign("sssss");
        model.setBigOrderTime(null);
        model.setCustomerName("");
        System.out.println("不序列化 Value 是 Null：" + new Gson().toJson(model));
        System.out.println("序列化 Value是 Null：" + new GsonBuilder().serializeNulls().create().toJson(model));
    }

    @Test
    public void test2() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        System.out.println(ZonedDateTime.of(LocalDateTime.parse("20210513112826", formatter), ZoneId.systemDefault()));
    }

}
