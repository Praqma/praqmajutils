package net.praqma.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
}
