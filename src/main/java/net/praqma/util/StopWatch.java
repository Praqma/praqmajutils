package net.praqma.util;

import edu.umd.cs.findbugs.annotations.*;

import java.util.LinkedList;
import java.util.List;

@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC")
public class StopWatch {

	private static final String SEP = System.getProperty( "line.separator" );
	
	private class Task {
		long nano;
		String title;
		
		public Task( String title ) {
			this.title = title;
		}
	}
	
	public static final long PRECISION_SECOND = 1;
	public static final long PRECISION_MILLI  = 1000;
	public static final long PRECISION_MICRO  = 1000000;
	public static final long PRECISION_NANO   = 1000000000;
	
	private List<Task> tasks = new LinkedList<Task>();
	private Task currentTask;

	public StopWatch() {
        this.initial = System.nanoTime();
	}

	private long initial = 0;
	private long startTime = 0;
	private long endTime = 0;
	private long totalTime = 0;

	public void start() {
		start( "N/A" );
	}
	
	public void start( String title ) {
		this.startTime = System.nanoTime();
		
		currentTask = new Task( title );
	}

	long getStartTime() {
		return this.startTime;
	}

	long getEndTime() {
		return this.endTime;
	}

	public void stop() {
		if( currentTask != null ) {
			this.endTime = System.nanoTime();
			
			currentTask.nano = this.endTime - this.startTime;
	
			this.totalTime += currentTask.nano;
			
			tasks.add( currentTask );
			currentTask = null;
		} else {
			throw new IllegalStateException( "No current tasks. Stop watch could have been stopped twice?" );
		}
	}

	public long getTime() {
		this.endTime = System.nanoTime();

		return ( this.endTime - this.startTime ) + this.totalTime;
	}
	
	public long getCurrentTime() {
		return ( this.endTime - this.startTime );
	}
	
	private static final int MAX_TITLE_LENGTH = 32;
	private static final int MAX_PERCENTAGE_LENGTH = 10;
	private static final int MAX_TIME_LENGTH = 10;
	
	public String print( long precision ) {
		StringBuilder sb = new StringBuilder();
		
		long now = System.nanoTime();
		long full = now - initial;
		
		System.out.println( "NOW: " + now + ", INITIAL: " + initial + " = " + ( ( now - initial ) / PRECISION_NANO ) );
		
		if( tasks.size() > 0 ) {
			long total = 0;
			for( Task t : tasks ) {
				total += t.nano;
			}
			
			sb.append( " Title                           %          Seconds" + SEP );
			sb.append( "-" + repeat( MAX_PERCENTAGE_LENGTH + MAX_TIME_LENGTH + MAX_TITLE_LENGTH, 0, "-" ) + SEP );
			
			for( Task t : tasks ) {
				Double p = Math.round( ( (double)t.nano / total ) * 10000.0 ) / 100.0;
				sb.append( " " + t.title + spaces( MAX_TITLE_LENGTH, t.title.length() ) + p + "%" + spaces( MAX_PERCENTAGE_LENGTH, ( p + "" ).length() ) + toSeconds( t.nano, precision ) + SEP );
			}
			
			sb.append( "-" + repeat( MAX_PERCENTAGE_LENGTH + MAX_TIME_LENGTH + MAX_TITLE_LENGTH, 0, "-" ) + SEP );
		}
		
		sb.append( "Total time. Overall:" + toSeconds( full, precision ) + "s. Aggregated: " + toSeconds( totalTime, precision ) );
		
		return sb.toString();
	}
	
	private String spaces( int max, int length ) {
		return repeat( max, length, " " );
	}
	
	private String repeat( int max, int length, String chr ) {
		return new String( new char[max - length] ).replace( "\0", chr );
	}

	public void reset() {
		initial = System.nanoTime();
		startTime = 0;
		endTime = 0;
		totalTime = 0;
		tasks = new LinkedList<Task>();
	}

	public double getSeconds() {
		return ( (double) totalTime / 1000000000 );
	}

	public static double toSeconds( long time, long precision ) {
		return ( ( Math.round( ( (double) time / PRECISION_NANO ) * precision ) ) / (double) precision );
	}
	
	public String toString() {
		return startTime + " -> " + endTime + " = " + ( endTime - startTime );
	}
}
