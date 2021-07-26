package com.github.hdmj.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RequestMapping("/test")
@RestController
public class TestController {

    @RequestMapping("/xml")
    public void test(HttpServletRequest request) throws IOException {
        System.out.println(request.getContentType());
        System.out.println(request.getContentLengthLong());

        InputStream is = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        br.close();
        System.out.println(stringBuilder.toString());
    }
}
