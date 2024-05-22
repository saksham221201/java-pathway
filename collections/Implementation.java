package collections;

public class Implementation implements Contract{
    @Override
    public void term1() {
        System.out.println("Term1 inside implemented");
    }

    @Override
    public void term2() {
        System.out.println("Term2 inside implemented");
    }

    @Override
    public void extendedTerm() {
        System.out.println("Implementation:extended");
    }
}
