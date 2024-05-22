package multithreading;

public class VolatileExample extends Thread {
    private volatile boolean running = true;

    public void run() {
        while (running) {
            System.out.println("Running");
        }
    }

    public void stopRunning() {
        running = false;
    }
}