package foo;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class PracticeTests {

	/**
	 * Test method for {@link foo.Sorts#bubbleSort(int[])}.
	 */
	@Test
	public void testBubbleSort() {
		//Setup
		Integer[] a = {19, 16, 15, 14, 14, 12, 11, 10, 13, 16};
		Integer[] b = Arrays.copyOf(a, a.length);
		Arrays.sort(b);
		//Act
		Sorts.bubbleSort(a);
		//Assert
		assertArrayEquals(b, a);	
	}
	
	/**
	 * Test method for {@link foo.Driver#sort(int[])}.
	 */
	@Test
	public void testSelectionSort() {
		//Setup
		Integer[] a = {19, 16, 15, 14, 14, 12, 11, 10, 13, 16};
		Integer[] b = Arrays.copyOf(a, a.length);
		Arrays.sort(b);
		//Act
		Sorts.selectionSort(a, 0, a.length - 1);
		//Assert
		assertArrayEquals(b, a);	
	}
}
