package net.praqma.util.structure.tree;

public class Tree<T> {

	private Node<T> root;
	
	public Tree( T data ) {
		root = new Node<T>( data );
	}
	
	public Node<T> getRoot() {
		return root;
	}
	
}
