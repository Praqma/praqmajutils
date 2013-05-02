package net.praqma.util.report;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cwolfgang
 */
public abstract class Report {

    protected List<List<String>> rows = new ArrayList<List<String>>();

    protected File output;
    protected PrintWriter out;

    public Report addRow( List<String> row ) {
        rows.add( row );

        return this;
    }

    public Report setOutputFile( File outputFile ) {
        this.output = outputFile;

        return this;
    }

    public void generate() throws IOException {

        if( output != null ) {
            out = new PrintWriter( output );
        } else {
            throw new IllegalStateException( "No output destination given" );
        }

        generateHeader( out );

        for( List<String> row : rows ) {
            generateRow( out, row );
        }

        generateFooter( out );

        out.close();
    }

    public abstract void generateHeader( PrintWriter out );
    public abstract void generateRow( PrintWriter out, List<String> row );
    public abstract void generateFooter( PrintWriter out );
}
