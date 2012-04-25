package net.praqma.util.xml.feed;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import junit.framework.TestCase;

public class FeedTest extends TestCase {

	@Test
	public void testFeed() throws FeedException {
		Feed feed = new Feed( "test", "my--001", new Date() );
		feed.link = "http://www.praqma.net";
		feed.author = new Person( "PRAQMA" );
		
		Entry e = new Entry( "Title E1", "My-id-001", new Date() );
		e.summary = "Something cool";
		e.author = new Person( "PRAQMA #1" );
		e.content = "A very long content tag";
		feed.addEntry( e );
		
		String xml = feed.getXML( new AtomPublisher() );
		
		System.out.println( "XML: " + xml );
	}
	
	@Test
	public void testToFeed() throws FeedException, IOException {
		File xml = new File( FeedTest.class.getClassLoader().getResource( "feed.xml" ).getFile() );
		Feed feed = Feed.getFeed( new AtomPublisher(), xml );
		
		System.out.println( "FEED: " + feed );
		
		for( Entry entry : feed.getEntries() ) {
			System.out.println( "ENTRY: " + entry );
		}
	}
}
