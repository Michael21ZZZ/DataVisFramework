package edu.cmu.cs214.hw6.NLP;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class TimeDemo {
    public void testDate(String textDate, DateTimeFormatter formatter) {
        LocalDate date = LocalDate.parse(textDate, formatter);
        System.out.println(date);
    }
    public static void main(String[] args) {
        TimeDemo timeDemo = new TimeDemo();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = new DateTimeFormatterBuilder()
                                            .appendPattern("yyyy-MM")
                                            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                                            .toFormatter(Locale.US);
        DateTimeFormatter formatter3 = new DateTimeFormatterBuilder()
                                            .appendPattern("MMM dd")
                                            .parseDefaulting(ChronoField.YEAR, 2001)
                                            .toFormatter(Locale.US);
        timeDemo.testDate("2018-05-05", formatter1);
        timeDemo.testDate("2020-09", formatter2);
        timeDemo.testDate("Jun 23", formatter3);

    }
}