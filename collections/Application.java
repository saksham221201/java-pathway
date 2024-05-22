package collections;

import collections.room.RoomService;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        Room manchester = new Room("Manchester", "Suite", 5, 250.00, false);
        Room cambridge = new Room("Cambridge", "Premium", 4, 175.00, true);
        Room london = new Room("London", "Guest Room", 10, 550.00, false);
        Room oxford = new Room("Oxford", "Suite", 15, 650.00, true);
        Room oxfordGuest = new Room("Oxford", "Guest", 15, 650.00, true);

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("Monday");
        treeSet.add("Tuesday");
        treeSet.add("Wednesday");
        treeSet.add("Thursday");
        treeSet.add("Friday");
        treeSet.add("Saturday");
        treeSet.add("Sunday");
        System.out.println("The original tree set is as follows : " + treeSet);

        TreeSet<String> cloned = (TreeSet<String>) treeSet.clone();
        System.out.println("Cloned: " + cloned);


        List<Room> roomsList = new ArrayList<>(List.of(manchester, cambridge, london, oxfordGuest));
        roomsList.sort(Comparator.naturalOrder());

        int index = Collections.binarySearch(roomsList, oxford, Comparator.comparing(Room::getRate));
        System.out.println(index);
        roomsList.forEach(r-> System.out.println(r.getName()));

        Collection<Room> rooms = new ArrayList<>(Arrays.asList(cambridge, manchester, london, oxford));

        double petFriendlyRoom = rooms.stream()
                .filter(Room::isPetFriendly)
                .mapToDouble(Room::getRate)
                .sum();
        // System.out.println(petFriendlyRoom);

        Collection<Room> ava = rooms.stream().filter(r-> r.getCapacity() >= 10).toList();
        // ava.forEach(r-> System.out.println(r.getName()));

        List<Integer> numbers = List.of(500,1500,2500,2000,100,3000,20);
        NavigableSet<Integer> numberTree = new TreeSet<>(numbers); // Store in sorted order
        // Uses binary tree
        numberTree
                .forEach(System.out::println);

        // System.out.println("Decending");
        // numberTree.descendingSet().forEach(System.out::println);

        // double total = getPotentialRevenue(cambridge, manchester, london); It is not the best way to do it.
//        Collection<Room> rooms = new ArrayList<>();
//        System.out.println(rooms.add(cambridge));
//        System.out.println(rooms.add(manchester));
//        System.out.println(rooms.add(london));
//        System.out.println(rooms.size());
//
//        Set<String> set = new HashSet<>();
////        System.out.println(set.add("Saksham"));
////        System.out.println(set.add("Saksham"));
////        System.out.println(set.add("John"));
////        System.out.println(set.add("Ria"));
////        System.out.println(set.add("Kamad"));
////        System.out.println(set.add("ABC"));
////        System.out.println(set.add("Randy"));
////        System.out.println(set.add("Surya"));
////        System.out.println(set.add("Virat"));
////        System.out.println(set);
////        System.out.println(set.remove("ABC"));
////        System.out.println(set.removeAll(List.of("John", "Ria")));
////        System.out.println(set);
////        System.out.println(set.retainAll(List.of("Randy", "Saksham")));
////        System.out.println(set);
////
////        System.out.println(set.contains("ABX"));
////        System.out.println(set);
////        System.out.println("Contains all " + set
////                .containsAll(List.of("Saksham", "Ria")));
//        // set.clear();
//        // System.out.println(set);
//        double total = getPotentialRevenue(rooms);
//        // System.out.println(total);
//
//        Contract contract = new Implementation();
//        // printTerms(contract);
//
//
//        Collection<String> c = new ArrayDeque<>();
//        c.add("Laptop");
//        c.add("Phone");
//        c.add("Phone");
//        c.add("Phone");
//        c.add("Headphone");
//        System.out.println(c);
//        System.out.println(c.remove("Phone"));
//        System.out.println(c);
//        Collection<String> s = new HashSet<>(c);
//        System.out.println(s);
//
////        Collection collection = new ArrayList<>();
////        collection.addAll(List.of(new Room("Cam", "trype", 3, 32.99), 3L));
////        collection.stream()
////                .forEach(e -> System.out.println(e.getClass()));
//    }
///*
//    private static double getPotentialRevenue(Room room1, Room room2, Room room3){
//        return room1.getRate() + room2.getRate() + room3.getRate();
//    }
//*/
//
//    private static double getPotentialRevenue(Collection<Room> rooms){
//        return rooms.stream()
//                .mapToDouble(Room::getRate)
//                .sum();
//    }
//
//    private static void printTerms(Contract contract){
//        System.out.println("Contract inside Application " + contract);
//        contract.term1();
//        contract.term2();
//        contract.extendedTerm();
    }
}
