package dev.wither.orca.misc.util.time.setting;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum TimePeriodLength {

    YEAR(31536000, "y", "year", "years", "year(s)", "ye"),
    MONTH(2592000, "month", "months", "month(s)", "mon", "mo"),
    WEEK(604800, "w", "week", "weeks", "week(s)", "we"),
    DAY(86400, "d", "day", "days", "day(s)", "da"),
    HOUR(3600, "h", "hour", "hours", "hour(s)", "ho"),
    MINUTE(60, "m", "min", "mins", "minute", "minutes", "minute(s)", "min(s)", "mi"),
    SECOND(1, "s", "sec", "seconds", "secs", "second(s)", "sec(s)", "se");

    @Getter private final List<String> matches;
    @Getter private final int time;

    TimePeriodLength(int time, String... matches) {

        this.matches = Arrays.asList(matches);
        this.time = time;

    }

    public static TimePeriodLength getLength(String match) {

        for (String string : YEAR.matches) {

            if (string.equalsIgnoreCase(match)) return YEAR;

        }
        for (String string : MONTH.matches) {

            if (string.equalsIgnoreCase(match)) return MONTH;

        }
        for (String string : WEEK.matches) {

            if (string.equalsIgnoreCase(match)) return WEEK;

        }
        for (String string : DAY.matches) {

            if (string.equalsIgnoreCase(match)) return DAY;

        }for (String string : HOUR.matches) {

            if (string.equalsIgnoreCase(match)) return HOUR;

        }
        for (String string : MINUTE.matches) {

            if (string.equalsIgnoreCase(match)) return MINUTE;

        }
        for (String string : SECOND.matches) {

            if (string.equalsIgnoreCase(match)) return SECOND;

        }
        return null;

    }

}
