import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class MedianMaintenance {
	public static final int MAX_LINES = 10000;
	public static final TreeSet<Integer> highHeap = new TreeSet<>();
	public static final TreeSet<Integer> lowHeap = new TreeSet<>();
	public static final List<Integer> medians = new ArrayList<>(MAX_LINES);
	
	public static void main(String[] args) {
		readInput("Median.txt", MAX_LINES);
		Integer answer = 0;
		for (Integer median : medians) {
			answer = (answer + median) % MAX_LINES;
		}
		System.out.println("The last four digits of the sum of medians is: " + answer);
	}
	
	private static void readInput(String fileName, int linesToRead) {
		try {
			Scanner sc = new Scanner(new File(fileName));
			medians.add(sc.nextInt());
			lowHeap.add(medians.get(0));
			System.out.println("Index 0: " + medians.get(0));
			int cnt = 1;
			int n, x;
			while (sc.hasNextInt() && cnt < linesToRead) {
				n = sc.nextInt();
				if (n > lowHeap.last()) {
					highHeap.add(n);
				} else {
					lowHeap.add(n);
				}
				if (lowHeap.size() > highHeap.size() + 1) {
					x = lowHeap.last();
					lowHeap.remove(x);
					highHeap.add(x);
				} else if (lowHeap.size() < highHeap.size() - 1) {
					x = highHeap.first();
					highHeap.remove(x);
					lowHeap.add(x);
				}
				if (lowHeap.size() >= highHeap.size()) {
					medians.add(lowHeap.last());
				} else {
					medians.add(highHeap.first());
				}
				System.out.println(String.format("Index %d: median=%d, n=%d", cnt, medians.get(cnt), n));
				cnt++;
			}
			System.out.println("Total number of integers read: " + cnt);
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}
}
