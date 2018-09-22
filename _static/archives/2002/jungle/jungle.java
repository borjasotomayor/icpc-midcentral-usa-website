// 2002 ACM Mid-Central Regional Programming Contest
// Problem G: Jungle Roads
// by Andy Harrington, Loyola University Chicago
/*
jungle.java

Problem:  given a weighted graph with n vertices, find the cost of the minimum
 spanning tree.  Solution using Kruskal's algorithm:  starting with a totally
 disconnected graph, the given edges are tested in the order from lowest to
 highest weight, and added to the graph if they connect different components.
 In this implementation, components are tracked with the component array.
*/

import java.io.*;
import java.util.*;

class jungle {

  public static void main(String[] arg) {
    String FILE = "jungle";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }

    final int WEIGHT = 0, FROM = 1, TO = 2; // edges right index names
    int[][] edges = new int[75][3];
    // villages are encoded as their label - 'A' (int 0 to n-1)
    int n = in.intRead();
    while ( n > 0) {
      String s = "";
      int v, lastEdge = -1;
      for (int from = 0; from < n-1; from++) {
        int lastTo = -1;  // for checking that input rules followed
        if (from != in.charRead() - 'A') System.out.println("Illegal first char");
        for (int k = in.intRead(); k > 0; k--) {
          int to = in.charRead() - 'A';
          if (to <= from || lastTo >= to) System.out.println("Illegal order");
          lastTo = to;  // reset order check
          lastEdge++;
          edges[lastEdge] = new int[] {in.intRead(), from, to};
        }
      }
      int[] component = new int[n]; // vertices i and j are in the same
                                    // component if component[i] == component[j]
      for (v = 0; v < n; v++)
        component[v] = v;  // start all in separate components
      int tot = 0;
      while (lastEdge >= 0) { // find and process edges from smallest to largest
        int smallI = 0;
        for (int i = 1; i <= lastEdge; i++)
          if (edges[i][WEIGHT] < edges[smallI][WEIGHT])
            smallI = i;
        int[] small = edges[smallI];    // remember smallest
        edges[smallI] = edges[lastEdge]; // close up edge list
        lastEdge--;
        if (component[small[FROM]] != component[small[TO]]) { // can use edge
          tot += small[WEIGHT];
          s += "+" + small[WEIGHT];
          int componentMerged = component[small[TO]];
          for (v = 0; v < n; v++)  // combine components
            if (component[v] == componentMerged)
              component[v] = component[small[FROM]];
        }
      }
      out.println(tot);
      System.out.println(tot + " = " + s);
      n = in.intRead();
    }
    out.close();
  }
}
