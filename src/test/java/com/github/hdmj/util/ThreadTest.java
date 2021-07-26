package com.github.hdmj.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThreadTest {

    @Test
    public void test() {
        ThreadPool.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {

                System.out.println("踩踩我是谁");

                ThreadPool.EXECUTOR_SERVICE.execute(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("hahah ");
                    }
                });
            }
        });
    }

}
