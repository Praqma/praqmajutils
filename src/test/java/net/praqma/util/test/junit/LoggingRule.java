package net.praqma.util.test.junit;

import net.praqma.logging.LoggingUtil;
import net.praqma.logging.PraqmaticFormatter;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;

public class LoggingRule implements TestRule {

    private Level level;
    private List<Handler> handlers;
    private List<String> loggerNames = new LinkedList<String>();

    public LoggingRule( Level level ) {
        this.level = level;
    }

    public LoggingRule( Level level, String ... loggerNames ) {
        this.level = level;

        for( String ln : loggerNames ) {
            this.loggerNames.add( ln );
        }
    }

    private void before() {
        LoggingUtil.changeLoggerLevel( level );
        LoggingUtil.changeLoggerFormat( new PraqmaticFormatter() );

        for( String ln : this.loggerNames ) {

        }
    }

    private void after() {

    }

    @Override
    public Statement apply( final Statement base, final Description description ) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                System.out.println( " ===== Setting up logger =====" );
                before();
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }
}
