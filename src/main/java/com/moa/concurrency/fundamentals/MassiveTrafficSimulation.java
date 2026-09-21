package com.moa.concurrency.fundamentals;



public class MassiveTrafficSimulation {

    private static int successfulRequests = 0;

    public static void main(String[] args) throws InterruptedException {


        int requestCount = 1_000;

        Thread[] threads = new Thread[requestCount];

        for (int i = 0; i < requestCount; i++) {

            String userId = "USER-" + i;

            threads[i] = new Thread(
                    () -> processRequest(userId),
                    "request-" + i
            );

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(
                "Expected: " + requestCount
        );

        System.out.println(
                "Actual: " + successfulRequests
        );
    }

    private static void processRequest(String userId) {
        int current = successfulRequests;
                Thread.yield();
        successfulRequests = current + 1;
    }
}