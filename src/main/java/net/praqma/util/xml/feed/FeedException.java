package net.praqma.util.xml.feed;

public class FeedException extends Exception {
	public FeedException( String m ) {
		super( m );
	}
	
	public FeedException( String m, Exception e ) {
		super( m, e );
	}
}
