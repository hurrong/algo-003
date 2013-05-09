import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class CountComparisonsQuickSort {
	public static final int MAX_LINES = 10000;
	public static int[] input = new int[MAX_LINES];
	public static int numComparisons = 0;
	
	public static void main(String[] args) {
		readInput("QuickSort.txt", MAX_LINES);
		numComparisons = 0;
		qsort(input, 0, MAX_LINES-1);
		System.out.println(Arrays.toString(input));
		System.out.println("Number of comparisons: " + numComparisons);
	}

	private static void qsort(int[] array, int l, int r) {
		if (l >= r) {
			return;
		}
		numComparisons += r-l;
		int medianIdx = findMedian(array, l, r);
		int tmp  = array[medianIdx];
		array[medianIdx] = array[l];
		array[l] = tmp;
		int pivot = array[l];
		int i = l+1;
		for (int j=l+1; j <= r; j++) {
			if (array[j] < pivot) {
				tmp = array[i];
				array[i] = array[j];
				array[j] = tmp;
				i++;
			}
		}
		array[l] = array[i-1];
		array[i-1] = pivot;
		qsort(array, l, i-2);
		qsort(array, i, r);
	}

	private static int findMedian(int[] array, int l, int r) {
		int m = (l+r)/2;
		if (array[l] < array[m]) {
			if (array[m] < array[r]) {
				return m;
			} else if (array[l] < array[r]){
				return r;
			} else {
				return l;
			}
		} else {
			if (array[m] > array[r]) {
				return m;
			} else if (array[l] > array[r]) {
				return r; 
			} else {
				return l;
			}
		}
	}
	
	private static void readInput(String fileName, int linesToRead) {
		try {
			Scanner sc = new Scanner(new File(fileName));
			int cnt = 0;
			while (sc.hasNextInt() && cnt < linesToRead) {
				input[cnt] = sc.nextInt();
				cnt++;
			}
			System.out.println(String.format("total number of integers: %d", cnt));
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}
}
