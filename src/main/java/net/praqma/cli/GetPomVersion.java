package net.praqma.cli;

import java.io.File;
import java.io.IOException;

import net.praqma.util.io.Pom;
import net.praqma.util.option.Option;
import net.praqma.util.option.Options;


/*
 * Usage
 * java -classpath COOL-0.1.5.jar net.praqma.cli.BuildNumber -f stamptest.txt -m 12 -p 1234 -s 22221 --minor 22b
 * 
 * 
 */

public class GetPomVersion
{
	
	public static void main( String[] args ) throws IOException
	{
		try
		{
			run( args );
		}
		catch( IOException e )
		{
			throw e;
		}
	}
	
	public static void run( String[] args ) throws IOException
	{
		Options o = new Options( "1" );
		
		Option ofile = new Option( "file", "f", true, 1, "The pom.xml file" );
		
		o.setOption( ofile );
		
		o.setDefaultOptions();
		
		o.setSyntax( "GetPomVersion -f file" );
		o.setDescription( "Retrieve the version number from a pom.xml file" );
		
		o.parse( args );
		
		try
		{
			o.checkOptions();
		}
		catch ( Exception e )
		{
			System.err.println( "Incorrect option: " + e.getMessage() );
			o.display();
			System.exit( 1 );
		}
		
		File file = new File( ofile.getString() );
		
		String version = "";
		
		try
		{
			net.praqma.util.io.Pom pom = new Pom( file );
			version = pom.getVersion();
		}
		catch ( IOException e )
		{
			System.err.println( "Error reading " + file );
			System.exit( 1 );
		}
		
		if( o.verbose() )
		{
			o.print();
		}

		System.out.println( version );
	}
}
