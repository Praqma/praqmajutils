package net.praqma.util.xml.feed;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.praqma.util.xml.XML;

public abstract class FeedPublisher extends XML {
	public static SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
	
	public abstract String toFeed( Feed feed, int limit ) throws FeedException;
	public abstract Feed fromFeed( XML xml ) throws FeedException;
	
	public static String dateToString( Date date ) {
		return format.format( date );
	}
}
