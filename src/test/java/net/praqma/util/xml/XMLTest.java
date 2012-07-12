package net.praqma.util.xml;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class XMLTest {

	@Test
	public void includeAware() throws IOException {
		File include = new File( XMLTest.class.getClassLoader().getResource( "include.xml" ).getFile() );
		XML xml = new XML( include );
		System.out.println( xml.getXML() );
	}
}
