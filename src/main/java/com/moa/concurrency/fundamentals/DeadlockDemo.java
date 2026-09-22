package com.moa.concurrency.fundamentals;

public class DeadlockDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {

            synchronized (lockA) {

                System.out.println("Thread-1 acquired lockA");

                sleep();

                System.out.println("Thread-1 waiting for lockB");

                synchronized (lockB) {
                    System.out.println("Thread-1 acquired lockB");
                }
            }

        }, "Thread-1");


        Thread thread2 = new Thread(() -> {

            synchronized (lockB) {

                System.out.println("Thread-2 acquired lockB");

                sleep();

                System.out.println("Thread-2 waiting for lockA");

                synchronized (lockA) {
                    System.out.println("Thread-2 acquired lockA");
                }
            }

        }, "Thread-2");


        thread1.start();
        thread2.start();
    }

    private static void sleep() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}