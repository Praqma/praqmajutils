package net.praqma.util.debug.appenders;

import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import net.praqma.util.debug.Logger.LogLevel;

public class Appender {
	private LogLevel minimumLevel = LogLevel.DEBUG;
	private PrintWriter out;
	private Set<String> included = new LinkedHashSet<String>();
	private boolean enabled = true;
	private boolean subscribeAll = true;
	
	private String template = "%datetime %level %space %stack %message%newline";
	
	public Appender( PrintWriter out ) {
		this.setOut( out );
	}
	
	public <T> void subscribe( Class<T> tclass ) {
		if( !included.contains( tclass.getCanonicalName() ) ) {
			included.add( tclass.getCanonicalName() );
			subscribeAll = false;
		}
	}
	
	public void onBeforeLogging() {
		
	}

	public LogLevel getMinimumLevel() {
		return minimumLevel;
	}

	public void setMinimumLevel( LogLevel minimumLevel ) {
		this.minimumLevel = minimumLevel;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled( boolean enabled ) {
		this.enabled = enabled;
	}

	public boolean isSubscribeAll() {
		return subscribeAll;
	}

	public void setSubscribeAll( boolean subscribeAll ) {
		this.subscribeAll = subscribeAll;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut( PrintWriter out ) {
		this.out = out;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate( String template ) {
		this.template = template;
	}
}
