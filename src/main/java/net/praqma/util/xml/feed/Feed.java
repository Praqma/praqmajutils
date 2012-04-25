package net.praqma.util.xml.feed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.praqma.util.xml.XML;

public class Feed {
	public String id;
	public String title;
	public Date updated;
	
	public String link;
	public Person author;
	
	private List<Entry> entries = new ArrayList<Entry>();
	
	public Feed( String title, String id, Date updated ) {
		this.title = title;
		this.id = id;
		this.updated = updated;
	}
	
	public void addEntry( Entry entry ) {
		entries.add( entry );
	}
	
	public List<Entry> getEntries() {
		return entries;
	}
	
	public String getXML( FeedPublisher publisher ) throws FeedException {
		return publisher.toFeed( this );
	}
	
	public static Feed getFeed( FeedPublisher publisher, File file ) throws FeedException, IOException {
		XML xml = new XML( file );
		return publisher.fromFeed( xml );
	}
	
	public static Feed getFeed( FeedPublisher publisher, XML xml ) throws FeedException {
		return publisher.fromFeed( xml );
	}
	
	@Override
	public String toString() {
		return title + "(" + id + "/" + updated + ")";
	}

}
