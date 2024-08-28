import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;


class SendDateInsurance {

    private static final List<LocalDate> HOLIDAYS = Arrays.asList(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            LocalDate.of(2024, 1, 3),
            LocalDate.of(2024, 1, 4),
            LocalDate.of(2024, 1, 5),
            LocalDate.of(2024, 1, 6),
            LocalDate.of(2024, 1, 7),
            LocalDate.of(2024, 1, 8),
            LocalDate.of(2024, 3, 8),
            LocalDate.of(2024, 4, 29),
            LocalDate.of(2024, 4, 30),
            LocalDate.of(2024, 5, 1),
            LocalDate.of(2024, 5, 9),
            LocalDate.of(2024, 5, 10),
            LocalDate.of(2024, 6, 12),
            LocalDate.of(2024, 11, 4),
            LocalDate.of(2024, 12, 30),
            LocalDate.of(2024, 12, 31)


    );

    public static LocalDateTime getSendingDate() {

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        int[] sendingDays = {1, 10, 20};

        for (int sendingDay : sendingDays) {
            if (day < sendingDay || (day == sendingDay && now.toLocalTime().isBefore(LocalTime.of(18, 0)))) {
                LocalDate date = LocalDate.of(year, month, sendingDay);
                LocalDateTime sendingDateTime = LocalDateTime.of(date, LocalTime.of(18, 0));
                sendingDateTime = adjustForWeekendOrHoliday(sendingDateTime);
                return sendingDateTime;
            }
        }

        LocalDate nextMonth = LocalDate.of(year, month, 1).plusMonths(1);
        LocalDateTime nextDateTime = LocalDateTime.of(nextMonth.withDayOfMonth(sendingDays[0]), LocalTime.of(18, 0));
        nextDateTime = adjustForWeekendOrHoliday(nextDateTime);
        return nextDateTime;

    }

    private static LocalDateTime adjustForWeekendOrHoliday(LocalDateTime sendingDateTime) {
        while (isWeekendOrHoliday(sendingDateTime.toLocalDate())) {
            sendingDateTime = sendingDateTime.minusDays(1).with(LocalTime.of(18, 0));
        }
        return sendingDateTime;
    }

    private static boolean isWeekendOrHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        if (HOLIDAYS.contains(date)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        LocalDateTime nextSendingDate = getSendingDate();
        System.out.println("Следующая дата отправки: " + nextSendingDate );
    }


}



