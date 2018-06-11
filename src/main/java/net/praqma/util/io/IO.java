package net.praqma.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class IO {
	public static boolean deleteDirectory( File directory ) {
		if( directory != null && directory.isDirectory() ) {
			String[] elements = directory.list();
			if(elements != null) {
				for (int i = 0; i < elements.length; i++) {
					boolean success = deleteDirectory(new File(directory, elements[i]));
					if (!success) return false;
				}
			}
		}
		if(directory == null) {
			return false;
		}
		return directory.delete();
	}

	public static String streamToString( InputStream is ) throws IOException {
		if( is != null ) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader( new InputStreamReader( is, "UTF-8" ) );
				int n;
				while( ( n = reader.read( buffer ) ) != -1 ) {
					writer.write( buffer, 0, n );
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
	public static List<String> streamToStrings( InputStream is ) throws IOException {
		if( is != null ) {
			List<String> lines = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader( new InputStreamReader( is, "UTF-8" ) );
				String line = "";
				while( ( line = reader.readLine() ) != null ) {
					lines.add( line );
				}
			} finally {
				is.close();
			}
			return lines;
		} else {
			return null;
		}
	}
}
