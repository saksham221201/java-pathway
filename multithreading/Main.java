package multithreading;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main is starting");

        // VolatileExample volatileExample = new VolatileExample();
        // volatileExample.start();
        // Sleep for 1 second
        // Thread.sleep(1000);
        // volatileExample.stopRunning();

        /*
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread());
        }, "Our Thread");
        thread.start();
        // This blocks the execution of parent thread and all the child threads will complete first and then parent thread will resume
        thread.join();
        */

        // Creating deadlock situation
        String lock1 = "LOCK1";
        String lock2 = "LOCK2";

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Exception " + e.getMessage());
                }
                synchronized (lock2) {
                    System.out.println("Lock acquired");
                }
            }
        }, "Thread1");

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Exception " + e.getMessage());
                }
                synchronized (lock1) {
                    System.out.println("Lock acquired");
                }
            }
        }, "Thread2");

        thread1.start();
        thread2.start();

        System.out.println("Main is exiting");
    }
}
