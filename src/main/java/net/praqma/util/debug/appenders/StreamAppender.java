package net.praqma.util.debug.appenders;

import java.io.OutputStream;
import java.io.PrintWriter;

public class StreamAppender extends Appender {
	public OutputStream outstream;
	
	public StreamAppender( OutputStream outstream ) {
		super( new PrintWriter( outstream ) );
		this.outstream = outstream;
	}	
}
