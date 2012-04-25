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
		
		Entry e2 = new Entry( "Title E2", "My-id-002", new Date() );
		e2.summary = "Something cool 2";
		e2.author = new Person( "PRAQMA #2" );
		e2.content = "A very long content tag 2";
		feed.addEntry( e2 );
		
		String xml = feed.getXML( new AtomPublisher() );
		
		System.out.println( "XML: " + xml );
	}
	
	@Test
	public void testToFeed() throws FeedException, IOException {
		File xml = new File( FeedTest.class.getClassLoader().getResource( "feed.xml" ).getFile() );
		Feed feed = Feed.getFeed( new AtomPublisher(), xml );
		
		System.out.println( "XML2way: " + feed.getXML( new AtomPublisher() ) );
	}
}
