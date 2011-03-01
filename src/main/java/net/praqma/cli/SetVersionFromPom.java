package net.praqma.cli;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.praqma.util.io.BuildNumberStamper;
import net.praqma.util.io.Pom;
import net.praqma.util.option.Option;
import net.praqma.util.option.Options;


public class SetVersionFromPom
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
		Options o = new Options( "123" );
		
		Option opom      = new Option( "pom", "p", true, 1, "The pom.xml file" );
		Option ofile     = new Option( "file", "f", true, 1, "The version file to be stamped" );
		Option osequence = new Option( "sequence", "s", false, 1, "An optional sequence for the version" );
		
		o.setOption( opom );
		o.setOption( ofile );
		o.setOption( osequence );
		
		o.setDefaultOptions();
		
		o.setSyntax( "SetVersionFromPom -p pom.xml -v Version.java -s 101" );
		o.setDescription( "Retrieve the version number from a pom.xml file and stamp it into a version file." );
		
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
		
		File mypom = new File( opom.getString() );
		File version = new File( ofile.getString() );
		
		String v = "";
		
		try
		{
			Pom pom = new Pom( mypom );
			v = pom.getVersion();
		}
		catch ( IOException e )
		{
			System.err.println( "Error reading " + mypom );
			System.exit( 1 );
		}
		
		BuildNumberStamper stamp = null;
		
		try
		{
			stamp = new BuildNumberStamper( version );
		}
		catch ( IOException e )
		{
			System.err.println( "Could not create temporary file" );
			System.exit( 1 );
		}
		
		try
		{
			String[] vs = v.split( "\\." );
			String sequence = osequence.used ? osequence.getString() : "XX";
			/* One level, patch */
			switch( vs.length )
			{
			case 1:
				stamp.stampIntoCode( "0", "0", vs[0], sequence );
				break;
				
			case 2:
				stamp.stampIntoCode( "0", vs[0], vs[1], sequence );
				break;
				
			case 3:
				stamp.stampIntoCode( vs[0], vs[1], vs[2], sequence );
				break;
				
			default:
				System.err.println( "Unknown format: " + vs.length );
				System.exit( 1 );
			}
			
		}
		catch ( IOException e )
		{
			System.err.println( "Could not stamp " + version );
			System.exit( 1 );
		}
		
		if( o.verbose() )
		{
			o.print();
		}

		System.out.println( v );
	}
}
