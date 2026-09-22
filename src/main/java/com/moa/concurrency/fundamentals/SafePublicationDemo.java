package com.moa.concurrency.fundamentals;

public class SafePublicationDemo {
    public static void main(String[] args) {

        Configuration config1 = Configuration.getInstance();

        System.out.println("Server: " + config1.getServerName());
        System.out.println("Max requests: " + config1.getMaxRequests());

        Configuration config2 = Configuration.getInstance();

        System.out.println("Same instance: " + (config1 == config2));
    }

    public static final class Configuration {

        // volatile = safe publication for double-checked locking
        private static volatile Configuration instance;

        // final = immutable state after construction
        private final String serverName;
        private final int maxRequests;

        // private = prevents direct construction
        private Configuration() {
            this.serverName = "results-server";
            this.maxRequests = 1_000_000;
        }

        public static Configuration getInstance() {

            // First check = avoid synchronization after initialization
            if (instance == null) {

                // synchronized = only one thread initializes
                synchronized (Configuration.class) {

                    // Second check = another thread may have initialized it
                    if (instance == null) {
                        instance = new Configuration();
                    }
                }
            }

            return instance;
        }

        public String getServerName() {
            return serverName;
        }

        public int getMaxRequests() {
            return maxRequests;
        }
    }
}
