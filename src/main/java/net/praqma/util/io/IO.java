package net.praqma.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class IO
{
	public static boolean deleteDirectory( File directory )
	{
		if ( directory.isDirectory() )
		{
			String[] elements = directory.list();
			for( int i = 0 ;  i < elements.length ; i++ ) 
			{
				boolean success = deleteDirectory( new File( directory, elements[i] ) ); 
				if ( !success ) return false;
			} 
		} 
		
		return directory.delete();
	}
	
	
	public static String streamToString( InputStream is ) throws IOException
	{
		if( is != null )
		{
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try
			{
				Reader reader = new BufferedReader( new InputStreamReader( is ) );
				int n;
				while( ( n = reader.read( buffer ) ) != -1 )
				{
					writer.write( buffer, 0, n );
				}
			}
			finally
			{
				is.close();
			}
			return writer.toString();
		}
		else
		{
			return "";
		}
	}
}
