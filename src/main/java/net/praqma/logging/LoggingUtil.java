package net.praqma.logging;

import java.io.PrintStream;
import java.util.logging.*;

public class LoggingUtil {
    private LoggingUtil() {}

    public static void changeLoggerFormat( Formatter formatter ) {
        changeLoggerFormat( Logger.getLogger( "" ), formatter );
    }

    public static void changeLoggerFormat( Logger logger, Formatter formatter ) {
        for( Handler h : logger.getHandlers() ) {
            h.setFormatter( new PraqmaticFormatter() );
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
}
