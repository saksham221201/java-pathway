package collections.queue;

public class Guest {
    private String firstName;
    private String lastName;
    private boolean isMarried;

    public Guest(String firstName, String lastName, boolean isMarried) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isMarried = isMarried;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isMarried=" + isMarried +
                '}';
    }
}
