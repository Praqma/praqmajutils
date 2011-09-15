package net.praqma.util.debug.appenders;

import java.io.FileWriter;
import java.io.PrintWriter;

public class FileAppender extends Appender {
	public FileWriter fw;
	
	public FileAppender( FileWriter fout ) {
		super( new PrintWriter( fout ) );
		this.fw = fout;
	}
}
