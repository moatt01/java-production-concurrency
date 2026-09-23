package com.moa.concurrency.fundamentals;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BoundedExecutorDemo {

    public static void main(String[] args) {

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(
                        2,
                        4,
                        10,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(2)
                );

        for (int i = 1; i <= 10; i++) {

            int requestId = i;

            executor.execute(() -> processRequest(requestId));
        }

        executor.shutdown();
    }

    private static void processRequest(int requestId) {

        System.out.println(
                Thread.currentThread().getName()
                        + " processing request "
                        + requestId
        );

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}