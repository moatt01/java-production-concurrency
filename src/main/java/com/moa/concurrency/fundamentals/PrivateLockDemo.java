package com.moa.concurrency.fundamentals;

import java.util.concurrent.atomic.AtomicInteger;

public class PrivateLockDemo {


        private static int successfulRequests = 0;
        private static final Object lock = new Object();

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
             synchronized (lock){
                 successfulRequests++;
             }
        }
    }

