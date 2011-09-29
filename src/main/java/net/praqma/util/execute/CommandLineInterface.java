package net.praqma.util.execute;

import java.io.File;
import java.util.Map;

public interface CommandLineInterface {
	public enum OperatingSystem {
		WINDOWS, UNIX
	}

	public OperatingSystem getOS();

	public CmdResult run( String cmd ) throws CommandLineException, AbnormalProcessTerminationException;

	public CmdResult run( String cmd, File dir ) throws CommandLineException, AbnormalProcessTerminationException;

	public CmdResult run( String cmd, File dir, boolean merge ) throws CommandLineException, AbnormalProcessTerminationException;

	public CmdResult run( String cmd, File dir, boolean merge, boolean ignore ) throws CommandLineException, AbnormalProcessTerminationException;
	
	public CmdResult run( String cmd, File dir, boolean merge, boolean ignore, Map<String, String> variables ) throws CommandLineException, AbnormalProcessTerminationException;
}
