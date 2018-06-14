package net.praqma.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Manifest {
	Map<String, String> properties = new HashMap<String, String>();

	public Manifest( File f ) throws IOException {
		FileReader fr = new FileReader( f );
		BufferedReader br = new BufferedReader( fr );

		String line = "";

		while( ( line = br.readLine() ) != null ) {
			String[] vs = line.split( ":" );
			try {
				properties.put( vs[0].trim(), vs[1].trim() );
			} catch( Exception e ) {
				/* Skipping field */
			}
		}
	}

	public Manifest( InputStream is ) throws IOException {
		String m = net.praqma.util.io.IO.streamToString( is );

		String[] lines = m.split( System.getProperty( "line.separator" ) );

		for( String line : lines ) {
			String[] vs = line.split( ":" );
			try {
				properties.put( vs[0].trim(), vs[1].trim() );
			} catch( ArrayIndexOutOfBoundsException e ) {
				/* Does not put this in... */
			}
		}
	}

	public String get( String key ) {
		return properties.get( key );
	}

	public String toString() {
		return net.praqma.util.structure.Printer.mapPrinterToString( properties );
	}
}
