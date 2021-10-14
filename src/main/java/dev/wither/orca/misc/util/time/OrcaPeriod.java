package dev.wither.orca.misc.util.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.wither.orca.misc.util.time.setting.TimePeriodLength;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrcaPeriod {

    @JsonProperty("seconds")
    @Getter @Setter private int seconds = 0;

    public OrcaPeriod(int seconds) {

        this.seconds = seconds;

    }

    public OrcaPeriod(String readable) {

        List<String> chars = Arrays.asList(readable.split(""));

        StringBuilder value = new StringBuilder();
        StringBuilder length = new StringBuilder();

        int i = 0;
        for (String character : new ArrayList<>(chars)) {

            try {

                int check = Integer.parseInt(character);
                value.append(check);
                chars.remove(i);
                i++;

            } catch (NumberFormatException e) {

                break;

            }

        }

        for (String character : new ArrayList<>(chars)) {

            length.append(character);

        }

        length.trimToSize();

        int intValue = 0;
        int intLength = 0;

        try {

            intValue = Integer.parseInt(value.toString());

        } catch (NumberFormatException e) {

            return;

        }

        TimePeriodLength var = TimePeriodLength.getLength(length.toString().trim());
        if (var == null) return;

        intLength = var.getTime();

        this.seconds = intValue * intLength;

    }

    /**
    * @return humanly worded length of time represented by this object.
     */
    public String format() {

        StringBuilder builder = new StringBuilder();

        int leftover = seconds;

        if (leftover >= 31536000) {

            int val = leftover/31536000;
            leftover -= val*31536000;
            if (val == 1) {

                builder.append(val).append(" year, ");

            } else {

                builder.append(val).append(" years, ");

            }

        }
        if (leftover >= 2592000) {

            int val = leftover/2592000;
            leftover -= val*2592000;
            if (val == 1) {

                builder.append(val).append(" month, ");

            } else {

                builder.append(val).append(" months, ");

            }

        }
        if (leftover >= 604800) {

            int val = leftover/604800;
            leftover -= val*604800;
            if (val == 1) {

                builder.append(val).append(" week, ");

            } else {

                builder.append(val).append(" weeks, ");

            }

        }
        if (leftover >= 86400) {

            int val = leftover/86400;
            leftover -= val*86400;
            if (val == 1) {

                builder.append(val).append(" day, ");

            } else {

                builder.append(val).append(" days, ");

            }

        }
        if (leftover >= 3600) {

            int val = leftover/3600;
            leftover -= val*3600;
            if (val == 1) {

                builder.append(val).append(" hour, ");

            } else {

                builder.append(val).append(" hours, ");

            }

        }
        if (leftover >= 60) {

            int val = leftover/60;
            leftover -= val*60;
            if (val == 1) {

                builder.append(val).append(" minute, ");

            } else {

                builder.append(val).append(" minutes, ");

            }

        }
        if (leftover >= 1) {

            int val = leftover;
            leftover -= val;
            if (val == 1) {

                builder.append(val).append(" second, ");

            } else {

                builder.append(val).append(" seconds, ");

            }

        }

        builder.trimToSize();
        builder.deleteCharAt(builder.length()-1).setCharAt(builder.length()-2, '.');

        return builder.toString();

    }

    /**
     * @return formatted length of time represented by this object, with each word shortened to 1 or 3 letters.
     */
    public String shortFormat() {

        StringBuilder builder = new StringBuilder();

        int leftover = seconds;

        if (leftover >= 31536000) {

            int val = leftover/31536000;
            leftover -= val*31536000;
            builder.append(val).append("y, ");

        }
        if (leftover >= 2592000) {

            int val = leftover/2592000;
            leftover -= val*2592000;
            builder.append(val).append("m, ");

        }
        if (leftover >= 604800) {

            int val = leftover/604800;
            leftover -= val*604800;
            builder.append(val).append("w, ");

        }
        if (leftover >= 86400) {

            int val = leftover/86400;
            leftover -= val*86400;
            builder.append(val).append("d, ");

        }
        if (leftover >= 3600) {

            int val = leftover/3600;
            leftover -= val*3600;
            builder.append(val).append("h, ");

        }
        if (leftover >= 60) {

            int val = leftover/60;
            leftover -= val*60;
            builder.append(val).append("min, ");

        }
        if (leftover >= 1) {

            int val = leftover;
            leftover -= val;
            builder.append(val).append("s, ");

        }

        builder.trimToSize();
        builder.deleteCharAt(builder.length()-1).setCharAt(builder.length()-2, '.');

        return builder.toString();

    }

    /**
     *
     * @param start the first date to be used by the equation.
     * @param end the final date to be used by the equation.
     * @return OrcaTimePeriod that represents the amount of time that passed between the two dates.
     */
    public static OrcaPeriod difference(OrcaDate start, OrcaDate end) {

        return new OrcaPeriod(end.getSeconds() - start.getSeconds());

    }

    /**
     *
     * @param start the first date to be used by the equation.
     * @return OrcaTimePeriod that represents the amount of time passed between now and the starting date.
     */
    public static OrcaPeriod since(OrcaDate start) {

        return difference(start, new OrcaDate());

    }

}
