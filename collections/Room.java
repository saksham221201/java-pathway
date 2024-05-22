package collections;

public class Room implements Comparable<Room>{
    private String name;
    private String type;
    private int capacity;
    private double rate;
    private boolean isPetFriendly;

    public Room(String name, String type, int capacity, double rate, boolean isPetFriendly) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.rate = rate;
        this.isPetFriendly = isPetFriendly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isPetFriendly() {
        return isPetFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        isPetFriendly = petFriendly;
    }

    @Override
    public int compareTo(Room o) {
        int result = this.getName().compareTo(o.getName());
        return result != 0 ? result : this.getType().compareTo(o.getType());
    }
}
