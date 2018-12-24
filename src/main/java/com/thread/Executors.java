package com.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class Executors {

    /**
     * 订单线程工厂
     */
    private final static ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("order-pool-%d").build();


    /**
     * 线程池
     * corePoolSize 要保留在池中的线程数，即使它们处于空闲状态也保留当前你设置的线程数，除非设置了{@code allowCoreThreadTimeOut}
     * maximumPoolSize 线程池中允许的最大线程数
     * keepAliveTime 当线程数大于 corePoolSize时，这是多余空闲线程在终止之前等待新任务的最长时间。
     * workQueue队列用于在执行任务之前保存任务。此队列仅包含{@code execute}方法提交的{@code Runnable} *任务。
     */
    public final static ExecutorService POOL = new ThreadPoolExecutor(64, 10240,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(10240), THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());

}
