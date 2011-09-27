package net.praqma.util.debug;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import net.praqma.util.debug.Logger.LogLevel;

public class LoggerSetting implements Serializable {
	private static final long serialVersionUID = 5836920612461350436L;

	private Set<String> subscriptions = new LinkedHashSet<String>();
	private LogLevel minimumLevel = LogLevel.INFO;
	
	public LogLevel getMinimumLevel() {
		return minimumLevel;
	}
	
	public void setMinimumLevel( LogLevel minimumLevel ) {
		this.minimumLevel = minimumLevel;
	}
	
	public Set<String> getSubscriptions() {
		return subscriptions;
	}
	
	public void setSubscriptions( Set<String> subscriptions ) {
		this.subscriptions = subscriptions;
	}
}
