import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class DateTime {
    public static void main(String[] args) {

        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.of(2024, Month.APRIL, 22);
		System.out.println(date1 + " " + date2);

        LocalTime time1 = LocalTime.now();
        LocalTime time2 = LocalTime.of(12, 13, 55);
        LocalTime time3 = LocalTime.of(12, 13, 55, 20000);
		System.out.println(time1 + " " + time2 + " " + time3);

        LocalDateTime dateTime1 = LocalDateTime.now();
		System.out.println(dateTime1);
        LocalDateTime dateTime2 = LocalDateTime.of(date1, time1);
		System.out.println(dateTime2);

        MonthDay monthDay = MonthDay.of(7, 12);
        LocalDate ld = monthDay.atYear(2022);
		System.out.println(ld);

        YearMonth yearMonth = YearMonth.of(2022, 12);
        LocalDate ld2 = yearMonth.atDay(23);
		System.out.println(ld2);

		for(String zoneId : ZoneId.getAvailableZoneIds()){
			System.out.println(zoneId);
		}

        LocalDateTime l = LocalDateTime.now();
        ZonedDateTime z = ZonedDateTime.of(l, ZoneId.of("Europe/Monaco"));
		System.out.println(l + " " + z);

        Clock clock = Clock.systemDefaultZone();
		System.out.println(clock);
        Instant instant = clock.instant();
		System.out.println(instant);

        Clock clock1 = Clock.systemUTC();
		System.out.println(clock1.instant());

        Clock clock2 = Clock.offset(clock, Duration.ofHours(12));
		System.out.println(clock2.instant());

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 4, 17, 17, 41,0);
		System.out.println("Compare: " + localDateTime.compareTo(localDateTime1));

		System.out.println(localDateTime.isAfter(localDateTime1));
		System.out.println(localDateTime.isBefore(localDateTime1));
		System.out.println(localDateTime.isEqual(localDateTime1));

        String dateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE.format(localDateTime);
		System.out.println(dateTimeFormatter);

        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("dd-MMM-yy");
        System.out.println(dateTimeFormatter1.format(localDateTime));

        String formatted = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(localDateTime);
        System.out.println(formatted);

        Period p1 = Period.of(1, 1, 1);
        System.out.println(p1);
        Period p2 = Period.ofDays(100);
        System.out.println(p2);
        LocalDate localDate = LocalDate.of(2024, 1, 1);
        LocalDate localDate1 = LocalDate.now();
        Period p3 = Period.between(localDate, localDate1);
        System.out.println(p3);
        System.out.println(p3.getDays()); // 17

        long numberOfDaysBetweenTwoDates = ChronoUnit.DAYS.between(localDate, localDate1);
        System.out.println(numberOfDaysBetweenTwoDates);
        long numberOfMonthsBetweenTwoDates = ChronoUnit.MONTHS.between(localDate, localDate1);
        System.out.println(numberOfMonthsBetweenTwoDates);

        Duration d1 = Duration.of(1, ChronoUnit.MINUTES);
        Duration d2 = Duration.ofHours(1);
        System.out.println(d2);
        LocalTime t1 = LocalTime.now();
        LocalTime t2 = LocalTime.of(12,12,12);
        Duration d3 = Duration.between(t1, t2);
        System.out.println(d3);
    }
}
