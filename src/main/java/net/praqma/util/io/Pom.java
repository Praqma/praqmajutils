package net.praqma.util.io;

import java.io.File;
import java.io.IOException;

import net.praqma.util.xml.XML;

public class Pom extends XML
{

	public Pom( File pom ) throws IOException
	{
		super( pom );
	}
	
	public String getVersion()
	{
		String version = getFirstElement( "version" ).getTextContent();
		
		return version;
	}

}
