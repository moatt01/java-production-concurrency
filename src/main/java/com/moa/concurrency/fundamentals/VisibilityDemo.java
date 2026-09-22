package com.moa.concurrency.fundamentals;

public class VisibilityDemo {


        private static volatile boolean running = true;

        public static void main(String[] args)
                throws InterruptedException {

            Thread worker = new Thread(() -> {

                while (running) {
                    // simulate work
                }

                System.out.println(
                        "Worker stopped."
                );

            }, "worker");

            worker.start();

            Thread.sleep(1000);

            System.out.println(
                    "Main requesting shutdown..."
            );

            running = false;

            worker.join();

            System.out.println(
                    "Application finished."
            );
        }
    }

