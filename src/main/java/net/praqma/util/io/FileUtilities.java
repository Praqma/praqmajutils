package net.praqma.util.io;

import java.io.*;

/**
 * @author cwolfgang
 *         Date: 06-03-13
 *         Time: 12:10
 */
public abstract class FileUtilities {
    private FileUtilities() {}

    public static String getContent( File file ) throws FileNotFoundException {
        return toString( new FileInputStream( file ) );
    }

    /**
     * Convert {@link InputStream} to {@link String}
     */
    public static String toString( InputStream is ) {
        java.util.Scanner s = new java.util.Scanner( is ).useDelimiter( "\\A" );
        return s.hasNext() ? s.next() : "";
    }
}
