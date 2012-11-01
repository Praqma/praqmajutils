package net.praqma.util.test.junit;

import net.praqma.logging.LoggingUtil;
import net.praqma.logging.PraqmaticFormatter;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;

public class LoggingRule implements TestRule {

    private Level level;
    private List<Handler> handlers;

    public LoggingRule( Level level ) {
        this.level = level;
    }

    private void before() {
        LoggingUtil.changeLoggerLevel( level );
        LoggingUtil.changeLoggerFormat( new PraqmaticFormatter() );


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
