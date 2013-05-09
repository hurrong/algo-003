import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class HashInt2Sum {
	public static final int MAX_LINES = 500000;
	public static final TreeSet<Integer> mainSet = new TreeSet<Integer>();
	public static final Set<Integer> duplicateSet = new HashSet<Integer>();
	public static Integer MIN = Integer.MAX_VALUE;
	public static Integer MAX = 0;
	
	public static void main(String[] args) {
		readInput("HashInt.txt", MAX_LINES);
		int cnt = 0;
		for (int sum = 2500; sum <= 4000; sum++) {
			Set<Integer> headSet = mainSet.headSet(sum);
			for (Integer x : headSet) {
				if (2*x != sum && mainSet.contains(sum-x)) {
					System.out.println(String.format("sum=%d is achievable. x=%d, y=%d", sum, x, sum-x));
					cnt++;
					break;
				}
			}
		}
		System.out.println("The number of target values in [2500, 4000] that can be achieve is: " + cnt);
	}
	
	private static void readInput(String fileName, int linesToRead) {
		try {
			Scanner sc = new Scanner(new File(fileName));
			int cnt = 0;
			Integer min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
			while (sc.hasNextInt() && cnt < linesToRead) {
				Integer n = sc.nextInt();
				if (n < 4000) {
					if (mainSet.contains(n)) {
						duplicateSet.add(n);
					} else {
						mainSet.add(n);
					}
				}
				if (n < MIN) {
					MIN = n;
				} else if (n > MAX) {
					MAX = n;
				}
				cnt++;
			}
			for (Integer x : mainSet) {
				System.out.println("main: " + x);
			}
			for (Integer n : duplicateSet) {
				System.out.println("duplicate: " + n);
			}
			System.out.println(String.format("Total number of positive integers < 4000 in main set: %d.", mainSet.size()));
			System.out.println(String.format("Min: %d. Max (input): %d. Max (mainSet): %d", MIN, MAX, mainSet.last()));
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}
}
