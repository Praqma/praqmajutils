package net.praqma.util;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class StopWatchTest {

	@Test
	public void testGet() {
		StopWatch sw = new StopWatch();
		assertNotNull( sw );
	}

	@Test
	public void testStart() {
		StopWatch sw = new StopWatch();
		sw.start();
		assertTrue( sw.getStartTime() > 0 );
	}

	@Test
	public void testStop() {
		StopWatch sw = new StopWatch();
		sw.start();
		sw.stop();
		assertTrue( sw.getEndTime() > 0 );
	}

	@Test
	public void testGetTime() {
		StopWatch sw = new StopWatch();
		sw.start();
		sw.stop();
		assertTrue( sw.getTime() > 0 );
	}

	@Test
	public void testReset() {
		StopWatch sw = new StopWatch();
		sw.start();
		sw.stop();
		sw.reset();
		assertTrue( sw.getStartTime() == 0 );
		assertTrue( sw.getEndTime() == 0 );
	}

	@Test
	public void testGetSeconds() {
		StopWatch sw = new StopWatch();
		sw.start();
		sw.stop();

		assertTrue( sw.getSeconds() > 0 );
	}

	@Test
	public void testToSeconds() {
		StopWatch sw = new StopWatch();
		sw.start();
		for( int i = 0; i < 1000000; i++ ) {

		}
		sw.stop();

		long t = sw.getTime();

		assertTrue( StopWatch.toSeconds( t, 1000000 ) > 0 );
	}
	
	@Test
	public void multipleTasks() throws InterruptedException {
		StopWatch sw = new StopWatch();
		
		sw.start( "title1" );
		Thread.sleep( 100 );
		sw.stop();
		
		sw.start( "title2" );
		Thread.sleep( 100 );
		sw.stop();
		
		System.out.println( sw.print( 1000 ) );
		
		assertNotNull( sw );
	}
	
	@Test
	public void multipleTasks2() throws InterruptedException {
		StopWatch sw = new StopWatch();
		
		sw.start( "title1" );
		Thread.sleep( 100 );
		sw.stop();
		
		sw.start( "title2" );
		Thread.sleep( 100 );
		sw.stop();
		
		Thread.sleep( 100 );
		
		sw.start( "title3" );
		Thread.sleep( 100 );
		sw.stop();
		
		System.out.println( sw.print( 1000 ) );
		
		assertNotNull( sw );
	}

}
