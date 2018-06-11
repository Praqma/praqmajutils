package net.praqma.util.debug.appenders;

import edu.umd.cs.findbugs.annotations.*;

import java.io.OutputStream;
import java.io.PrintWriter;

@SuppressFBWarnings(value = {"DM_DEFAULT_ENCODING","URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD"},
		justification = "It's ok for us to rely on def. encoding here")
public class StreamAppender extends Appender {
	public OutputStream outstream;
	
	public StreamAppender( OutputStream outstream ) {
		super( new PrintWriter( outstream ) );
		this.outstream = outstream;
	}	
}
