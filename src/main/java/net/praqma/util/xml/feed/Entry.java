package net.praqma.util.xml.feed;

import java.util.Date;

public class Entry {
	public String id;
	public String title;
	public Date updated;
	public String link;
	public Person author;
	public String content;
	public String summary;
	
	public Entry( String title, String id, Date updated ) {
		this.title = title;
		this.id = id;
		this.updated = updated;
	}
	
	@Override
	public String toString() {
		return title + "(" + id + "/" + updated + ")";
	}
}
