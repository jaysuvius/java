package foobar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Graph<T> {
	List<Node<T>> nodes;
	
	public Graph(){
		nodes = new ArrayList<>();
	}
	
	public void add(T data){
		Node<T> n = new Node<>(data);
		nodes.add(n);
	}
	
	private void dfs(Node<Integer> n){

		dfs(n, new boolean[nodes.size()+ 1]);
		
	}
	
	private void dfs(Node<Integer> n, boolean[] visited){
		if (n== null)
			return;
		if(!visited[n.data]){
			System.out.println(n.data);
			visited[n.data] = true;
			for(Node<Integer> child : n.adjacents){
				dfs(child, visited);
			}
		}
		
	}
	
//	private void bfs(Node<Integer> n){
//
//		bfs(n, new boolean[nodes.size()+ 1]);
//		
//	}
	
	private void bfs(Node<Integer> n, boolean[] ){

		Queue<Node<Integer>> queue = new ArrayDeque<>();
		queue.offer(n);
		
		while (!queue.isEmpty()){
			Node<Integer> current = queue.poll();
			if (current == null)
				continue;
			if(visited[current.data])
				continue;
			System.out.println(current.data);
			visited[current.data] = true;
			
			for(Node<Integer> child : current.adjacents){
				queue.offer(child);
			}
		}

	}
	

	public static void main(String[] args){
		
		Graph<Integer> g = new Graph<>();
		for (int i = 1; i <= 50; i++){
			g.add(i);
		}
		
		Node<Integer> n1 = g.nodes.get(0);
		Node<Integer> n2 = g.nodes.get(4);
		Node<Integer> n3 = g.nodes.get(24);
		
		n1.addAdjacent(n2);
		n1.addAdjacent(n3);
		
		g.dfs(n1);
		g.bfs(n1);
		
	}
	
}

class Node<T>{
	T data;
	List<Node<T>> adjacents;
	
	public Node(T d){
		adjacents = new ArrayList<>();
		data = d;
	}
	
	public void addAdjacent(Node<T> n){
		adjacents.add(n);
		n.adjacents.add(this);
	}
}