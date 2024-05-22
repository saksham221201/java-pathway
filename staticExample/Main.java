package staticExample;

public class Main {
    public static void main(String[] args) {
        Human saksham = new Human(22, "Saksham", 100000, false);
        Human kunal = new Human(22, "Kunal", 200000, false);
        Human mehul = new Human(32, "Mehul", 500000, true);
        System.out.println(saksham.name);
        System.out.println(Human.population);

    }
}
