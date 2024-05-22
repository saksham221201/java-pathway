package staticExample;

public class Human {
    int age;
    String name;
    int salary;
    boolean isMarried;
    static int population; // Not related to object, object-independent attribute, it can be accessed before any object is made.

    public Human(int age, String name, int salary, boolean isMarried) {
        this.age = age;
        this.name = name;
        this.salary = salary;
        this.isMarried = isMarried;
        Human.population += 1;
    }
}
