package utilities;

import java.time.LocalDate;

/**
 * Holds commonly used methods that can be accessed globally.
 */
public class Util {
    /**
     * Calls the overloaded getMonday method with the current date as a parameter.
     *
     * @return the date of the week we calculate Monday for
     */
    public static LocalDate getMonday() {
        return Util.getMonday(LocalDate.now());
    }

    /**
     * Calculates the date of Monday that is within the week specified by the date parameter.
     *
     * @param current the date of Monday
     * @return the date of the week we calculate Monday for
     */
    public static LocalDate getMonday(LocalDate current) {
        LocalDate monday;
        if (current.getDayOfWeek().getValue() > 1) {
            int daysSubtracted = current.getDayOfWeek().getValue() - 1;
            monday = current.minusDays(daysSubtracted);
        } else monday = current;

        return monday;
    }

    /**
     * Converts the String representation of the month to its relevant Integer form.
     *
     * @param month String representation of the month e.g. "January"
     * @return Integer representation of the month e.g. 1 (for the parameter "January"). Zero if invalid
     */
    public static int monthStringToMonthInt(String month) {
        switch (month.toLowerCase()) {
            case "january":
                return 1;
            case "february":
                return 2;
            case "march":
                return 3;
            case "april":
                return 4;
            case "may":
                return 5;
            case "june":
                return 6;
            case "july":
                return 7;
            case "august":
                return 8;
            case "september":
                return 9;
            case "october":
                return 10;
            case "november":
                return 11;
            case "december":
                return 12;
        }

        return 0;
    }
}
