package net.praqma.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class PraqmaticLogFormatter extends Formatter {

    public static final String NORMAL_FORMAT = "{3,date,HH:mm:ss} [{1}]{5} {6}.{7}, {19}: {4} \n{8}";
    public static final String SMALL_FORMAT = "{3,date,HH:mm:ss} [{1}]{5}: {4} \n{8}";
    public static final String TINY_FORMAT = "[{1}]{5} {4} \n{8}";

    public String format = "";

    private static final int width = 8;
    private boolean enableLineNumbers = true;

    private MessageFormat messageFormat = new MessageFormat( NORMAL_FORMAT );

    public PraqmaticLogFormatter() {}

    public PraqmaticLogFormatter( String format ) {
        messageFormat = new MessageFormat( format );
        this.format = format;
    }

    public void setFormat( String format ) {
        this.messageFormat = new MessageFormat( format );
    }

    @Override
    public String format( LogRecord record ) {

        Object[] args = new Object[20];
        args[0] = record.getLoggerName();
        args[1] = record.getLevel();
        args[2] = Thread.currentThread().getName();
        args[3] = new Date( record.getMillis() );
        args[4] = record.getMessage();

        int w = width - record.getLevel().getName().length();
        if( w > 0 ) {
            args[5] = new String( new char[w] ).replace( "\0", " " );
        } else {
            args[5] = "";
        }

        args[6] = record.getSourceClassName();
        args[7] = record.getSourceMethodName();
        if( record.getThrown() != null ) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                args[8] = sw.toString();
            } catch (Exception ex) {
                args[8] = "?";
            }
        } else {
            args[8] = "";
        }


        if( enableLineNumbers ) {
            try {
                args[19] = Thread.currentThread().getStackTrace()[8].getLineNumber();
            } catch( Exception e ) {
                args[19] = -1;
            }
        } else {
            args[19] = "?";
        }

        return messageFormat.format( args );
    }

}
