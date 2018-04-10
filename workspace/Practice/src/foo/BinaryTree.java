package foo;

public class BinaryTree<T> {
	
	Node _root;
	
	void addNode(int key, String data){
		
		Node newNode = new Node(key, data);
		
		if (_root == null){
			_root = newNode;
		}
		else{
			Node focusNode = _root;
			Node parent;
			
			while(true){
				
				parent = focusNode;
				
				if (newNode.getKey() > focusNode.getKey()){
					focusNode = focusNode.getLeftChild();
					if (focusNode == null){
						parent.setLeftChild(newNode);
						return;
					}
						
				}
				else
				{
					focusNode = focusNode.getRightChild();
					if (focusNode == null){
						parent.setRightChild(newNode);
						return;
					}
						
				}
				
				
			}
		}
	}
	

}

class Node {
	private int _key;
	private String _data;
	private Node _leftChild;
	private Node _rightChild;
	
	public int getKey(){
		return _key;
	}
	
	public void setKey(int key){
		_key = key;
	}
	
	public String getData(){
		return _data;
	}
	
	public void setData(String data){
		_data = data;
	}
	
	public Node getRightChild(){
		return _rightChild;
	}
	
	public void setRightChild(Node rightChild){
		_rightChild = rightChild;
	}
	
	public Node getLeftChild(){
		return _leftChild;
	}
	
	public void setLeftChild(Node leftChild){
		_leftChild = leftChild;
	}
	
	
	public Node(int key, String data){
		_key = key;
		_data = data;
	}
	
	
}
