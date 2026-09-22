package com.moa.concurrency.fundamentals;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultiplePC {
    private static final int CAPACITY = 3;

    private static final Queue<String> queue = new ArrayDeque<>();

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition notEmpty = lock.newCondition();
    private static final Condition notFull = lock.newCondition();

    private static int requestId = 0;
    public static void main(String[] args) throws InterruptedException {

        Thread producer1 = new Thread(
                MultiplePC::produce,
                "producer-1"
        );

        Thread producer2 = new Thread(
                MultiplePC::produce,
                "producer-2"
        );

        Thread consumer1 = new Thread(
                MultiplePC::consume,
                "consumer-1"
        );

        Thread consumer2 = new Thread(
                MultiplePC::consume,
                "consumer-2"
        );

        consumer1.start();
        consumer2.start();

        producer1.start();
        producer2.start();

        producer1.join();
        producer2.join();
        consumer1.join();
        consumer2.join();

        System.out.println("Finished.");
    }

    private static void produce() {

        for (int i = 1; i <= 5; i++) {

            lock.lock();

            try {

                while (queue.size() == CAPACITY) {
                    notFull.await();
                }
                requestId++;
                String request = "REQUEST-" + requestId;

                queue.add(request);

                System.out.println(
                        Thread.currentThread().getName()
                                + " produced " + request
                                + " | queue size = " + queue.size()
                );

                notEmpty.signal();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                return;

            } finally {

                lock.unlock();
            }
        }
    }

    private static void consume() {

        for (int i = 1; i <= 5; i++) {

            lock.lock();

            try {

                while (queue.isEmpty()) {
                    System.out.println("Consumer waiting...");
                    notEmpty.await();
                }

                String request = queue.remove();

                System.out.println(
                        Thread.currentThread().getName()
                                + " consumed " + request
                                + " | queue size = " + queue.size()
                );

                notFull.signal();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                return;

            } finally {

                lock.unlock();
            }

            sleep();
        }
    }

    private static void sleep() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
