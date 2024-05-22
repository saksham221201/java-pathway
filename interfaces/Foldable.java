package interfaces;

public interface Foldable {
    static void printFoldInstructions(){
        System.out.println("Carefully fold the object.");
    }
    default void fold(){
        System.out.println("Inside default");
    }
}
