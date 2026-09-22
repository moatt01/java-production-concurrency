package com.moa.concurrency.fundamentals;

public class DeadlockFixedDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(
                DeadlockFixedDemo::process,
                "Thread-1"
        );

        Thread thread2 = new Thread(
                DeadlockFixedDemo::process,
                "Thread-2"
        );

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Both threads finished.");
    }

    private static void process() {

        synchronized (lockA) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " acquired lockA"
            );

            sleep();

            synchronized (lockB) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " acquired lockB"
                );
            }
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " finished"
        );
    }

    private static void sleep() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}