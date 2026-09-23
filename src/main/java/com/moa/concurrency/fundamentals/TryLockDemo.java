package com.moa.concurrency.fundamentals;

import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(
                TryLockDemo::longTask,
                "worker-1"
        );

        Thread thread2 = new Thread(
                TryLockDemo::tryTask,
                "worker-2"
        );

        thread1.start();

        // Give worker-1 time to acquire the lock
        Thread.sleep(100);

        thread2.start();

        thread1.join();
        thread2.join();
    }

    private static void longTask() {

        lock.lock();

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " acquired lock"
            );

            Thread.sleep(3000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            lock.unlock();

            System.out.println(
                    Thread.currentThread().getName()
                            + " released lock"
            );
        }
    }

    private static void tryTask() {

        System.out.println(
                Thread.currentThread().getName()
                        + " trying to acquire lock"
        );

        if (lock.tryLock()) {

            try {

                System.out.println(
                        Thread.currentThread().getName()
                                + " acquired lock"
                );

            } finally {

                lock.unlock();
            }

        } else {

            System.out.println(
                    Thread.currentThread().getName()
                            + " could not acquire lock"
            );
        }
    }
}