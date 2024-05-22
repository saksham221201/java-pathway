package collections.queue;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Application {
    public static void main(String[] args) {
        Guest john = new Guest("John", "Doe", false);
        Guest bob = new Guest("Bob", "Builder", true);
        Guest sonia = new Guest("Sonia", "Doe", false);
        Guest siri = new Guest("Siri", "Singh", true);

//        Comparator<Guest> isMarried = Comparator.comparing(Guest::isMarried).reversed();
//
//        Queue<Guest> checkInPriorityQueue = new PriorityQueue<>(isMarried);
//        checkInPriorityQueue.offer(john);
//        checkInPriorityQueue.offer(sonia);
//        checkInPriorityQueue.offer(bob);
//        checkInPriorityQueue.offer(siri);
//
//        print(checkInPriorityQueue);

         Queue<Guest> checkIn = new ArrayDeque<>();
         checkIn.add(john);
        // Guest guest = checkIn.remove(); // throws exception NoSuchElementException when trying to remove from an empty queue
        // Guest polledGuest = checkIn.poll(); // It will return null
        // checkIn.offer(john);
        // checkIn.offer(bob);
        // checkIn.offer(sonia);
        // checkIn.poll();
        // checkIn.offer(siri);
         print(checkIn);
    }

    public static void print(Queue<Guest> queue){
        System.out.format("%n--Queue Contents--%n");
        int x = 0;
        for (Guest guest : queue){
            System.out.format("%x: %s %s %n", x++, guest.toString(), x == 1 ? "(Head)":"");
        }
        System.out.println();
    }
}
