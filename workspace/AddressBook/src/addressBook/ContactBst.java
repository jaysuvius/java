package addressBook;
/**
 * @author Jeremy May
 * CS189 Data Structures Project
 * 9/12/2016
 * Binary Search Tree (BST) Implementation
 */
public class ContactBst {
	
	/** JM:
	 * Root node
	 */
	private Node _root;
	
	/** JM:
	 * @return Root Node
	 */
	public Node getRoot(){
		return _root;
	}
	
	/** JM:
	 * @param root
	 */
	public void setRoot(Node root){
		_root = root;
	}
	
	/** JM:
	 * Adds contact node to binary search tree
	 * @param contact
	 */
	public void addNode(Contact contact){
		
		String key = contact.getFirstName() + " " + contact.getLastName();
		
		Node newNode = new Node(key, contact);
		
		//JM: If this is first entry, the node is the root node
		if (_root == null)
			_root = newNode;
		else{ //JM:  If not, find the next available null child
			Node focusNode = _root;
			Node parent;
			while(true){
				
				parent = focusNode;
				
				//JM: if new node is less than parent, add to left child
				if (key.compareTo(focusNode.getKey()) < 0){
					
					focusNode = focusNode.getLeftChild();
					
					if (focusNode == null){
						
						parent.setLeftChild(newNode);
						return;
					}
				}
				//JM: else add to parent child node
				else {
					
					focusNode = focusNode.getRightChild();
					
					if(focusNode == null){
						parent.setRightChild(newNode);
						return;
					}
				}
			}
		}
	}
	
	/** JM:
	 * Removes contact from BST by first and last name
	 * @param firstName
	 * @param lastName
	 * @return Contact details or Not Found
	 */
	public String remove(String firstName, String lastName){
		//JM: execute remove node function and return message indicating success
		if (removeNode(firstName, lastName)){
			return "Removed " + firstName + " " + lastName;
		}
		else
			return firstName + " " + lastName + " Not Found";
	}
	
	
	/** JM:
	 * Removes contact node from BST
	 * @param firstName
	 * @param lastName
	 * @return boolean whether or not node was found and removed
	 */
	public boolean removeNode(String firstName, String lastName)
	{
		String key = firstName + " " + lastName;
		
		Node focusNode = _root;
		Node parent = _root;
		
		boolean isLeftChild = true;
		
		//JM: Loop while focusNode is not equal to key
		while(focusNode.getKey().compareTo(key) != 0 ){
			parent = focusNode;
			
			//JM: if focusNode key is less than parent work with left child
			if (key.compareTo(focusNode.getKey()) < 0){
				isLeftChild = true;
				focusNode = focusNode.getLeftChild();
			}
			else{ //JM: work with right child if not
				isLeftChild = false;
				focusNode = focusNode.getRightChild();
			}
			
			//JM: if focusNode is null we did not find the node to remove
			if (focusNode == null)
				return false;
			
			//JM: this is leaf with no children, just remove
			if (focusNode.getLeftChild() == null && focusNode.getRightChild() == null){
				if (focusNode == _root)
					_root = null;
				else if (isLeftChild){
					parent.setLeftChild(null);
				}
				else {
					parent.setRightChild(null);
				}
			}
			//JM: remove left child leaf and reconnect it's children
			else if (focusNode.getRightChild() == null){
				if (focusNode == _root)
					_root = focusNode.getLeftChild();
				else if (isLeftChild)
					parent.setLeftChild(focusNode.getLeftChild());
				else
					parent.setRightChild(focusNode.getRightChild());
			}
			//remove right child leaf and reconnect it's children
			else if (focusNode.getLeftChild() == null){
				if (focusNode == _root)
					_root = focusNode.getRightChild();
				else if (isLeftChild)
					parent.setLeftChild(focusNode.getRightChild());
				else
					parent.setRightChild(focusNode.getLeftChild());
				
			}
			else{
				//JM: remove node, bubble up, and reconnect the proper child nodes
				Node replacement = getReplacementNode(focusNode);
				if(focusNode == _root)
					_root = replacement;
				else if (isLeftChild)
					parent.setLeftChild(replacement);
				else
					parent.setRightChild(replacement);
				
				replacement.setLeftChild(focusNode.getLeftChild());
			}			
		}
		
		return true;
	}
	
	/** JM:
	 * Returns correct replacement node to replace deleted node
	 * @param replacedNode
	 * @return replacement Node for removal algorithm
	 */
	public Node getReplacementNode(Node replacedNode){
		
		Node replacementParent = replacedNode;
		Node replacement = replacedNode;
		
		Node focusNode = replacedNode.getRightChild();
		//JM: decide which node is the proper replacement and return it
		while (focusNode != null){
			replacementParent = replacement;
			replacement = focusNode;
			focusNode = focusNode.getLeftChild();
		}
		if (replacement != replacedNode.getRightChild()){
			
			replacementParent.setLeftChild(replacement.getRightChild());
			replacement.setRightChild(replacedNode.getRightChild());
		}
		return replacement;
	}
	
	/** JM:
	 * Performs In Order BST Traversal
	 * @param focusNode
	 */
	public void traverseInOrder(Node focusNode){
		//JM: perform recursive in order traversal
		if (focusNode != null){
			traverseInOrder(focusNode.getLeftChild());
			System.out.println(focusNode.getContact().toString());
			traverseInOrder(focusNode.getRightChild());
		}
	}
	/** JM:
	 * Performs Pre Order BST Traversal
	 * @param focusNode
	 */
	public void traversePreOrder(Node focusNode){
		//JM: perform recursive pre order traversal
		if (focusNode != null){
			System.out.println(focusNode.getContact().toString());
			traverseInOrder(focusNode.getLeftChild());
			traverseInOrder(focusNode.getRightChild());
		}
	}
	/** JM:
	 * Performs Post Order BST Traversal
	 * @param focusNode
	 */
	public void traversePostOrder(Node focusNode){
		//JM: perform recursive post order traversal
		if (focusNode != null){
			traverseInOrder(focusNode.getLeftChild());
			traverseInOrder(focusNode.getRightChild());
			System.out.println(focusNode.getContact().toString());
		}
	}
	
	/** JM:
	 * Searches and returns contact details if found
	 * @param firstName
	 * @param lastName
	 * @return Contact details or Not Found
	 */
	public String find(String firstName, String lastName){
		//JM: execute findNode based on first and last names to search for
		Node foundNode = findNode(firstName, lastName);
		
		if (foundNode != null)
			return foundNode.toString();
		else
			return firstName + " " + lastName + " Not Found";
	}
	
	/** JM:
	 * Returns contact node if found null if not
	 * @param firstName
	 * @param lastName
	 * @return Contact Node if found
	 */
	public Node findNode(String firstName, String lastName){
		//JM: perform iteration and find node
		String key = firstName + " " + lastName;
		
		Node focusNode = getRoot();
		
		while (!focusNode.getKey().equals(key)){
			
			if (key.compareTo(focusNode.getKey()) < 0){
				focusNode = focusNode.getLeftChild();
			} else {
				focusNode = focusNode.getRightChild();
			}
			
			if (focusNode == null)
				return null;
		}
		
		return focusNode;
		
	}
	
	/** JM:
	 * @param root
	 * @param minKey
	 * @param maxKey
	 * @return boolean whether the binary tree starting at node n is a valid binary search tree
	 */
	public boolean isValid(Node root, String minKey, String maxKey) {
	     return isValidBST(root, minKey, maxKey);
	}
	
	/** JM:
	 * Recursive function to determind of BST is valid
	 * @param node
	 * @param MIN
	 * @param MAX
	 * @return boolean whether the binary tree starting at node n is a valid binary search tree 
	 */
	private boolean isValidBST(Node node, String MIN, String MAX) {
		//JM: perform recursive check to see if tree starting at node is BST
	     if(node == null)
	         return true;
	     if(node.getKey().compareTo(MIN) > 0 
	         && node.getKey().compareTo(MAX) < 0
	         && isValidBST(node.getLeftChild(), MIN, node.getKey())
	         && isValidBST(node.getRightChild(), node.getKey(), MAX))
	         return true;
	     else 
	         return false;
	}
	
}
/**
 * @author Jeremy May
 * CS189 Data Structures Project
 * 9/12/2016
 * Binary Search Tree Node
 */
class Node {
	
	/** JM:
	 * Private Vars
	 */
	private String _key;
	private Contact _contact;
	private Node _leftChild;
	private Node _rightChild;
	
	/** JM:
	 * @return Node Key
	 */
	public String getKey(){
		return _key;
	}
	
	/** JM:
	 * @param key
	 */
	public void setKey(String key){
		_key = key;
	}
	
	/** JM:
	 * @return Right Child Node
	 */
	public Node getRightChild(){
		return _rightChild;
	}
	
	/** JM:
	 * @param rightChild
	 */
	public void setRightChild(Node rightChild){
		_rightChild = rightChild;
	}
	
	/** JM:
	 * @return Left Child Node
	 */
	public Node getLeftChild(){
		return _leftChild;
	}
	
	/** JM:
	 * @param leftChild
	 */
	public void setLeftChild(Node leftChild){
		_leftChild = leftChild;
	}
	
	/** JM:
	 * @return Node data
	 */
	public Contact getContact(){
		return _contact;
	}
	
	/** JM:
	 * @param contact
	 */
	public void setContact(Contact contact){
		_contact = contact;
	}
	
	/** JM:
	 * Constructor
	 * @param key
	 * @param contact
	 */
	Node(String key, Contact contact){
		_key = key;
		_contact = contact;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Key:" + _key + " Contact:" + _contact.toString();
	}
}