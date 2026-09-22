package com.moa.concurrency.fundamentals;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    private static final int CAPACITY = 3;

    private static final Queue<String> queue = new ArrayDeque<>();

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition notEmpty = lock.newCondition();
    private static final Condition notFull = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        Thread consumer = new Thread(
                ConditionDemo::consume,
                "consumer"
        );

        Thread producer = new Thread(
                ConditionDemo::produce,
                "producer"
        );

        consumer.start();

        Thread.sleep(1000);

        producer.start();

        producer.join();
        consumer.join();

        System.out.println("Finished.");
    }

    private static void produce() {

        for (int i = 1; i <= 5; i++) {

            lock.lock();

            try {

                while (queue.size() == CAPACITY) {
                    notFull.await();
                }

                String request = "REQUEST-" + i;

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