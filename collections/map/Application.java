package collections.map;

import collections.Room;
import collections.queue.Guest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Application {
    Map<Room, Guest> bookings = new HashMap<>();
    public static void main(String[] args) {
        Room oxford = new Room("Oxford", "Suite", 5, 100.0, true);
        Room london = new Room("London", "Deluxe", 15, 200.0, false);

        Guest saksham = new Guest("Saksham", "Joshi", false);
        Guest maria = new Guest("Maria", "Doe", true);

        Map<Room, Guest> assignments = new HashMap<>();
        System.out.println("Saksham " + assignments.put(oxford, maria));
        System.out.println("Oxford:" + assignments.get(oxford));

        // System.out.println("Oxford: " + assignments.get(new Room("Oxford", "Suite", 5, 100.0, true))); // This will return null because we have not override hashcode and equals method


        Set<Map.Entry<Room, Guest>> cv = assignments.entrySet();
        for (Map.Entry<Room, Guest> entry : assignments.entrySet()){
            Room room = entry.getKey();
            Guest guest = entry.getValue();
            System.out.format("%s : %s%n", room.getName(), guest.getFirstName());
        }
    }

    public boolean book(Room room, Guest guest){
        return bookings.putIfAbsent(room, guest) == null;
    }

    public double totalRevenue(){
        return bookings.keySet().stream()
                .mapToDouble(Room::getRate)
                .sum();
    }
}
