package net.praqma.util.execute;

import java.io.File;

import net.praqma.util.debug.PraqmaLogger.Logger;

public interface CommandLineInterface
{	
	public void setLogger( Logger logger );
	
	public CmdResult run( String cmd ) throws CommandLineException, AbnormalProcessTerminationException;
	
	public CmdResult run( String cmd, File dir ) throws CommandLineException, AbnormalProcessTerminationException;
	
	public CmdResult run( String cmd, File dir, boolean merge ) throws CommandLineException, AbnormalProcessTerminationException;

	public CmdResult run( String cmd, File dir, boolean merge, boolean ignore ) throws CommandLineException, AbnormalProcessTerminationException;
}
