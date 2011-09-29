package net.praqma.util.execute;

import static org.junit.Assert.*;

import java.io.File;

import junit.framework.TestSuite;

import org.junit.Test;

public class CommandTest extends TestSuite {

	private static CommandLine cli = CommandLine.getInstance();
	
	@Test
	public void testRunCommand() {

		String cmd = "java";

		CmdResult res = cli.run(cmd);

		assertNotNull(res);
	}

	@Test
	public void testRunCommandDir() {
		String cmd = "java";

		CmdResult res = cli.run(cmd,
				new File(System.getProperty("user.home")));

		assertNotNull(res);
	}

	@Test
	public void testRunCommandDirMerge() {
		String cmd = "java";

		CmdResult res = cli.run(cmd,
				new File(System.getProperty("user.home")), true);

		assertNotNull(res);
	}

	@Test
	public void testRunCommandDirMergeIgnore() {
		String cmd = "java";

		CmdResult res = cli.run(cmd,
				new File(System.getProperty("user.home")), true);

		assertNotNull(res);
	}
}
