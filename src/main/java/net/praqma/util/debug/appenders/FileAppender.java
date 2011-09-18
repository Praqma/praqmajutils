package net.praqma.util.debug.appenders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileAppender extends Appender {
	public FileWriter fw;
	
	public FileAppender( FileWriter fout ) {
		super( new PrintWriter( fout ) );
		this.fw = fout;
	}
	
	public FileAppender( File file ) throws IOException {
		super( new PrintWriter( new FileWriter( file, true ) ) );
	}
}
