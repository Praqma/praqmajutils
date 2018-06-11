package net.praqma.util.debug.appenders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.umd.cs.findbugs.annotations.*;
import net.praqma.util.debug.Logger.LogLevel;

@SuppressFBWarnings(value = {"DM_DEFAULT_ENCODING","URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD"},
		justification = "It's ok for us to rely on def. encoding here")
public class FileAppender extends Appender {
	public FileWriter fw;
	public File file;
	
	public FileAppender( FileWriter fout ) {
		super( new PrintWriter( fout ) );
		this.fw = fout;
	}
	
	public FileAppender( FileWriter fout, LogLevel level ) {
		super( new PrintWriter( fout ), level );
		this.fw = fout;
	}
	
	public FileAppender( File file ) throws IOException {
		super( new PrintWriter( file, "UTF-8" ) );
		this.file = file;
	}
	
	public FileAppender( File file, LogLevel level ) throws IOException {
		super( new PrintWriter( file, "UTF-8" ), level );
		this.file = file;
	}
}
