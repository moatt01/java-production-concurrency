package com.moa.concurrency.fundamentals;

import java.util.concurrent.*;

public class CallableFutureDemo {

    public static void main(String[] args) {

        ExecutorService executor =
                Executors.newFixedThreadPool(2);

        Callable<String> task = () -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " started task"
            );

            try {

                Thread.sleep(10_000);

            } catch (InterruptedException e) {

                System.out.println("Task interrupted.");

                Thread.currentThread().interrupt();

                return "CANCELLED";
            }

            return "SUCCESS";
        };

        Future<String> future =
                executor.submit(task);

        try {

            String result =
                    future.get(2, TimeUnit.SECONDS);

            System.out.println("Result: " + result);

        } catch (TimeoutException e) {

            System.out.println("Task timed out.");

            future.cancel(true);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {

            System.out.println(
                    "Task failed: " + e.getCause()
            );
        }

        executor.shutdown();
    }
}