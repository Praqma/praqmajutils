package net.praqma.util.execute;

import static org.junit.Assert.*;

import java.io.File;

import net.praqma.util.debug.Logger;
import net.praqma.util.execute.CommandLineInterface.OperatingSystem;

import org.junit.Test;

public class CommandLineTest {

	@Test
	public final void testGetInstance() {
		CommandLine cli = CommandLine.getInstance();
		assertNotNull(cli);
	}
	
	@Test
	public final void testSetLogger() {
		CommandLine cli = CommandLine.getInstance();
		Logger l = Logger.getLogger();
		assertNotNull(cli);
	}

	@Test
	public final void testRunString() {
		CommandLine cli = CommandLine.getInstance();
		CmdResult res = cli.run("java");
		assertNotNull(res);
	}

	/**
	 * AbnormalProcessTerminationException don't ignore
	 */
	@Test
	public final void testAbnormalProcessTerminationException() {
		CommandLine cli = CommandLine.getInstance();
		CmdResult res;
		try {
			res = cli.run("java -something",
					new File(System.getProperty("user.home")), true, false);
		} catch (AbnormalProcessTerminationException e) {
			assertNotNull(e);
			return;
		}
		catch( CommandLineException e )
		{
		    if( cli.getOS() == OperatingSystem.WINDOWS )
		    {
			fail();
		    }
		    else
		    {
			return;
		    }
		}
		fail();
	}
	
	/**
	 * AbnormalProcessTerminationException ignore
	 */
	@Test
	public final void testAbnormalProcessTerminationExceptionIgnore() {
		CommandLine cli = CommandLine.getInstance();
		CmdResult res;
		try {
			res = cli.run("java -something",
					new File(System.getProperty("user.home")), true, true);
		} catch (AbnormalProcessTerminationException e) {
			fail();
			return;
		}
		catch( CommandLineException e )
		{
		    if( cli.getOS() == OperatingSystem.WINDOWS )
		    {
			fail();
		    }
		    else
		    {
			return;
		    }
		}
	}
	
	
	/**
	 * CommandLineException ignore
	 */
	@Test
	public final void testCommandLineException() {
		CommandLine cli = CommandLine.getInstance();
		CmdResult res;
		try {
			res = cli.run("java -something",
					new File(System.getProperty("user.home")+"\\"+Math.random()*100), true, true);
		} catch (CommandLineException e) {
			assertNotNull(e);
			return;
		}
		fail();
	}
	

	@Test
	public final void testRunStringFile() {
		CommandLine cli = CommandLine.getInstance();
		CmdResult res = cli.run("java",
				new File(System.getProperty("user.home")));
		assertNotNull(res);
	}

	@Test
	public final void testRunStringFileBoolean() {
		CommandLine cli = CommandLine.getInstance();
		CmdResult res = cli.run("java",
				new File(System.getProperty("user.home")), false);
		assertNotNull(res);
	}

	@Test
	public final void testRunStringFileBooleanBoolean() {
		CommandLine cli = CommandLine.getInstance();
		CmdResult res = cli.run("java",
				new File(System.getProperty("user.home")), true, false);
		assertNotNull(res);
	}

}
