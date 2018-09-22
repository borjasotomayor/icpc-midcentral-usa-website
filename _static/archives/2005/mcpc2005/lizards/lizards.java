// 2005 ACM Mid-Central Regional Programming Contest
// Problem H: Leaping Lizards
// Posed by Tom Capaul, Eastern Washington University
// Coded by Javier Fernandez-Ivern
// Hacked by the MidCentral judges for I/O consistency

import java.io.*;
import java.util.*;

public class lizards {
    private static final String INPUT_FILE = "lizards.in";

    public int solve(String[] map, String[] lizards, int maxLeap) {
	CapacityGraph cap = new CapacityGraph(map, lizards, maxLeap);
	return cap.getNumLizards() - cap.maxFlow();
    }

    public static void main(String[] args) throws Exception {
	lizards leapingLizards = new lizards();
	BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));

	int numCases = Integer.parseInt(br.readLine().trim());

	for (int i = 1; i <= numCases; ++i) {
	    StringTokenizer st = new StringTokenizer(br.readLine().trim());
	    int numRows = Integer.parseInt(st.nextToken());
	    int maxLeap = Integer.parseInt(st.nextToken());

	    String[] map = new String[numRows];
	    for (int j = 0; j < numRows; ++j) {
		map[j] = br.readLine().trim();
	    }

	    String[] lizards = new String[numRows];
	    for (int j = 0; j < numRows; ++j) {
		lizards[j] = br.readLine().trim();

                // Consistency checks
                if (lizards[j].length() != map[j].length()) {
                   System.err.println("OOPS - map lengths differ on row "+j);
                }
                // Check that lizards are on pillars
                for (int k=0; k<lizards[j].length(); k++) {
                   if (lizards[j].charAt(k) == 'L' && map[j].charAt(k) == '0') {
                      System.err.println("OOPS - Lizard but no pillar -" +
                                         "row "+j+", col "+k);
                   }
                }
	    }

	    int solution = leapingLizards.solve(map, lizards, maxLeap);

	    System.out.print("Case #" + i + ": ");
	    if (solution == 0) {
		System.out.println("no lizard was left behind.");
	    } else if (solution == 1) {
		System.out.println("1 lizard was left behind.");
	    } else {
		System.out.println(solution + " lizards were left behind.");
	    }
	}
    }

    private class CapacityGraph {
	private static final int INF = 1000000000;
	private static final int SOURCE = 0;
	private static final int SINK = 1;

	private int numLizards;
	private int numNodes;
	private int[][] cap;

	public CapacityGraph(String[] map, String[] lizards, int maxLeap) {
	    int numRows = map.length;
	    int numCols = map[0].length();
	    int maxLeap2 = maxLeap * maxLeap;

	    numNodes = numRows * numCols * 2 + 2;
	    cap = new int[numNodes][numNodes];

	    for (int i = 0; i < numNodes; ++i) {
		Arrays.fill(cap[i], 0);
	    }

	    numLizards = 0;
	    for (int row = 0; row < numRows; ++row) {
		for (int col = 0; col < numCols; ++col) {
		    // Split each node so we can have node capacities.
		    int in = inNode(row, col, numCols);
		    int out = outNode(row, col, numCols);
		    // Connect each in-node to its corresponding out-node.
		    cap[in][out] = map[row].charAt(col) - '0';
		    // Connect each lizard to the source.
		    if (lizards[row].charAt(col) == 'L') {
			cap[SOURCE][in] = 1;
			++numLizards;
		    }

		    // Connect pillars within maxLeap of the edge to the sink.
		    if ((row < maxLeap || row > numRows - 1 - maxLeap  // condition corrected
			 || col  < maxLeap || col > numCols - 1 - maxLeap)) {
			cap[out][SINK] = INF;
		    }
		    // Connect each pillar to other pillars within leaping
		    // range.
		    for (int i = 0; i < numRows; ++i) {
			for (int j = 0; j < numCols; ++j) {
			    if (((row != i || col != j)
				 && distance2(row, col, i, j) <= maxLeap2)) {
				cap[out][inNode(i, j, numCols)] = INF;
			    }
			}
		    }
		}
	    }
	}

	private int inNode(int row, int col, int numCols) {
	    return (row * numCols + col) * 2 + 2;
	}

	private int outNode(int row, int col, int numCols) {
	    return (row * numCols + col) * 2 + 3;
	}

	private int distance2(int x1, int y1, int x2, int y2) {
	    int dx = x2 - x1;
	    int dy = y2 - y1;
	    return dx * dx + dy * dy;
	}

	public int maxFlow() {
	    int[][] flow = new int[numNodes][numNodes];
	    for (int i = 0; i < numNodes; ++i) {
		for (int j = 0; j < numNodes; ++j) {
		    flow[i][j] = 0;
		}
	    }

	    while (augment(flow) > 0) {
		// Do nothing.
	    }

	    int result = 0;
	    for (int i = 0; i < numNodes; ++i) {
		result += flow[SOURCE][i];
	    }

	    return result;
	}

	private int augment(int[][] flow) { // JDK1.5 version - no warnings
	    LinkedList<Integer> fringe = new LinkedList<Integer>();
	    HashSet<Integer> seen = new HashSet<Integer>();
	    int[] parent = new int[numNodes];

	    fringe.addLast(SOURCE);
	    seen.add(SOURCE);
	    Arrays.fill(parent, -1);

	    while (!fringe.isEmpty()) {
		int u = fringe.getFirst();
		fringe.removeFirst();

		if (u == SINK) {
		    int augmentBy = INF;

		    int n = u;
		    while (n != SOURCE) {
			augmentBy =
			    Math.min(augmentBy, (cap[parent[n]][n]
						 - flow[parent[n]][n]));
			n = parent[n];
		    }

		    n = u;
		    while (n != SOURCE) {
			flow[parent[n]][n] += augmentBy;
			flow[n][parent[n]] -= augmentBy;
			n = parent[n];
		    }

		    return augmentBy;
		}

		for (int v = 0; v < numNodes; ++v) {
		    if (cap[u][v] - flow[u][v] > 0) {
			if (!seen.contains(v)) {
			    fringe.addLast(v);
			    seen.add(v);
			    parent[v] = u;
			}
		    }
		}
	    }

	    return 0;
	}

	public int getNumLizards() {
	    return numLizards;
	}
    }
}
