package net.praqma.util.logging;

import net.praqma.util.test.junit.LoggingRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Level;

public class LoggingRuleTest {

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger( LoggingRuleTest.class.getName() );

    @Rule
    public LoggingRule lrule = new LoggingRule( Level.ALL );

    @Test
    public void test() {
        logger.fine("FINE");

        logger.severe("SEEVVEERE");
    }
}
