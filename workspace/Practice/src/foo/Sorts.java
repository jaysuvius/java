package foo;

public class Sorts {
	
	public static <T extends Comparable<? super T>> void bubbleSort(T[] list){
		 for(int i = list.length - 1; i > 1; i--){
            
            // The inner loop starts at the beginning of 
            // the array and compares each value next to each 
            // other. If the value is greater then they are 
            // swapped
            
            for(int j = 0; j < i; j++){
                
                // To change sort to Descending change to <
                
                if(list[j].compareTo(list[j+1]) > 0){
                    
                    swap(list, j, j+1);
                    
                    //printHorzArray(i, j);
                    
                }
                
            }
            
        }
	}
	
	private static <T extends Comparable<? super T>> int getIndexOfSmallest(T[] a, int first, int last){
		T min = a[first];
		int indexOfMin = first;
		for (int i = first + 1; i <= last; i++){
			if(a[i].compareTo(min) < 0){
				min = a[i];
				indexOfMin = i;
			}
			
		}
		return indexOfMin;
	}
	
	public static <T extends Comparable<? super T>> void selectionSort(T[] a, int first, int last){
		if(first < last){
			int indexOfNextSmallest = getIndexOfSmallest(a, first, last);
			swap(a, first, indexOfNextSmallest);
			selectionSort(a, first + 1, last);
		}
		
	}
	
	private static <T extends Comparable<? super T>> void insertInOrder(T item, T[] a, int begin, int end){
		if (item.compareTo(a[end]) > 0 || item.compareTo(a[end]) == 0 ){
			a[end + 1] = item;
		}
		else if (begin < end){
			a[end + 1] = a[end];
		}
		else{
			a[end + 1] = a[end];
			a[end] = item;
		}
			
	}
		public static <T extends Comparable<? super T>> void insertionSort(T[] a, int first, int last){
			
			for (int i = 1; i < a.length; i++){
				int j = i;
				T toInsert = a[i];
				
				while((j > 0) && (a[j-1].compareTo(toInsert) > 0  ))
				{
					a[j] = a[j-1];
					j--;
				}
				
				a[j] = toInsert;
			}
			
			
			
			//if (first < last){
				//insertInOrder(a[last], a, first, last - 1);
				//insertionSort(a, first, last - 1);
				
			//}
			
		}
		
		
	public static void quickSort(Integer[] array, Integer left, Integer right){
		if ((right - left)<-0){
			return;
		}
		else{
			
			Integer pivot = array[right];
			
			Integer pivotLocation = partitionArray(array, left, right, pivot);
			
			quickSort(array, left, pivotLocation - 1);
			quickSort(array, pivotLocation + 1, right);
			
		}
		
		
		
	}
		
	public static Integer partitionArray(Integer[] array, Integer left, Integer right, Integer pivot){
		int leftPointer = left - 1;
		int rightPointer = right;
		
		while (true){
			while (array[++leftPointer] < pivot){
				;
				
			}
			while (rightPointer > 0 && array[--rightPointer] > pivot){
				;
				
			}
			
			if (leftPointer >= rightPointer) break;
			
			else {
				swap(array, leftPointer, rightPointer);
			}
			
		}
		
		swap(array, leftPointer, right);
		
		return leftPointer;
		
	}
		
	
	public static <T extends Comparable<? super T>> T binarySearch(T[] a, T val){
		int lowIndex = 0;
		int highIndex = a.length;
		T returnVal = null;
		
		while (lowIndex < highIndex){
			
			int middleIndex = (highIndex + lowIndex) /2;
			
			if (a[middleIndex].compareTo(val) < 0){
				lowIndex = middleIndex + 1;
			}
			else if (a[middleIndex].compareTo(val) > 0){
				highIndex = middleIndex - 1;
			}
			else
				lowIndex = a.length;
				returnVal = a[middleIndex];
		}
		
		return returnVal;
		
	}
	
	
	
	

	private static <T extends Comparable<? super T>> void swap(T[] list, int x, int y){
		T temp = null;
		temp = list[x];
		list[x] = list[y];
		list[y] = temp;
	}
}
