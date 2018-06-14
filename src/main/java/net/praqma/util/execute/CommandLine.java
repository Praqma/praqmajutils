package net.praqma.util.execute;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.commons.lang.SystemUtils;

/**
 * CLI class
 * 
 * @author wolfgang
 * 
 */
public class CommandLine implements CommandLineInterface {
	protected static final Logger logger = Logger.getLogger( CommandLine.class.getName() );
	protected static final String linesep = System.getProperty( "line.separator" );
	private static CommandLine instance = new CommandLine();
	private String[] cmd = null;
    
	private CommandLine() { }

    @Override
	public OperatingSystem getOS() {
		if(SystemUtils.IS_OS_WINDOWS) {
            return OperatingSystem.WINDOWS;
        } else {
            return OperatingSystem.UNIX;
        }
	}

	public static CommandLine getInstance() {
		return instance;
	}

    @Override
	public CmdResult run( String cmd ) throws CommandLineException, AbnormalProcessTerminationException {
		return run( cmd, null, true, false, null );
	}

    @Override
	public CmdResult run( String cmd, File dir ) throws CommandLineException, AbnormalProcessTerminationException {
		return run( cmd, dir, true, false, null );
	}

    @Override
	public CmdResult run( String cmd, File dir, boolean merge ) throws CommandLineException, AbnormalProcessTerminationException {
		return run( cmd, dir, merge, false, null );
	}

    @Override
	public CmdResult run( String cmd, File dir, boolean merge, boolean ignore ) throws CommandLineException, AbnormalProcessTerminationException {
		return run( cmd, dir, merge, ignore, null );
	}

	/**
	 * Execute a command line operation.
	 * 
	 * @param c
	 *            The command itself
	 * @param dir
	 *            The working directory
	 * @param merge
	 *            Merge stderror with stdout
	 * @param ignore
	 *            Ignore any abnormal process terminations. This will allow the
	 *            output to be returned without exceptions to be thrown.
	 * @return
	 * @throws CommandLineException
	 * @throws AbnormalProcessTerminationException
	 */
    @Override
	public CmdResult run( String c, File dir, boolean merge, boolean ignore, Map<String, String> variables ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.config(String.format("$ %s" ,c));

		try {            
			ProcessBuilder pb = new ProcessBuilder( new String[] { 
                SystemUtils.IS_OS_WINDOWS ? "cmd.exe" : "bash", 
                SystemUtils.IS_OS_WINDOWS ? "/C" : "-c",
                c                    
            } );
			pb.redirectErrorStream( merge );
			if( dir != null ) {
				logger.config( "Executing command in " + dir );
				pb.directory( dir );
			}
			
			Process p = pb.start();

			/* Starting Gobbler threads */
			StreamGobbler output = new StreamGobbler( p.getInputStream() );
			StreamGobbler errors = new StreamGobbler( p.getErrorStream() );
			p.getOutputStream().close();

			output.start();
			errors.start();

			int exitValue = 0;
			try {
				exitValue = p.waitFor();
			} catch( InterruptedException e ) {
				p.destroy();
			} finally {
				Thread.interrupted();
			}

			try {
				output.join();
			} catch( InterruptedException e ) {
				logger.severe( "Could not join output thread" );
			}

			try {
				errors.join();
			} catch( InterruptedException e ) {
				logger.severe( "Could not join errors thread" );
			}

			/* Closing streams */
			p.getErrorStream().close();
			p.getInputStream().close();
			/* Abnormal process termination, with error out as message */
			if( exitValue != 0 ) {
				logger.fine( "Abnormal process termination(" + exitValue + "): " + errors.sres.toString() );

				/*
				 * Only throw the exception if it is not ignored, this is
				 * default
				 */
				if( !ignore ) {
                    StringBuilder error = new StringBuilder().append( output.sres.toString() ).
                            append( System.getProperty( "line.separator" ) ).
                            append( "Command: " ).append( c ).
                            append( System.getProperty( "line.separator" ) ).
                            append( "Path: " ).append( dir );

					if( merge ) {
						throw new AbnormalProcessTerminationException( error.toString(), c, exitValue );
					} else {
						throw new AbnormalProcessTerminationException( error.toString(), c, exitValue );
					}
				}
			}

			/* Setting command result */
            CmdResult result = new CmdResult();
			result.stdoutBuffer = output.sres;
			result.stdoutList = output.lres;

			result.errorBuffer = errors.sres;
			result.errorList = errors.lres;

			return result;
		} catch( IOException e ) {
			logger.warning( "Could not execute the command \"" + cmd + "\" correctly: " + e.getMessage() );
			throw new CommandLineException( "Could not execute the command \"" + c + "\" correctly: " + e.getMessage() );
		}
	}

}