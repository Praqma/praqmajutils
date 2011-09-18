package net.praqma.util.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import net.praqma.util.debug.appenders.Appender;

public class Logger {

	private static final int levelMaxlength = 8;
	

	private static final String filesep = System.getProperty( "file.separator" );
	private static final String linesep = System.getProperty( "line.separator" );

	private static Logger instance = null;
	
	private boolean append = true;
	
	private Date current;
	
	private static String filename = "logger_";
	
	private File loggerPath;
	private File loggerFile;

	private static SimpleDateFormat datetimeformat  = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	private static SimpleDateFormat fileformat = new SimpleDateFormat( "yyyyMMdd" );
	private static SimpleDateFormat timeformat = new SimpleDateFormat( "HH:mm:ss" );
	private static SimpleDateFormat dateformat = new SimpleDateFormat( "yyyy-MM-dd" );
	
	private static List<Appender> appenders = new ArrayList<Appender>();



	private FileWriter fw;
	private PrintWriter out;
	
	public enum LogLevel {
		DEBUG,
		INFO,
		WARNING,
		ERROR,
		FATAL
	}
	
	private Logger() {
		
	}
	
	public static Logger getLogger() {
		if( instance == null ) {
			instance = new Logger();
		}
		
		return instance;
	}
	
	private boolean initialize() {
		/* CWD */
		if( loggerPath == null ) {
			loggerPath = new File( System.getProperty("user.dir") );
		}
		
		String format = instance.fileformat.format( new Date() );
		loggerFile = new File( loggerPath, filename + format + ".log" );
		
		/* Existence + creation */
		if( !loggerPath.exists() ) {
			boolean created = false;
			try {
				created = loggerFile.mkdirs();
			} catch (Exception e) {
				created = false;
			}

			if( !created ) {
				return false;
			}
		}
		
		if( fw != null ) {
			try {
				fw.close();
				out.close();
			} catch (IOException e) {
				System.err.println( "Could not close file writer and/or buffered writer." );
			}
		}

		try {
			fw = new FileWriter( loggerFile, append );

		} catch (IOException e) {
			System.err.println( "Could not create logger. Quitting!" );
			System.exit( 1 );
		}

		out = new PrintWriter( fw );
		
		return true;
	}
	
	public static void addAppender( Appender appender ) {
		appenders.add( appender );
	}
	
	public static void removeAppender( Appender appender ) {
		System.out.println( "I HAVE " + appenders.contains( appender ) );
		appenders.remove( appender );
		appender.getOut().close();
		System.out.println( "Closing " + appender );
	}

	public static void setFilename( String filename1 ) {
		filename = filename1;
	}
	
	
	public String objectToString( Object t ) {
		if( t instanceof Throwable ) {
			Writer sw = new StringWriter();
			PrintWriter pw = new PrintWriter( sw );
			( (Throwable) t ).printStackTrace( pw );

			return sw.toString();
		} else {
			return String.valueOf( t );
		}
	}
	
	public void log( Object message ) {
		log( message, LogLevel.INFO, 3 );
	}
	
	public void debug( Object message ) {
		log( message, LogLevel.DEBUG, 3 );
	}
	
	public void info( Object message ) {
		log( message, LogLevel.INFO, 3 );
	}
	
	public void warning( Object message ) {
		log( message, LogLevel.WARNING, 3 );
	}
	
	public void error( Object message ) {
		log( message, LogLevel.ERROR, 3 );
	}
	
	public void fatal( Object message ) {
		log( message, LogLevel.FATAL, 3 );
	}
	
	public void log( Object message, LogLevel level ) {
		log( message, level, 3 );
	}
	
	private String parseTemplate( Map<String, String> keywords, String template ) {
		Set<String> keys = keywords.keySet();
		for( String key : keys ) {
			//System.out.println( key + "=" + keywords.get( key ) );
			try {
				template = template.replaceAll( key, keywords.get( key ) );
			} catch( Exception e ) {
				e.printStackTrace();
			}
		}
		
		return template;
	}
	
	public void redirect( InputStream input ) {
		BufferedReader in = new BufferedReader( new InputStreamReader( input ) );
		String line = "";
		try {
			while( ( line = in.readLine() ) != null ) {
				writeAppenders( line );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write a specific message to appenders
	 * @param message
	 * @param level
	 */
	private void writeAppenders( String message ) {
		for( Appender a : appenders ) {
			a.getOut().write( message );
			a.getOut().flush();
		}
	}
	
	private void log( Object message, LogLevel level, int depth ) {
		Date now = new Date();
		
		if( current == null ) {
			instance.initialize();
		}
		
		Map<String, String> keywords = new HashMap<String, String>();
		
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		
		keywords.put( "%class", stack[depth].getClassName() );
		keywords.put( "%stack", stack[depth].getClassName() + "::" + stack[depth].getMethodName() + "," + stack[depth].getLineNumber() );
		keywords.put( "%line", stack[depth].getLineNumber()+"" );
		keywords.put( "%datetime", datetimeformat.format( now ) );
		keywords.put( "%date", dateformat.format( now ) );
		keywords.put( "%time", timeformat.format( now ) );
		if( level != null ) {
			keywords.put( "%level", level.toString() );
			keywords.put( "%space", new String( new char[Logger.levelMaxlength - level.toString().length()] ).replace( "\0", " " ) );
		} else {
			keywords.put( "%level", "" );
			keywords.put( "%space", new String( new char[Logger.levelMaxlength] ).replace( "\0", " " ) );
		}
		keywords.put( "%message", Matcher.quoteReplacement( objectToString( message ) ) );
		keywords.put( "%newline", linesep );

		/* Writing */
		for( Appender a : appenders ) {
			if(!a.isEnabled() || a.getMinimumLevel().ordinal() > level.ordinal()) {
				continue;
			}
			
			String finalmsg = parseTemplate( keywords, a.getTemplate() );
			if( !a.onBeforeLogging() ) {
				continue;
			}
			
			a.getOut().write( finalmsg );
			a.getOut().flush();
		}
	}
}
