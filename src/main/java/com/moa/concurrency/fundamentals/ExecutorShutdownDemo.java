package com.moa.concurrency.fundamentals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorShutdownDemo {

    public static void main(String[] args)
            throws InterruptedException {

        ExecutorService executor =
                Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 5; i++) {

            int requestId = i;

            executor.submit(() -> process(requestId));
        }

        executor.shutdown();

        System.out.println("Shutdown requested.");

        if (executor.awaitTermination(
                10,
                TimeUnit.SECONDS
        )) {

            System.out.println("All tasks finished.");

        } else {

            System.out.println("Timeout.");
        }
    }

    private static void process(int requestId) {

        System.out.println(
                Thread.currentThread().getName()
                        + " processing "
                        + requestId
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}