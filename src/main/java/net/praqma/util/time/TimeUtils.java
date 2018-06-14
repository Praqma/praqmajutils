package net.praqma.util.time;

/**
 * @author cwolfgang
 */
public class TimeUtils {

    private TimeUtils() {}

    public static int SECONDS = 1000;
    public static int MINUTES = SECONDS * 60;
    public static int HOURS = MINUTES * 60;
    public static int DAYS = HOURS * 24;
    public static int WEEKS = DAYS * 7;

    public static class Time {
        public int millis = 0;
        public int seconds = 0;
        public int minutes = 0;
        public int hours = 0;
        public int days = 0;
        public int weeks = 0;
    }

    public static Time getTime( long millis ) {
        Time t = new Time();

        t.days = (int) (millis / DAYS);
        int rest = (int) (millis % DAYS);

        t.hours = rest / HOURS;
        rest = rest % HOURS;

        t.minutes = rest / MINUTES;
        rest = rest % MINUTES;

        t.seconds = rest / SECONDS;

        t.millis = rest % SECONDS;

        return t;
    }

    public static String getTimeString( long millis ) {
        Time t = getTime( millis );

        StringBuilder b = new StringBuilder();

        if( t.days > 0 ) {
            b.append( t.days ).append( "d " );
        }

        if( t.hours > 0 ) {
            b.append( t.hours ).append( "h " );
        }

        if( t.minutes > 0 ) {
            b.append( t.minutes ).append( "m " );
        }

        b.append( t.seconds ).append( "s " ).append( t.millis ).append( "ms" );

        return b.toString();
    }
}
