package net.praqma.util.debug.appenders;

import java.io.PrintWriter;

import net.praqma.util.io.NullOutputStream;

public class NullAppender extends Appender {
	public static NullOutputStream outstream = new NullOutputStream();
	
	public NullAppender() {
		super( new PrintWriter( outstream ) );		
	}
}
