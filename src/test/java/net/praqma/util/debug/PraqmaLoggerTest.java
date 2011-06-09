package net.praqma.util.debug;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import net.praqma.util.debug.PraqmaLogger.Logger;
import net.praqma.util.io.BuildNumberStamperTest;

import org.junit.Test;

public class PraqmaLoggerTest {

	@Test
	public void testGetLoggerBooleanBoolean() {
		Logger logger = PraqmaLogger.getLogger(true, true);
		assertNotNull(logger);
	}

	@Test
	public void testGetLoggerBooleanBoolean_1() {
		Logger logger = PraqmaLogger.getLogger(true, false);
		assertNotNull(logger);
	}

	@Test
	public void testGetLoggerBooleanBoolean_2() {
		Logger logger = PraqmaLogger.getLogger(false, true);
		assertNotNull(logger);
	}

	@Test
	public void testGetLoggerBooleanBoolean_3() {
		Logger logger = PraqmaLogger.getLogger(false, false);
		assertNotNull(logger);
	}

	@Test
	public void testGetLoggerBoolean() {
		Logger logger = PraqmaLogger.getLogger(false);
		assertNotNull(logger);
	}

	@Test
	public void testGetLoggerBoolean_1() {
		Logger logger = PraqmaLogger.getLogger(true);
		assertNotNull(logger);
	}

	@Test
	public void testSetLocalLog() throws IOException {
		Logger logger = PraqmaLogger.getLogger(true);
	    	File file = new File( BuildNumberStamperTest.class.getClassLoader().getResource( "test.log" ).getFile() );
	    	file.delete();
		file.createNewFile();

		logger.setLocalLog(file);
		assertNotNull(logger.getLocalLog());

	}

	@Test
	public void testEnable() {
		Logger logger = PraqmaLogger.getLogger(true);
		logger.enable();
		assertTrue(logger.enabled);

	}

	@Test
	public void testDisable() {
		Logger logger = PraqmaLogger.getLogger(true);
		logger.disable();
		assertTrue(!logger.enabled);

	}

	@Test
	public void testSubscribeAll() {
		Logger logger = PraqmaLogger.getLogger(true);
		logger.subscribeAll();
		assertTrue(logger.all);

	}

	@Test
	public void testUnsubscribeAll() {
		Logger logger = PraqmaLogger.getLogger(true);
		logger.unsubscribeAll();
		assertTrue(!logger.all);
	}
}
