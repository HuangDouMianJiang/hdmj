package com;

import static org.junit.Assert.assertTrue;

import com.thread.Executors;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    /**
     * 用于测试创建多个线程时 待所有的线程都结束了 在执行后面的代码
     * 参考：https://vmlens.com/articles/examples_countDownLatch/
     * <p>
     * 当您想要使用CountDownLatch等待另一个线程时，请按照下列步骤操作：
     * <p>
     * 1、使用您正在等待的线程数初始化CountDownLatch。
     * 2、当线程完成其任务时，调用finally块中的countDown方法。
     * 3、使用标志或计数器表示任务成功。
     * 4、等待方法的线程等待。传播 await 抛出的InterruptedException或在捕获InterruptedException时恢复被中断的标志。
     */
    @Test
    public void testCountDownLatch() {
        List<Integer> integerList = Collections.synchronizedList(new ArrayList<Integer>(1000));
        final CountDownLatch latch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {

            final int index = i;

            Runnable runnable = () -> {
                integerList.add(index);
                latch.countDown();
                System.out.println(latch.getCount());
            };
            Executors.POOL.execute(runnable);

        }
        if (0 < latch.getCount()) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        for (int i = 0; i < integerList.size(); i++) {
            System.out.println(i);
        }

    }


}
