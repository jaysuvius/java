package foo;

import java.util.Stack;

public class DoubleLinkedList {

	DoubleNode root;
	
	public DoubleLinkedList(){
		
	}
	
	DoubleNode addNode(int data){
		
		DoubleNode newNode = new DoubleNode(data);
		
		if (root == null){
			root = newNode;
			return root;
		}
		
		DoubleNode current = root;
		
		while(current != null){
			if(current.next == null){
				current.next = newNode;
				newNode.prev = current;
				return root;
			}
			current = current.next;
		}
		
		return root;
		
	}
	
	public DoubleNode insertNode(int data){
		
		DoubleNode newNode = new DoubleNode(data);
		DoubleNode current = root;
		DoubleNode previous;
		
		if (root == null){
			root = newNode;
			return root;
		}
		
		if (newNode.data < root.data){
			newNode.next = root;
			root.prev = newNode;
			root = newNode;
			return root;
		}
		
		while(current.next != null){
			previous = current;
			current = current.next;
			
			if (newNode.data >= previous.data && newNode.data < current.data){
				previous.next = newNode;
				newNode.prev = previous;
				newNode.next = current;
				current.prev = newNode;
				return root;
			}	
		}
		
		if (newNode.data >= current.data){
			current.next = newNode;
			newNode.prev = current;
		}
		
		return root;
		
	}
	
	public DoubleNode reverseList(){
		
		if (root == null || root.next == null)
			return root;
		
		
		DoubleNode current = root;
		DoubleNode newHead = null;

		while (current != null){
	        DoubleNode prev = current.prev;
	        current.prev = current.next;
	        current.next = prev;
	        newHead = current;
	        current = current.prev;
		}
		
		root = newHead;
		
		
		return root;
		
	}
	
	
	public String toString(){
		
		String rtnVal = "";
		
		DoubleNode current = root;
		
		while (current != null){
			rtnVal = rtnVal + current.data + ":";
			current = current.next;
		}
		
		return rtnVal;
		
	}
	
	
	
	
	
	
	
}

class DoubleNode {
	Integer data;
	DoubleNode next;
	DoubleNode prev;
	
	DoubleNode(){
		
	}
	
	DoubleNode(int d){
		data = d;
	}
	
	public String toString(){
		return data.toString();
	}
		
}

