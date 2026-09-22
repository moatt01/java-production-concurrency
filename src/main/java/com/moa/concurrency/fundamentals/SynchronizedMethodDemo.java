package com.moa.concurrency.fundamentals;

public class SynchronizedMethodDemo {

    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {

        int threadCount = 1_000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {

            threads[i] = new Thread(
                    SynchronizedMethodDemo::increment,
                    "worker-" + i
            );

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Expected: " + threadCount);
        System.out.println("Actual: " + counter);
    }

    private static synchronized void increment() {
        counter++;
    }
}