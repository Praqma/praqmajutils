package net.praqma.util.execute;

import java.io.File;

import org.w3c.dom.Element;

import net.praqma.util.xml.XML;

public class Recorder extends XML {
	public File output;
	
	public Recorder() {
		super( "commands" );
		output = new File( "commands.xml" );
		System.out.println( output.getAbsolutePath() );
	}
	
	public void addCommand( String command, int exitValue, File context, String result ) {
		Element e = addElement( "command" );
		e.setAttribute( "command", command );
		e.setAttribute( "context", context + "" );
		e.setAttribute( "exitValue", exitValue + "" );
		e.setTextContent( "<![CDATA" + result + "]]>" );
		
		saveState( output );
	}
}
