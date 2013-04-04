package net.praqma.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

public class LoggingUtil {
    private LoggingUtil() {}

    public static void changeLoggerFormat( ) {
        changeLoggerFormat( Logger.getLogger( "" ), new PraqmaticLogFormatter() );
    }

    public static void changeLoggerFormat( Formatter formatter ) {
        changeLoggerFormat( Logger.getLogger( "" ), formatter );
    }

    public static void changeLoggerFormat( Logger logger, Formatter formatter ) {
        for( Handler h : logger.getHandlers() ) {
            h.setFormatter( formatter );
        }
    }

    public static void changeLoggerLevel( Level level ) {
        changeLoggerLevel( Logger.getLogger( "" ), level );
    }

    public static void changeLoggerLevel( Logger logger, Level level ) {
        logger.setLevel( level );

        for( Handler h : logger.getHandlers() ) {
            h.setLevel( level );
        }
    }

    public static void setPraqmaticHandler( Level level, String ... names ) {
        setPraqmaticHandler( level, Arrays.asList( names ) );
    }

    public static void setPraqmaticHandler( Level level, List<String> names ) {
        setPraqmaticHandler( level, names, PraqmaticLogFormatter.NORMAL_FORMAT );
    }

    public static void setPraqmaticHandler( Level level, List<String> names, String format ) {
        removeRootHandlers();

        PraqmaticLogHandler h = new PraqmaticLogHandler( System.out, new PraqmaticLogFormatter( format ) );
        h.addTargets( level, names );
        h.setLevel( Level.ALL );
    }

    public static void setPraqmaticHandler( Level level, List<String> names, File outputFile ) throws FileNotFoundException {
        removeRootHandlers();

        OutputStream out = new FileOutputStream( outputFile );

        PraqmaticLogHandler h = new PraqmaticLogHandler( out, new PraqmaticLogFormatter() );
        h.addTargets( level, names );
        h.setLevel( Level.ALL );
    }

    public static void removeRootHandlers() {
        removeHandlers( Logger.getLogger( "" ) );
    }

    public static void removeHandlers( Logger logger ) {
        for( Handler h : logger.getHandlers() ) {
            logger.removeHandler( h );
            h.close();
        }
    }

    public static void setHandler( Handler handler ) {
        setHandler( Logger.getLogger( "" ), handler );
    }

    public static void setHandler( Logger logger, Handler handler ) {
        logger.addHandler( handler );
    }



    public static class LoggerNameFilter implements Filter {

        private List<String> names = new LinkedList<String>();

        public LoggerNameFilter() { }

        public LoggerNameFilter( Collection<String> c ) {
            names.addAll( c );
        }

        public LoggerNameFilter add( String name ) {
            this.names.add( name );

            return this;
        }

        @Override
        public boolean isLoggable( LogRecord record ) {
            System.out.println( "YAY" );
            return names.contains( record.getLoggerName() );
        }

        @Override
        public String toString() {
            return "Names " + names;
        }
    }
}
