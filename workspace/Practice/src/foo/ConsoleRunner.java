package foo;

import java.util.Arrays;

public class ConsoleRunner {

	public static void main(String[] args){
		Integer[] a = {19, 16, 15, 14, 14, 12, 11, 10, 13, 16};
		
		Integer[] b = Arrays.copyOf(a, a.length);
		Sorts.bubbleSort(a);
		//Sorts.selectionSort(a, 0, a.length - 1);
		//Sorts.insertionSort(a, 0, a.length - 1);
		Sorts.quickSort(a, 0, a.length - 1);
		System.out.println(Sorts.binarySearch(a, 12));
		Arrays.sort(b);
		System.out.println(Arrays.equals(a,  b));
		System.out.println(Arrays.toString(a));
		
		DoubleLinkedList list = new DoubleLinkedList();
		
		
		for(int node = 1; node <= 10; node += 2){
			list.addNode(node);
		}
		
		list.insertNode(2);
		
		System.out.println(list.toString());
		
		list.reverseList();
		
		System.out.println(list.toString());
		
		
		//double n = 10.0;
		//double result = 1.0;

		//int doubleCount = 0;
		//if (n %2 == 0)
	//		n = n-1;
		//for (double i = 3.0; i <= n; i+=2){
	//			if (doubleCount%2 == 0){
		//			result = result - 1.0/i;
	//			}
	//			else{
	//				result = result + 1.0/i;
	//			}
	//			doubleCount ++;
	//	}
	//	
	//	System.out.print(result);
	}
	
	

}
