package net.praqma.util.debug.appenders;

import java.io.PrintWriter;

import edu.umd.cs.findbugs.annotations.*;
import net.praqma.util.io.NullOutputStream;

@SuppressFBWarnings(value = "DM_DEFAULT_ENCODING", justification = "It's ok for us to rely on def. encoding here")
public class NullAppender extends Appender {
	public static final NullOutputStream outstream = new NullOutputStream();
	
	public NullAppender() {
		super( new PrintWriter( outstream ) );		
	}
}
