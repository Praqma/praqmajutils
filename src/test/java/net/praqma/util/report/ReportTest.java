package net.praqma.util.report;

import net.praqma.logging.PraqmaticLogFormatter;
import net.praqma.util.test.junit.LoggingRule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author cwolfgang
 */
public class ReportTest {

    public static LoggingRule lrule = new LoggingRule( "net.praqma" ).setFormat( PraqmaticLogFormatter.TINY_FORMAT );
    public static String lineSep = System.getProperty( "line.separator" );

    @Test
    public void test() throws IOException {
        CSVReport r = new CSVReport();

        List<String> row1 = new ArrayList<String>( 2 );
        row1.add( "one" );
        row1.add( "two" );

        Writer out = new StringWriter();
        r.addRow( row1 ).setWriter( out ).generate();

        assertThat( out.toString(), is( "one, two" + lineSep ) );
    }
}
