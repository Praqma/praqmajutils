package net.praqma.util.structure.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private List<Node<T>> childs = new ArrayList<Node<T>>();
	
	private T data;
	
	public Node( T data ) {
		this.data = data;
	}
	
	public void addChild( Node<T> node ) {
		childs.add( node );
	}
	
	public T getData() {
		return data;
	}
	
	public void setData( T data ) {
		this.data = data;
	}
	
	public List<Node<T>> getChilds() {
		return childs;
	}
}
