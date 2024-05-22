import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class ChangingDatesAndTimes {
    public static void main(String[] args) {
        LocalDateTime ld1 = LocalDateTime.now();
        LocalDateTime changed1 = ld1.minusMinutes(21);
        System.out.println(changed1);

        LocalDateTime changed2 = ld1.withDayOfMonth(15);
        System.out.println(changed2);

        LocalDateTime changed3 = ld1.plus(Period.ofWeeks(1));
        LocalDateTime changed4 = ld1.plus(Duration.ofHours(12));
        System.out.println(changed3 + " " + changed4);

        ld1 = ld1.plusHours(5);
        System.out.println(ld1);

        LocalDateTime ldt3 = LocalDateTime.of(2004,7,14,4,23);
        ldt3 = ldt3.minusHours(2);
        ldt3 = ldt3.withMonth(4);
        ldt3 = ldt3.plus(Period.ofWeeks(2)); System.out.println(ldt3);
    }
}
