package net.praqma.util.report;

import edu.umd.cs.findbugs.annotations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author cwolfgang
 */
@SuppressFBWarnings("DM_DEFAULT_ENCODING")
public abstract class Report {

    private static Logger logger = Logger.getLogger( Report.class.getName() );

    protected List<List<String>> rows = new ArrayList<List<String>>();

    protected File output;
    protected Writer out;

    public Report addRow( List<String> row ) {
        rows.add( row );

        return this;
    }

    public Report setOutputFile( File outputFile ) {
        this.output = outputFile;

        return this;
    }

    public Report setWriter( Writer out ) {
        this.out = out;

        return this;
    }

    public void generate() throws IOException {
        logger.fine( "Generating report" );

        PrintWriter thisOut;

        if( out == null ) {
            if( output != null ) {
                thisOut = new PrintWriter( output );
            } else {
                throw new IllegalStateException( "No output destination given" );
            }
        } else {
            thisOut = new PrintWriter( out );
        }

        logger.fine( "Output is " + thisOut );

        generateHeader( thisOut );

        for( List<String> row : rows ) {
            generateRow( thisOut, row );
        }

        generateFooter( thisOut );

        out.close();
    }

    public abstract void generateHeader( PrintWriter out );
    public abstract void generateRow( PrintWriter out, List<String> row );
    public abstract void generateFooter( PrintWriter out );
}
