package net.praqma.util.xml.feed;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.umd.cs.findbugs.annotations.*;
import net.praqma.util.xml.XML;

@SuppressFBWarnings( {"STCAL_STATIC_SIMPLE_DATE_FORMAT_INSTANCE",
		"STCAL_INVOKE_ON_STATIC_DATE_FORMAT_INSTANCE"
})
public abstract class FeedPublisher extends XML {
	public static final SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
	public static final SimpleDateFormat oldformat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'" );
	
	public abstract String toFeed( Feed feed, int limit ) throws FeedException;
	public abstract Feed fromFeed( XML xml ) throws FeedException;
	
	public static String dateToString( Date date ) {
		return format.format( date );
	}
}
