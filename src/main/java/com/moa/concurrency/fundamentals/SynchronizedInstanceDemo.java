package com.moa.concurrency.fundamentals;

public class SynchronizedInstanceDemo {

    public static void main(String[] args) throws InterruptedException {

        Worker worker1 = new Worker("WORKER-1");
        Worker worker2 = new Worker("WORKER-2");

        Thread thread1 = new Thread(worker1::process);
        Thread thread2 = new Thread(worker2::process);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    static class Worker {

        private final String name;

        Worker(String name) {
            this.name = name;
        }

        public synchronized void process() {

            System.out.println(name + " entered");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println(name + " leaving");
        }
    }
}