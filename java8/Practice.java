package java8;

import java.util.Comparator;
import java.util.List;

public class Practice {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1,2,4,6,4,3,6,8,4,7,9,4,7,8,90);
        List<String> names = List.of("Aman", "Bob", "John", "Saksham", "Ray", "Sam", "Suraj");

        // 1. Write a Java program to calculate the average of a list of integers using streams.
        double average = numbers.stream()
                .mapToDouble(Integer::doubleValue)
                .average().orElse(0.0);
        System.out.println("Average " + average);

        // 2. Write a Java program to convert a list of strings to uppercase or lowercase using streams.
        List<String> upperCase = names.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println(upperCase);
        List<String> lowerCase = names.stream()
                .map(String::toLowerCase)
                .toList();
        System.out.println(lowerCase);

        // 3. Write a Java program to calculate the sum of all even, odd numbers in a list using streams.
        int evenSum = numbers.stream()
                .filter(n -> n % 2 == 0).mapToInt(Integer::intValue)
                .sum();
        System.out.println(evenSum);
        int oddSum = numbers.stream()
                .filter(n -> n % 2 != 0).mapToInt(Integer::intValue)
                .sum();
        System.out.println(oddSum);

        // 4. Write a Java program to remove all duplicate elements from a list using streams.
        List<Integer> removeDuplicates = numbers.stream()
                .distinct().toList();
        System.out.println(removeDuplicates);
        List<Integer> removeDuplicatesSorted = numbers.stream()
                .distinct().sorted().toList();
        System.out.println(removeDuplicatesSorted);

        // 5. Write a Java program to count the number of strings in a list that start with a specific letter using streams.
        long count = names.stream()
                .filter(s->s.startsWith("S")).count();
        System.out.println(count);

        // 6. Write a Java program to sort a list of strings in alphabetical order, ascending and descending using streams.
        List<String> sorted = names.stream()
                .sorted().toList();
        System.out.println(sorted);
        List<String> reverseSorted = names.stream()
                .sorted(Comparator.reverseOrder()).toList();
        System.out.println(reverseSorted);

        // 7. Get the list of length of the names
        List<Integer> namesLength = names.stream()
                .map(String::length)
                .toList();
        System.out.println(namesLength);

        String str = "Hellow";
        System.out.println(str.indexOf('t'));
        String str1 = "Interviewbit".replace('e','s');
        System.out.println(str1);

        StringBuffer s1 = new StringBuffer("Complete");
        s1.setCharAt(1,'i');
        s1.setCharAt(7,'d');
        System.out.println(s1);

    }
}
