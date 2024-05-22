package collections.room;

import collections.Room;

import java.util.*;
import java.util.stream.Collectors;

public class RoomService {

    private Collection<Room> rooms;
    public RoomService(){
        this.rooms = new LinkedHashSet<>();
    }

    public void applyDiscount(final double discount){
        rooms.forEach(r -> r.setRate(r.getRate() - discount));
    }

    public Collection<Room> getRoomsByCapacity(final int requiredCapacity){
        return rooms.stream().filter(r-> r.getCapacity() >= requiredCapacity).collect(Collectors.toList());
    }

    public Collection<Room> getRoomsByRateAndType(final double rate, final String type){
        return rooms.stream()
                .filter(r -> r.getRate() > rate)
                .filter(r -> r.getType().equals(type))
                .collect(Collectors.toList());
    }

    public Collection<Room> getInventory(){
        return new HashSet<>(this.rooms);
    }

    public void createRoom(String name, String type, int capacity, double rate){
        this.rooms.add(new Room(name, type, capacity, rate, true));
    }

    public void createRooms(Room[] rooms){
        this.rooms.addAll(Arrays.asList(rooms));
    }
}
