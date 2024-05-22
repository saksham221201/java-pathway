package interfaces;

public class Bag implements Foldable, Washable{
    public void fold(){
        System.out.println("Folding Bag");
    }

    @Override
    public void wash() {
        System.out.println("Washing Bag");
    }
}
