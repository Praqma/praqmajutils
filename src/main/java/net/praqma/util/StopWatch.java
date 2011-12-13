package net.praqma.util;

import java.util.HashMap;
import java.util.Map;

public class StopWatch {
	private static Map<String, StopWatch> sws = new HashMap<String, StopWatch>();

	private StopWatch() {

	}

	public static StopWatch get( String name ) {
		StopWatch sw = null;

		if( !sws.containsKey( name ) ) {
			sw = new StopWatch();
			sw.setName( name );
			sws.put( name, sw );
		} else {
			sw = sws.get( name );
		}

		return sw;
	}

	private long startTime = 0;
	private long endTime = 0;
	private long time = 0;
	private String name;

	public void start() {
		this.startTime = System.nanoTime();
	}

	long getStartTime() {
		return this.startTime;
	}

	long getEndTime() {
		return this.endTime;
	}

	public void stop() {
		this.endTime = System.nanoTime();

		this.time += this.endTime - this.startTime;
	}

	public long getTime() {
		this.endTime = System.nanoTime();

		return ( this.endTime - this.startTime ) + this.time;
	}

	public void reset() {
		startTime = 0;
		endTime = 0;
		time = 0;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void delete() {
		sws.remove( name );
	}

	public double getSeconds() {
		return ( (double) time / 1000000000 );
	}

	public static double toSeconds( long time, int precision ) {
		return ( ( Math.round( ( (double) time / 1000000000 ) * precision ) ) / (double) precision );
	}
}
