package net.praqma.util.execute;

public class AbnormalProcessTerminationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6920102041434852713L;
	
	private int exitValue = 0;

	public AbnormalProcessTerminationException( String s ) {
		super( s );
	}
	
	public AbnormalProcessTerminationException( String s, int exitValue ) {
		super( s );
		
		this.exitValue = exitValue;
	}
	
	public int getExitValue() {
		return exitValue;
	}

}