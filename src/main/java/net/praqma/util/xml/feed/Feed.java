package net.praqma.util.xml.feed;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.umd.cs.findbugs.annotations.*;
import net.praqma.util.xml.XML;

public class Feed {
	public String id;
	public String title;
	public Date updated;
	
	public String link;
	public Person author;
	
	private List<Entry> entries = new ArrayList<Entry>();

	@SuppressFBWarnings("EI_EXPOSE_REP2")
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
		Collections.sort( entries, new AscendingDateSort() );
		return publisher.toFeed( this, -1 );
	}
	
	public String getXML( FeedPublisher publisher, int limit ) throws FeedException {
		Collections.sort( entries, new AscendingDateSort() );
		return publisher.toFeed( this, limit );
	}
	
	public static Feed getFeed( FeedPublisher publisher, File file ) throws FeedException, IOException {
		XML xml = new XML( file );
		Feed feed = publisher.fromFeed( xml );
		Collections.sort( feed.entries, new AscendingDateSort() );
		return feed;
	}
	
	public static Feed getFeed( FeedPublisher publisher, XML xml ) throws FeedException {
		Feed feed = publisher.fromFeed( xml );
		Collections.sort( feed.entries, new AscendingDateSort() );
		return feed;
	}
	
	public static class AscendingDateSort implements Comparator<Entry>, Serializable {

		@Override
		public int compare( Entry e1, Entry e2 ) {
			if( e1.updated == null ) {
				return -1;
			}
			if( e2.updated == null ) {
				return 1;
			}
			return (int) ( ( e2.updated.getTime() / 1000 ) - ( e1.updated.getTime() / 1000 ) );
		}
	}
	
	@Override
	public String toString() {
		return title + "(" + id + "/" + updated + ")";
	}

}
