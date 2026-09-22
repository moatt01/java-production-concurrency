package com.moa.concurrency.fundamentals;

public class ReentrantMonitorDemo {

    private static final Object lock = new Object();

    public static void main(String[] args) {

        synchronized (lock) {

            System.out.println("First lock acquired");

            synchronized (lock) {

                System.out.println("Second lock acquired");

            }

            System.out.println("Second lock released");

        }

        System.out.println("First lock released");
    }
}