package net.praqma.util.logging;

import net.praqma.logging.LoggingUtil;
import net.praqma.logging.PraqmaticFormatter;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingTest {

    @Test
    public void test() {
        LoggingUtil.changeLoggerLevel( Level.ALL );
        LoggingUtil.changeLoggerFormat( new PraqmaticFormatter() );

        Logger logger = Logger.getLogger( "snade" );
        logger.fine("FINE");
        logger.info("INFO");
    }
}
