package com.moa.concurrency.fundamentals;

public class LockContentionDemo {

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(
                LockContentionDemo::process,
                "worker-1"
        );

        Thread thread2 = new Thread(
                LockContentionDemo::process,
                "worker-2"
        );

        Thread thread3 = new Thread(
                LockContentionDemo::process,
                "worker-3"
        );

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    private static void process() {

        long start = System.currentTimeMillis();

        synchronized (lock) {

            long acquired = System.currentTimeMillis();

            System.out.println(
                    Thread.currentThread().getName()
                            + " acquired lock after "
                            + (acquired - start)
                            + " ms"
            );

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println(
                    Thread.currentThread().getName()
                            + " releasing lock"
            );
        }
    }
}