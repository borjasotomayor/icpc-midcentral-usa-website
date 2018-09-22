/*
  markers.java
  The Mark of a Wizard, MCPC 2012 Problem E
  Java version by Andy Harrington
*/

/* 
Get graph.  
Find min dists by memoizing
Remove all edges not minimal (then no need for lengths any more)
Any node with edge from it removed would need a marker if the node is used.
Recursively mark vertices still in possible use via memoization
Mark others as having large dist
Sort by dist to get reverse topological order
Generate all subsets of marks with few enough marks,
and test each to see if sufficient via reverse topological check
if sufficient, reduce minMarks

Since there is a minimal length path with at most 7 tunnels, there
exists a solution with 7 or less markers. That bounds the number of
possible marker subsets to consider to C(17, 7) + C(17,6) + ... + C(17,0) = 41226.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

class Node implements Comparable<Node> {
   List<Node> next = new ArrayList<Node>(); // posssible next nodes
   int[] wt;
   char name;
   boolean needsMark, canUse, doMark;
      // nextMark[i] is next mark after edge from this to next[i]
   int dist = -1;
   
   void init(Scanner in, Node[] nodes) 
   {
      String seq="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghij";
      name = in.next().charAt(0);
      int n = in.nextInt();
      wt = new int[n];
      for (int i = 0; i < n; i++) {
         next.add(nodes[seq.indexOf(in.next())]);
         wt[i] = in.nextInt();
      }
   }
   
   public int compareTo(Node nd) // allows sort by distance
   {
      return dist - nd.dist;
   }
   
   int getDist()  // basic memoized minimum path length algorithm
   {
      if (dist == -1) {
         dist = 100000;
         int i = 0;
         for (Node nd : next) {
            dist = min(dist, wt[i] + nd.getDist());
            i++;
         }
      }  
      return dist;
   }
   
   void killLong()  //remove edges that are too long
   {
      int k = next.size();
      for (int i = k-1;  i >= 0; i --) // backward allows use of original wt
         if (dist  != wt[i]  + next.get(i).dist) {
            markers.prp(" " + name + next.get(i).name);
            next.remove(i);
         }
      needsMark = next.size() < k;              
   }
   
   void setReachable()  // seach forward for nodes reachable after edge removal
   {
      if (canUse) return;
      canUse = true;
      for (Node nd : next) 
         nd.setReachable();
   }
   
   void setCanUse()  // reverse topological check if node is useable
                     // with current marker choices
   {
      if (! doMark && needsMark) 
         canUse = false;
      else {
         int okTunnels = 0;
         for (Node nd : next)
            if (nd.canUse)
               okTunnels++;
         canUse = okTunnels == next.size()  || okTunnels > 0 && doMark; 
      }
   }

   public String toString()
   {  
      String after = "";
      for (Node nd : next) 
         after += nd.name;
      return String.format("%s: %s, d: %s; nM:%s;  cU: %s",
                              name, after, dist, needsMark, canUse);    
   }
}
   
public class markers {
   static int MAX_STEPS = 7, MAX_EDGES=35;
   static Node[] nodes;  
   static int tot;  // intial count of nodes; later drop unreachable
   static int minMarks;
    
   public static void main(String[] args) throws Exception {
      Scanner in = new Scanner(new File("markers.in"));
      tot = in.nextInt();
      while (tot > 0) {
         nodes = new Node[tot];
         for (int i =0; i < tot; i ++)
            nodes[i] = new Node(); // uninitialized, but can reference
         int totEdges = 0;
         for (Node nd : nodes) {
            nd.init(in, nodes);   // input data initializes node
            totEdges += nd.next.size();
         }
         if (totEdges > MAX_EDGES)
            pr("Too many edges! " + totEdges);
         nodes[tot-1].dist = 0;  // at destination
         int totDist = nodes[0].getDist(); //recursive memoize, find dist to end
         pr("dump edges: ");
         for (Node nd : nodes)
            nd.killLong(); // dump nonoptimal edges
         nodes[tot-1].canUse = true;
         nodes[0].setReachable(); // label all now reachable (canUse) from start
         for (Node nd : nodes) {
            if (!nd.canUse) {
                  nd.dist = 100000;  // will be sorted out of the way
                  tot--;             // one less active Node
            }
            for (Node nxt : nd.next) 
               if (!nd.canUse || !nxt.canUse)
                  prp(" " + nd.name + nxt.name); 
         }
         pr("\nGood edges:");
         for (Node nd : nodes) 
            if (nd.canUse) {
               for (Node nxt : nd.next) 
                  prp(" " + nd.name + nxt.name); 
            }
         pr("");   
         Arrays.sort(nodes); // now reverse topogogical order of tot nodes
//         for (Node nd : nodes)
//            pr(""+nd);
         minMarks = min(MAX_STEPS, tot-1); // could mark a single whole path
         pr(tot + " " + minMarks);
         pr("Marked edge sets:");
         doSubsets(1, 0);
         System.out.println(totDist + " " + minMarks);  
         tot = in.nextInt();
      }
   }
   
   static void doSubsets(int nextMark, int marks) {
      if (marks >= minMarks) return;
      for (int i = 1; i < tot; i++) // see if marks sufficient
         nodes[i].setCanUse();      // set in reverse topo order
      if (nodes[tot-1].canUse) {    // reach starting node?
         minMarks = marks;
         for (int i = 0; i < tot; i++) 
            if (nodes[i].doMark) {
               for (Node nd : nodes[i].next)
                  if (nd.canUse)
                     prp(" " + nodes[i].name + nd.name);
            }
            pr(": " + marks);
         return;
      }
      for (int i = nextMark; i < tot; i++) { // add one more mark
         nodes[i].doMark = true;
         doSubsets(i+1, marks+1);
         nodes[i].doMark = false;
      }
   }
         
   // only judge checks follow
   static boolean DEBUG = true;
    
    
   static void prp(String msg) {
      if (DEBUG) System.err.print(msg);
   }
    
   static void pr(String msg) {
      prp(msg+"\n");
   }
}
