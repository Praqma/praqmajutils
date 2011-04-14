package net.praqma.util.execute;

public class CommandLineException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8031565757415076147L;
//TODO remove this is not used any where in the code..
//	CommandLineException()
//	{
//		super(); 
//	}
	
	CommandLineException( String s )
	{
		super( s ); 
	}

}