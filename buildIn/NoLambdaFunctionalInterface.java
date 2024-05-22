package buildIn;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class NoLambdaFunctionalInterface {
    public static void main(String[] args) {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.startsWith("S");
            }
        };

        Supplier<String> stringSupplier = new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        };

        System.out.println(stringSupplier.get());

        System.out.println(predicate.test("Saksham"));
        System.out.println(predicate.test("John"));
    }
}
