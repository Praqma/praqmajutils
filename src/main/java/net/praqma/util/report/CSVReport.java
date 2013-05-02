package net.praqma.util.report;

import java.io.PrintWriter;
import java.util.List;

/**
 * @author cwolfgang
 */
public class CSVReport extends Report {
    @Override
    public void generateHeader( PrintWriter out ) {
        /* no op */
    }

    @Override
    public void generateRow( PrintWriter out, List<String> row ) {
        int i = 0;
        for(  ; i < ( row.size() - 1 ) ; i++ ) {
            out.print( row.get( i ) + ", " );
        }

        out.println( row.get( i ) );
    }

    @Override
    public void generateFooter( PrintWriter out ) {
        /* no op */
    }
}
