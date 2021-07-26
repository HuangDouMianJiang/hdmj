package com.github.hdmj.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class FastJsonTest {

    @Test
    public void test1() {

        @Data
        class User<T> {
            private String name;

            private Integer age;

            private List<String> infoList;

            private Map<String, Object> others;

            private T o;
        }

        User user = new User();

        user.setO(null);
        System.out.println(JSONObject.toJSONString(user,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero
                ));

    }

    @Test
    public void test2() {
        System.out.println("有符号： "+String.valueOf((Integer.valueOf("09c4",16).shortValue())/256-1));
        System.out.println(Integer.valueOf("09c4",16).shortValue()/ 100);
        System.out.println(Integer.valueOf("FC7C",16).intValue() );
        System.out.println(Integer.valueOf("FC7C",16).shortValue() / 256 );
        System.out.println(Integer.valueOf("FC7C",16).shortValue() / 256 -1);
    }

    @Test
    public void test3() {
        System.out.println(LocalDateTime.parse("20210621153145000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }
}
