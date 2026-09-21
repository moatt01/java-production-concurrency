package com.moa.concurrency.fundamentals;



public class MassiveTrafficSimulation {

    public static void main(String[] args) throws InterruptedException {

        int requestCount = 20;

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

        System.out.println("All requests completed.");
    }

    private static void processRequest(String userId) {

        System.out.println(
                Thread.currentThread().getName()
                        + " STARTED " + userId
        );

        try {

            long delay =
                    100 + (long) (Math.random() * 900);

            Thread.sleep(delay);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            return;
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " FINISHED " + userId
        );
    }
}