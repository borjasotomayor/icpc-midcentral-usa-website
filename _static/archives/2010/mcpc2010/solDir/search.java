/* Quick Search, MCPC 2010 Problem G by Andy Harrington
input:

Each dataset:
s n p <paths>   Number of sites, searchers, number of connecting paths 
Each path token is a pair of capital letters.

Algorithm:  The input data gives a *site* graph.  
A breadth first search is done over a related *state* graph. In the
state graph a node is a pair of integers encoding the sorted current
sites of searches and set of sites already visited as a bit field:
    The variable length location array is encoded as a single integer, base s.
    Add site i to those visited:   visted |= 1 << i
A state graph edge involves each searcher moving along a path of
the site graph - a possible single time step.

Because of the variable number of searchers, the full collection
of neighbors in the state graph is found recursively.

A boolean array stored which states have been found.  What states needs to be 
traversed (at current distance, at next distance) are stored in Lists.

The breadth-first search terminates when a state is reached with all
sites visited.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class search  // version with big encoded boolean array: found
{
    public static int s, n, p, // # nodes, searchers, edges
        allVisited, // visited bitfield in terminal node  
        MIN_S = 2, MAX_S = 10, MAX_N = 4, MAX_P = 20; //match final problem!
    static boolean[][] edge;  // in original graph
    
    static boolean[][]found; //found[vCode][locCode] true when find state  

    static int locCode(int[] loc) {
        int[] lc = loc.clone();
        Arrays.sort(lc);
        int sum = 0;
        for (int i = n-1; i >= 0; i--) 
            sum = s*sum + lc[i];
        return sum;
    }

    static int[] locCodeToLoc(int lCode) {
        int[] loc = new int[n]; 
        for (int i = 0; i < n; i++) {
            loc[i] = lCode % s;
            lCode /= s;
        }
        return loc;
    }

    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "search.in";
        Scanner in = new Scanner(new File(file));
        while (true) {
            s  = in.nextInt();
            if (s == 0) break;
            n = in.nextInt();
            p = in.nextInt();
            judgeParamCheck();  // just for judges
            edge = new boolean[s][s];
            for (int i = 0; i < p; i++) {
                String e = in.next();
                int from = e.charAt(0) - 'A', to = e.charAt(1) - 'A';
                judgeEdgeCheck(from, to);  // just for judges
                edge[to][from] = edge[from][to] = true;
            }
            System.out.println(calc());                   
        }
    }

    //breadth first search through states
    //returns number of step to first visiting all sites
    static int calc() {
        allVisited = (1 << s) - 1;
        found = new boolean[allVisited+1][(int)pow(s,n)];
        List<int[]> curDist = new ArrayList<int[]>(),//traverse current dist
                       nextDist;            //record neighbors at next distance
        curDist.add(new int[]{1,0}); //all at node 0, only node 0 visited
        found[1][0] = true;
        int steps = 0;
        while(true) {
            nextDist = new ArrayList<int[]>();
            steps += 1;
            for (int[] st : curDist)
                if (doNeighbors(0, locCodeToLoc(st[1]), st[0], nextDist))
                    return steps;
            curDist = nextDist;
        }
    }

    // nextSearcher 0 .. n-1  recursively find neighbor for nextSearcher, 
    // nextSearcher = n; all searcher set:
    //       return true if find terminal state
    //       or add to next search distance
    static boolean doNeighbors(int nextSearcher, int[] at, int visited, 
                               List<int[]> nextDist) {
        if (nextSearcher == n) {
            for (int i : at)
                visited |= 1 << i;
            if (visited == allVisited)   
                return true;
            int lCode = locCode(at);
            if (!found[visited][lCode]) {
                nextDist.add(new int[]{visited, lCode});
                found[visited][lCode] = true;
            }
            return false;
        }
        int from = at[nextSearcher];
        for (int to = 0; to < s; to++) 
            if (edge[from][to]) {
                at[nextSearcher] = to;
                if (doNeighbors(nextSearcher+1, at, visited, nextDist))
                    return true;
            }
        at[nextSearcher] = from; // now at[nextSearcher] in the original call
        return false;
    }

    // The rest is judge's checks.

    static void judgeParamCheck() {
        if (MIN_S > s || MAX_S < s || MAX_N < n || MAX_P < p)
            System.out.format("param out of range!");
    }

    static void judgeEdgeCheck(int from, int to) {
        if (from >= to) System.out.println("Wrong order!");     
        if (edge[to][from]) System.out.println("Repeated edge!");
    }
}

