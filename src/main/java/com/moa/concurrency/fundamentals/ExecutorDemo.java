package com.moa.concurrency.fundamentals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {

    public static void main(String[] args) {

        ExecutorService executor =
                Executors.newFixedThreadPool(4);

        for (int i = 1; i <= 10; i++) {

            int requestId = i;

            executor.submit(() -> processRequest(requestId));
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}