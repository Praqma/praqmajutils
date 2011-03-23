package net.praqma.util.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.praqma.util.xml.XML;

public class Pom extends XML
{

	public Pom( File pom ) throws IOException
	{
		super( pom );
	}
	
	public Pom( InputStream is )
	{
		super( is );
	}
	
	public String getVersion()
	{
		String version = getFirstElement( "version" ).getTextContent();
		
		return version;
	}

}
