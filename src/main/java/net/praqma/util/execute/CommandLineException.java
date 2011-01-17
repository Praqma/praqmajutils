package net.praqma.util.execute;

public class CommandLineException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8031565757415076147L;

	CommandLineException()
	{
		super(); 
	}
	
	CommandLineException( String s )
	{
		super( s ); 
	}

}