import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class CountInversionsMergeSort {
	public static final int MAX_LINES = 100000;
	public static int[] input = new int[MAX_LINES];
	private static long numInversions = 0;
	
	public static void main(String[] args) {
		readInput("inversions.txt");
		numInversions = 0;
		sort(0, MAX_LINES-1);
//		System.out.println(Arrays.toString(sort(0, MAX_LINES-1)));
		System.out.println("Number of inversions: " + numInversions);
	}

	private static void readInput(String fileName) {
		try {
			Scanner sc = new Scanner(new File(fileName));
			int cnt = 0;
			while (sc.hasNextInt() && cnt < MAX_LINES) {
				input[cnt] = sc.nextInt();
				cnt++;
			}
			System.out.println(String.format("total number of integers: %d", cnt));
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}
	
	private static int[] sort(int beginIdx, int endIdx) {
		if (beginIdx == endIdx) {
			return new int[] { input[beginIdx] };
		}
		return merge(sort(beginIdx, (beginIdx+endIdx)/2), sort((beginIdx+endIdx)/2 + 1, endIdx));
	}
	
	private static int[] merge(int[] left, int[] right) {
		int[] result = new int[left.length + right.length];
		int i=0, j=0, rIdx = 0;
		for (; i < left.length && j < right.length; rIdx++) {
			if (left[i] <= right[j]) {
				result[rIdx] = left[i];
				i++;
			} else {
				result[rIdx] = right[j];
				j++;
				numInversions += left.length - i;
			}
		}
		if (i < left.length) {
			System.arraycopy(left, i, result, rIdx, left.length - i);
		} else if (j < right.length) {
			System.arraycopy(right, j, result, rIdx, right.length - j);
		}
		return result;
	}
 }
