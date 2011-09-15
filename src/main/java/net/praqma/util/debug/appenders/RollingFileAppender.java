package net.praqma.util.debug.appenders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RollingFileAppender extends Appender {
	public FileWriter fw;
	private File file;
	private File currentFile;
	private Calendar current;
	
	private SimpleDateFormat fileformat = new SimpleDateFormat( "yyyyMMdd" );
	
	public RollingFileAppender( File file ) {
		super();
		
		this.file = file;
		
		current = Calendar.getInstance();
		current.setTimeInMillis( current.getTimeInMillis() - ( current.getTimeInMillis() % 86400 ) );
		initialize(	current );
	}
		
	private void initialize( Calendar now ) {
		now.setTimeInMillis( now.getTimeInMillis() - ( now.getTimeInMillis() % 86400 ) );
		
		if( now.after( current ) ) {
			File path = file.getParentFile();
			String f = file.getName();
			if( f.contains( "." ) ) {
				String[] fs = f.split( ".", 2 );
				currentFile = new File( path, fs[0] + fileformat.format( now ) + "." + fs[1] );
			} else {
				currentFile = new File( path, f + fileformat.format( now ) );
			}
		}
		
		try {
			fw = new FileWriter( currentFile, true );

		} catch (IOException e) {
			System.err.println( "Could not create logger.!" );
		}

		out = new PrintWriter( fw );
	}
	
	public void onBeforeLogging() {
		initialize( Calendar.getInstance() );
	}
}
