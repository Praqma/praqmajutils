package net.praqma.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class ExceptionUtils {

	public static void print( Throwable e, PrintStream out, boolean stack ) {
		if( e.getCause() != null ) {
			out.println( e.getMessage() );
			print( e.getCause(), out, stack );
		} else {
			if( stack ) { 
				e.printStackTrace( out );
			} else {
				out.println( e.getMessage() );
			}
		}
	}
	
	/**
	 * Unpack an exception, returning the child exception of the first exception assignable from end 
	 * @param end
	 * @param throwable
	 * @return
	 */
	public static Throwable unpackBottom( Class<? extends Throwable> end, Throwable throwable ) {
		
		List<Throwable> throwables = new ArrayList<Throwable>();
		while( throwable != null ) {
			throwables.add( throwable );
			throwable = throwable.getCause();
		}
		
		ListIterator<Throwable> it = throwables.listIterator( throwables.size() );
		
		/* If the first item found matches end, return null?! NO!! */
		Throwable previous = null;
		while( it.hasPrevious() ) {
			Throwable current = it.previous();
			
			if( current.getClass().isAssignableFrom( end ) && previous != null ) {
				break;
			}
			
			previous = current;
		}
		
		return previous;
	}
	
	/**
	 * Unpack an exception from a given throwable. Returning the cause of the first matched end throwable. 
	 * @param from - Stopping condition
	 * @param throwable - The throwable
	 * @return Cause of the first matched throwable
	 */
	public static Throwable unpackFrom( Class<? extends Throwable> from, Throwable throwable ) {
		
		Throwable origin = throwable;
		
		while( throwable != null ) {
			if( throwable.getClass().isAssignableFrom( from ) ) {
				Throwable child = throwable.getCause();
				if( child != null ) {
					return child;
				} else {
					return throwable;
				}
			}
			
			throwable = throwable.getCause();
		}
		
		return origin;
	}
	
	public static Throwable unpack( Class<? extends Throwable> parentClass, Throwable throwable ) {
		/* When cause is no longer assignable from end */
		if( !throwable.getClass().isAssignableFrom( parentClass ) ) {
			return throwable;
		} else {
			Throwable cause = throwable.getCause();
		
			/* If cause is null, throwable is the root cause, which we return */
			if( cause == null ) {
				return throwable;
			/* Otherwise unpack cause */
			} else {
				return unpack( parentClass, cause );
			}
		}
	}
	
}
