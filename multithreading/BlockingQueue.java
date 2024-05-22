package multithreading;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {
    private Queue<Integer> queue;
    private int capacity;

    public BlockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public boolean add(int item) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                return false;
            }
            queue.add(item);
            // Notify all the threads that are in the wait state
            queue.notifyAll();
            return true;
        }
    }

    public int remove() {
        synchronized (queue) {
            while (queue.size() == 0) {
                // If remove thread has access to the lock but initially there are no items in the queue
                // The remove thread has to wait till any add thread adds item to the queue
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

            }
            queue.notifyAll();
            return queue.poll();
        }
    }
}
