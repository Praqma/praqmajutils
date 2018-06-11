package net.praqma.util.debug.appenders;

import edu.umd.cs.findbugs.annotations.*;

import java.io.PrintWriter;

@SuppressFBWarnings(value = "DM_DEFAULT_ENCODING", justification = "It's ok for us to rely on def. encoding here")
public class ConsoleAppender extends Appender {
	
	public ConsoleAppender() {
		super( new PrintWriter( System.out ) );
		setTemplate( "[%level] %space %message%newline" );
	}	
}
