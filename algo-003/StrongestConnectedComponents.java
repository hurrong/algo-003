import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class StrongestConnectedComponents {
	private static final int NUM_NODES = 875714;
	private static final Node[] GRAPH = new Node[NUM_NODES];
	private static final Node[] ORDERED_GRAPH = new Node[NUM_NODES];
	private static int PASS = 0;
	private static int t = 0;
	private static int leaderId = 0;
	private static int compSize = 0;
	
	public static void main(String[] args) {
		readInput("SCC.txt");
		System.out.println("Finished reading input graph");
		Long startTime = System.currentTimeMillis();
		for (int i = NUM_NODES - 1; i > 0; i--) {
			if (!GRAPH[i].visited[PASS]) {
				DFS(GRAPH[i]);
			}
		}
		System.out.println(String.format("Finished first pass in %d milliseconds.", System.currentTimeMillis() - startTime));
//		for (Node node : ORDERED_GRAPH) {
//			System.out.println("" + node.t + ": " + node.id);
//		}
		PASS++;
		List<Integer> compSizes = new ArrayList<>();
		for (int i = NUM_NODES - 1; i > 0; i--) {
			if (!ORDERED_GRAPH[i].visited[PASS]) {
				leaderId = ORDERED_GRAPH[i].id;
				DFS(ORDERED_GRAPH[i]);
				compSizes.add(compSize);
				compSize = 0;
			}
		}
//		System.out.println("Node Id: Leader Id");
//		for (Node node : GRAPH) {
//			System.out.println("" + node.id + ": " + node.leaderId);
//		}
		System.out.println("Number of SCCs: " + compSizes.size());
		Collections.sort(compSizes, Collections.reverseOrder());
		for (int i = 0; i < 5; i++) {
			System.out.print("" + compSizes.get(i) + ", ");
		}
	}
	
	public static void DFS(Node node) {
		List<Node> neighbors;
		if (PASS == 0) {
			neighbors = node.ins;
		} else {
			neighbors = node.outs;
		}
		node.visited[PASS] = true;
		for (Node neighbor : neighbors) {
			if (!neighbor.visited[PASS]) {
				DFS(neighbor);
			}
		}
		if (PASS == 0) {
			ORDERED_GRAPH[t] = node;
			node.t = t;
			t++;
		} else {
			node.leaderId = leaderId;
			compSize++;
		}
	}
	
	private static class Node {
		List<Node> outs = new ArrayList<>();
		List<Node> ins = new ArrayList<>();
		int id;
		int t = 0;
		int leaderId = 0;
		boolean[] visited = new boolean[2];
		public Node(int id) {
			this.id = id;
		}
	}
	
	private static void readInput(String fileName) {
		try {
			Scanner sc = new Scanner(new FileInputStream(fileName));
			int cnt = 0;
			while (sc.hasNextInt()) {
				int src = sc.nextInt() - 1;
				int dst = sc.nextInt() - 1;
				Node srcNode, dstNode;
				if (GRAPH[src] == null) {
					srcNode = new Node(src);
					GRAPH[src] = srcNode;
				} else {
					srcNode = GRAPH[src];
				}
				if (GRAPH[dst] == null) {
					dstNode = new Node(dst);
					GRAPH[dst] = dstNode;
				} else {
					dstNode = GRAPH[dst];
				}
				srcNode.outs.add(dstNode);
				dstNode.ins.add(srcNode);
				cnt++;
			}
			System.out.println(String.format("total number of edges: %d", cnt));
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}
}