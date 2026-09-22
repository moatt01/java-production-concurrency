
    package com.moa.concurrency.fundamentals;

import java.util.concurrent.locks.ReentrantLock;

    public class FairLockDemo {

        private static final ReentrantLock lock =
                new ReentrantLock(true);

        public static void main(String[] args) throws InterruptedException {

            for (int i = 1; i <= 5; i++) {

                int workerId = i;

                Thread thread = new Thread(() -> {

                    lock.lock();

                    try {
                        System.out.println(
                                "Worker-" + workerId + " acquired lock"
                        );

                        Thread.sleep(500);

                    } catch (InterruptedException e) {

                        Thread.currentThread().interrupt();

                    } finally {
                        lock.unlock();
                    }

                });

                thread.start();

                Thread.sleep(50);
            }
        }
    }

