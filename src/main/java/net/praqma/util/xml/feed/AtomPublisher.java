package net.praqma.util.xml.feed;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import edu.umd.cs.findbugs.annotations.*;
import org.w3c.dom.Element;

import net.praqma.util.xml.XML;

@SuppressFBWarnings("UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
public class AtomPublisher extends FeedPublisher {
	
	@Override
	public String toFeed( Feed feed, int limit ) throws FeedException {
		XML xml = new XML( "feed" );
		Element root = xml.getRoot();
		root.setAttribute( "xmlns", "http://www.w3.org/2005/Atom" );
		
		/* Title */
		if( feed.title != null ) {
			xml.addElement( "title" ).setTextContent( feed.title );
		}
		
		/* Link */
		if( feed.link != null ) {
			xml.addElement( "link" ).setTextContent( feed.link );
		}
		
		/* Updated */
		if( feed.updated != null ) {
			xml.addElement( "updated" ).setTextContent( dateToString( feed.updated ) );
		}		
		
		/* Author */
		if( feed.author != null ) {
			xml.appendElement( getPerson( feed.author, xml ) );
		}
		
		/* ID */
		if( feed.id != null ) {
			xml.addElement( "id" ).setTextContent( feed.id );
		}
		
		/* Process entries */
		int count = 0;
		for( Entry entry : feed.getEntries() ) {
			if( count == limit ) {
				break;
			}
			Element e = xml.createElement( "entry", root );
			
			/* Title */
			if( entry.title != null ) {
				xml.addElement( e, "title" ).setTextContent( entry.title );
			}
			
			/* Link */
			if( entry.link != null ) {
				xml.addElement( e, "link" ).setTextContent( entry.link );
			}
			
			/* Author */
			if( entry.author != null ) {
				xml.appendElement( getPerson( entry.author, xml ), e );
			}
			
			/* Id */
			if( entry.id != null ) {
				xml.addElement( e, "id" ).setTextContent( entry.id );
			}
			
			/* Updated */
			if( entry.updated != null ) {
				xml.addElement( e, "updated" ).setTextContent( dateToString( entry.updated ) );
			}
			
			/* Summary */
			if( entry.summary != null ) {
				xml.addElement( e, "summary" ).setTextContent( entry.summary );
			}
			
			/* Content */
			if( entry.content != null ) {
				Element cElement = xml.addElement( e, "content" );
                cElement.setAttribute("type", "html");
                cElement.setTextContent(entry.content);
			}
			
			count++;
		}
		
		return xml.getXML();
	}

	@SuppressFBWarnings("STCAL_INVOKE_ON_STATIC_DATE_FORMAT_INSTANCE")
	@Override
	public Feed fromFeed( XML xml ) throws FeedException {
		Element root = xml.getRoot();
		
		String title = "";
		String id = "";
		Date updated = null;
		
		try {
			title = xml.getFirstElement( "title" ).getTextContent();
			id = xml.getFirstElement( "id" ).getTextContent();
			try {
				updated = format.parse( xml.getFirstElement( "updated" ).getTextContent() );
			} catch( ParseException e ) {
				/* Unable to parse date format, try old */
				updated = oldformat.parse( xml.getFirstElement( "updated" ).getTextContent() );
			}
		} catch( Exception ex ) {
			throw new FeedException( "Missing required elements in feed", ex );
		}
		
		Feed feed = new Feed( title, id, updated );
		
		/* Get other fields */
		/* Author */
		try {
			feed.author = getPerson( xml.getFirstElement( "author" ), xml );
		} catch( Exception ex ) {
			/* no op */
		}
		
		/* Link */
		try {
			feed.link = xml.getFirstElement( "link" ).getTextContent();
		} catch( Exception ex ) {
			/* no op */
		}
		
		List<Element> elements = xml.getElements( root, "entry" );
		
		for( Element e : elements ) {
			String etitle = "";
			String eid = "";
			Date eupdated = null;
			try {
				etitle = xml.getFirstElement( e, "title" ).getTextContent();
				eid = xml.getFirstElement( e, "id" ).getTextContent();
				eupdated = format.parse( xml.getFirstElement( e, "updated" ).getTextContent() );
			} catch( ParseException ex ) {
				continue;
			}

            Entry entry = new Entry( etitle, eid, eupdated );
			
			/* Summary */
			try {
				entry.summary = xml.getFirstElement( e, "summary" ).getTextContent();
			} catch( Exception ex ) {
				/* no op */
			}
			
			/* Content */
			try {
				entry.content = xml.getFirstElement( e, "content" ).getTextContent();
			} catch( Exception ex ) {
				/* no op */
			}
			
			/* Link */
			try {
				entry.link = xml.getFirstElement( e, "link" ).getTextContent();
			} catch( Exception ex ) {
				/* no op */
			}
			
			/* Author */
			try {
				entry.author = getPerson( xml.getFirstElement( e, "author" ), xml );
			} catch( Exception ex ) {
				/* no op */
			}
			
			
			feed.addEntry( entry );
		}		
		
		return feed;
	}

	public static Element getPerson( Person author, XML xml ) throws FeedException {
		Element e = xml.createElement( "author" );
		
		if( author.name != null ) {
			xml.createElement( "name", e ).setTextContent( author.name );
		} else {
			throw new FeedException( "Author requires name" );
		}
		
		if( author.uri != null ) {
			xml.createElement( "uri", e ).setTextContent( author.uri );
		}
		
		if( author.email != null ) {
			xml.createElement( "email", e ).setTextContent( author.email );
		}
		
		return e;
	}
	
	public static Person getPerson( Element e, XML xml ) throws FeedException {
		String name = "";
		try {
			name = xml.getFirstElement( e, "name" ).getTextContent();
		} catch( Exception ex ) {
			throw new FeedException( "Missing required elements in person", ex );
		}
		
		Person p = new Person( name );
		
		/* Other */
		
		return p;
	}
}
