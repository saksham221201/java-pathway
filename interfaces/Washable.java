package interfaces;

@FunctionalInterface
public interface Washable {
    default void fold(){
        System.out.println("Folding washed!");
    }

    void wash();
}
