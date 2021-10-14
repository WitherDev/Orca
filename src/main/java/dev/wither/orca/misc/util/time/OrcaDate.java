package dev.wither.orca.misc.util.time;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * OrcaDate is the Orca alternative to Java's built-in date and is used by other time-related classes and methods in Orca.
 *
 */
public class OrcaDate {

    @Getter @Setter private Date date;

    public OrcaDate() {

        date = new Date();

    }

    public OrcaDate(long time) {

        date = new Date(time);

    }

    public OrcaDate(Date date) {

        this.date = date;

    }

    /**
     * @return number of seconds since January 1st, 1970 that this OrcaDate object represents.
     */
    public int getSeconds() {

        return (int)(date.getTime()/1000L);

    }

}
