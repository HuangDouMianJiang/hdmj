package com.github.hdmj.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest

public class LocalDateTimeTest {

    @Test
    public void test() {
        String date = "19930301";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    @Test
    public void testLong() {
//        String str = "2021-05-24 16:00:42.001";
        String str = "2021-05-24 16:31:58.001";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localtDateAndTime = LocalDateTime.parse(str, formatter);
        ZonedDateTime dateAndTimeInSydney = ZonedDateTime.of(localtDateAndTime, ZoneId.systemDefault() );

        System.out.println("Current date and time in a particular timezone : " + dateAndTimeInSydney);

        ZonedDateTime utcDate = dateAndTimeInSydney.withZoneSameInstant(ZoneOffset.UTC);

        System.out.println("Current date and time in UTC : " + utcDate);
    }

    @Test
    public void testJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        System.out.println(jsonObject.getString("code"));
    }

    private static final DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    @Test
    public void testLong1() {
        long time17Long = 20210606140510123L;
        String time17LongStr = String.valueOf(time17Long);
        String time = time17LongStr.substring(0, 4) + "-" + time17LongStr.substring(4, 6) + "-" + time17LongStr.substring(6, 8) + " " +
                time17LongStr.substring(8, 10) + ":" + time17LongStr.substring(10, 12) + ":" + time17LongStr.substring(12, 14) + "." + time17LongStr.substring(14, 17);
        System.out.println(LocalDateTime.parse(time, formatterUTC));
    }

}
