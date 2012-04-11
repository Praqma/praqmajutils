package net.praqma.util.execute;

import java.io.File;

import org.junit.Test;

import junit.framework.TestCase;

public class RecorderTest extends TestCase {

	@Test
	public void testRecorder() {
		CommandLine cli = CommandLine.getInstance();
		Recorder recorder = new Recorder();
		cli.setRecorder( recorder );
		
		cli.run( "dir" );
		
		cli.run( "dir", new File( "c:/" ) );
	}
}
