package net.praqma.util.xml.feed;

import edu.umd.cs.findbugs.annotations.*;

@SuppressFBWarnings("UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
public class Person {
	public String name;
	public String uri;
	public String email;
	
	public Person( String name ) {
		this.name = name;
	}
}
