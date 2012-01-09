package net.praqma.util.debug;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import net.praqma.util.debug.Logger.LogLevel;
import net.praqma.util.debug.appenders.Appender;

import org.junit.Before;
import org.junit.Test;

public class LoggerTest {
	
	private Logger logger = Logger.getLogger();;
	
	@Before
	public void initLogger() {
		Logger.setMinLogLevel( LogLevel.DEBUG );
		Logger.enable();
	}

	@Test
	public void testEnabledConstructor() throws IOException {
		assertNotNull( logger );
	}
	
	@Test
	public void testEnabled() throws IOException {
		Writer out = new StringWriter();
		Appender app = new Appender( new PrintWriter( out ) );
		app.setTemplate( "%message" );
		app.setMinimumLevel( LogLevel.DEBUG );
		Logger.addAppender( app );
		
		logger.debug( "hey" );
		assertEquals( "hey", out.toString() );
	}
	
	@Test
	public void testEnabled2() throws IOException {
		Writer out = new StringWriter();
		Appender app = new Appender( new PrintWriter( out ) );
		app.setTemplate( "%message" );
		app.setMinimumLevel( LogLevel.DEBUG );
		Logger.addAppender( app );
		
		Logger.disable();
		
		logger.debug( "hey" );
		assertFalse( out.toString().equals( "hey" ) );
	}
	
	@Test
	public void testGlobalLogLevel() throws IOException {
		Writer out = new StringWriter();
		Appender app = new Appender( new PrintWriter( out ) );
		app.setTemplate( "%message" );
		app.setMinimumLevel( LogLevel.DEBUG );
		Logger.addAppender( app );
		
		Logger.setMinLogLevel( LogLevel.INFO );
		
		logger.debug( "hey" );
		assertFalse( out.toString().equals( "hey" ) );
	}
	
	@Test
	public void testGlobalLogLevel2() throws IOException {
		Writer out = new StringWriter();
		Appender app = new Appender( new PrintWriter( out ) );
		app.setTemplate( "%message" );
		app.setMinimumLevel( LogLevel.DEBUG );
		Logger.addAppender( app );
				
		Logger.setMinLogLevel( LogLevel.INFO );
		
		logger.info( "hey" );
		assertTrue( out.toString().equals( "hey" ) );
	}
	
	
	@Test
	public void testLocalogLevel() throws IOException {
		Writer out = new StringWriter();
		Appender app = new Appender( new PrintWriter( out ) );
		app.setTemplate( "%message" );
		app.setMinimumLevel( LogLevel.DEBUG );
		Logger.addAppender( app );
		
		logger.debug( "hey" );
		assertTrue( out.toString().equals( "hey" ) );
	}
	
	@Test
	public void testLocalogLevel2() throws IOException {
		Writer out = new StringWriter();
		Appender app = new Appender( new PrintWriter( out ) );
		app.setTemplate( "%message" );
		app.setMinimumLevel( LogLevel.INFO );
		Logger.addAppender( app );
		
		logger.debug( "hey" );
		assertFalse( out.toString().equals( "hey" ) );
	}
}
