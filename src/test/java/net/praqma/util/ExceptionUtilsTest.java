package net.praqma.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.praqma.util.execute.*;
import org.junit.Test;
import org.omg.CORBA.UserException;

public class ExceptionUtilsTest {
	@Test
	public void unpackBottom() {
		Exception inner = new ClassNotFoundException( "Inner", null );
		Exception e = new ExecutionException( "Outer", new IOException( "Middle", inner ) );
		Throwable t = ExceptionUtils.unpackBottom( IOException.class, e );
		assertEquals( inner, t );
	}
	
	@Test
	public void unpackBottomMiddleIsEnd() {
		Exception inner = new IOException( "Inner", null );
		Exception middle = new IOException( "Middle", inner );
		Exception e = new ExecutionException( "Outer", middle );
		Throwable t = ExceptionUtils.unpackBottom( IOException.class, e );
		assertEquals( inner, t );
	}
	
	@Test
	public void unpackBottomOuterIsEnd() {
		Exception inner = new IOException( "Inner", null );
		Exception middle = new IOException( "Middle", inner );
		Exception e = new ExecutionException( "Outer", middle );
		Throwable t = ExceptionUtils.unpackBottom( ExecutionException.class, e );
		assertEquals( middle, t );
	}
	
	@Test
	public void unpackBottomEndNotMatched() {
		Exception inner = new ClassNotFoundException( "Inner" );
		Exception e = new ExecutionException( "Outer", new IOException( "Middle", inner ) );
		Throwable t = ExceptionUtils.unpackBottom( UserException.class, e );
		assertEquals( e, t );
	}
	
	@Test
	public void unpackFrom() {
		Exception inner = new ClassNotFoundException( "Inner", null );
		Exception e = new ExecutionException( "Outer", new IOException( "Middle", inner ) );
		Throwable t = ExceptionUtils.unpackFrom( IOException.class, e );
		assertEquals( inner, t );
	}
	
	@Test
	public void unpackFromNoEndMatch() {
		Exception inner = new ExecutionException( "Inner", null );
		Exception middle = new ExecutionException( "Middle", inner );
		Exception outer = new ExecutionException( "Outer", middle );
		Throwable t = ExceptionUtils.unpackFrom( IOException.class, outer );
		assertEquals( outer, t );
	}
	
	@Test
	public void unpackFromEndLast() {
		Exception inner = new IOException( "Inner", null );
		Exception middle = new ExecutionException( "Middle", inner );
		Exception outer = new ExecutionException( "Outer", middle );
		Throwable t = ExceptionUtils.unpackFrom( IOException.class, outer );
		assertEquals( inner, t );
	}
	
	@Test
    public void unpackFromEnds() {
        Exception inner = new IOException( "Inner", null );
        Exception middle = new IOException( "Middle", inner );
        Exception outer = new IOException( "Outer", middle );
        Throwable t = ExceptionUtils.unpackFrom( IOException.class, outer );
        assertEquals( middle, t );
    }

    @Test
    public void getCause() {
        Exception inner = new AbnormalProcessTerminationException( "Inner", "ls -la" );
        Exception middle = new IOException( "Middle", inner );
        Exception outer = new IOException( "Outer", middle );
        Throwable t = ExceptionUtils.getCause( AbnormalProcessTerminationException.class, outer );
        assertEquals( inner, t );
        assertTrue( t.getClass().equals(AbnormalProcessTerminationException.class ) ) ;
    }

    @Test
    public void getCauseNone() {
        Exception inner = new IOException( "Inner", null );
        Exception middle = new IOException( "Middle", inner );
        Exception outer = new IOException( "Outer", middle );
        Throwable t = ExceptionUtils.getCause( AbnormalProcessTerminationException.class, outer );
        assertEquals( null, t );
    }
}
