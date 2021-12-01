package utilities;

import java.time.LocalDate;

public class Util {
    public static LocalDate getMonday() {
        LocalDate now = LocalDate.now();
        LocalDate monday;
        if (now.getDayOfWeek().getValue() > 1) {
            int daysSubtracted = now.getDayOfWeek().getValue() - 1;
            monday = now.minusDays(daysSubtracted);
        } else monday = now;
        return monday;
    }
}
