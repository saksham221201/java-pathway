package java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Practice {

    //public static void main(String[] args) {
//        List<Integer> numbers = List.of(1,2,4,6,4,3,6,8,4,7,9,4,7,8,90);
//        List<String> names = List.of("Aman", "Bob", "John", "Saksham", "Ray", "Sam", "Suraj");
//
//        // 1. Write a Java program to calculate the average of a list of integers using streams.
//        double average = numbers.stream()
//                .mapToDouble(Integer::doubleValue)
//                .average().orElse(0.0);
//        System.out.println("Average " + average);
//
//        // 2. Write a Java program to convert a list of strings to uppercase or lowercase using streams.
//        List<String> upperCase = names.stream()
//                .map(String::toUpperCase)
//                .toList();
//        System.out.println(upperCase);
//        List<String> lowerCase = names.stream()
//                .map(String::toLowerCase)
//                .toList();
//        System.out.println(lowerCase);
//
//        // 3. Write a Java program to calculate the sum of all even, odd numbers in a list using streams.
//        int evenSum = numbers.stream()
//                .filter(n -> n % 2 == 0).mapToInt(Integer::intValue)
//                .sum();
//        System.out.println(evenSum);
//        int oddSum = numbers.stream()
//                .filter(n -> n % 2 != 0).mapToInt(Integer::intValue)
//                .sum();
//        System.out.println(oddSum);
//
//        // 4. Write a Java program to remove all duplicate elements from a list using streams.
//        List<Integer> removeDuplicates = numbers.stream()
//                .distinct().toList();
//        System.out.println(removeDuplicates);
//        List<Integer> removeDuplicatesSorted = numbers.stream()
//                .distinct().sorted().toList();
//        System.out.println(removeDuplicatesSorted);
//
//        // 5. Write a Java program to count the number of strings in a list that start with a specific letter using streams.
//        long count = names.stream()
//                .filter(s->s.startsWith("S")).count();
//        System.out.println(count);
//
//        // 6. Write a Java program to sort a list of strings in alphabetical order, ascending and descending using streams.
//        List<String> sorted = names.stream()
//                .sorted().toList();
//        System.out.println(sorted);
//        List<String> reverseSorted = names.stream()
//                .sorted(Comparator.reverseOrder()).toList();
//        System.out.println(reverseSorted);
//
//        // 7. Get the list of length of the names
//        List<Integer> namesLength = names.stream()
//                .map(String::length)
//                .toList();
//        System.out.println(namesLength);
//
//        String str = "Hellow";
//        System.out.println(str.indexOf('t'));
//        String str1 = "Interviewbit".replace('e','s');
//        System.out.println(str1);
//
//        StringBuffer s1 = new StringBuffer("Complete");
//        s1.setCharAt(1,'i');
//        s1.setCharAt(7,'d');
//        System.out.println(s1);

//        List<Integer> newList = List.of(1,10,3,9,5);
//
//        System.out.println(
//                newList.stream().collect(Collectors.averagingInt(Integer::intValue))
//        );

    //}

    public class Sample {

        static class UserScore {
            private final String name;
            private final int score;

            public UserScore(String name, int score) {
                this.name = name;
                this.score = score;
            }

            public String getName() {
                return this.name;
            }

            public int getScore() {
                return this.score;
            }

            @Override
            public String toString() {
                return "UserScore{" +
                        "name='" + name + '\'' +
                        ", score=" + score +
                        '}';
            }
        }

        static class Competition {
            private final List<UserScore> scores;

            private Competition(UserScore... scores) {
                this.scores = List.of(scores);
            }

            public List<UserScore> getScores() {
                return this.scores;
            }
        }

    }

    public static void main(String[] args) {
        Sample.Competition round1 = new Sample.Competition(
                new Sample.UserScore("Harish", 2),
                new Sample.UserScore("Rudy", 4),
                new Sample.UserScore("Alexa", 9),
                new Sample.UserScore("Mary", 10),
                new Sample.UserScore("Bella", 2),
                new Sample.UserScore("John", 1)
        );

        Optional optional = round1.getScores().stream().map(Sample.UserScore::getScore).reduce(Integer::sum);
        System.out.println(optional.get());

        round1.getScores().stream().sorted(Comparator.comparing(Sample.UserScore::getScore).thenComparing(Sample.UserScore::getName)).collect(Collectors.toList()).forEach(i-> System.out.println(i));

        System.out.println(
        round1.getScores().stream().sorted(Comparator.comparing(Sample.UserScore::getScore).reversed()).findFirst().get());
        
    }

}
