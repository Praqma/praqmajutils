package net.praqma.util;

import java.io.PrintStream;

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
}
