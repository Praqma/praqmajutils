package net.praqma.util.structure;

import java.io.Serializable;

public class Tuple<T1, T2> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public T1 t1 = null;
	public T2 t2 = null;
	
	public Tuple( T1 t1, T2 t2 )
	{
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public Tuple(){}
	
	public T1 getFirst()
	{
		return t1;
	}
	
	public T2 getSecond()
	{
		return t2;
	}
	
	public void setFirst( T1 t1 )
	{
		this.t1 = t1;
	}
	
	public void setSecond( T2 t2 )
	{
		this.t2 = t2;
	}
	
	public String toString()
	{
		return "(" + t1.toString() + ", " + t2.toString() + ")";
	}
}
