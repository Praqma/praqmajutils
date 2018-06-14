package net.praqma.util.xml.feed;

import java.util.Date;
import java.util.UUID;

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
    
    /**
     * Added new constructor. This one takes no ID argument as this is automatically generated.
     * 
     * In specific cases where you want to tailor your feed id, don't use this one.
     * @param title
     * @param updated 
     */
    public Entry( String title, Date updated) {
        this.title = title;
        this.id = UUID.randomUUID().toString();
        this.updated = updated;
    }
	
	@Override
	public String toString() {
		return title + "(" + id + "/" + updated + ")";
	}
}
