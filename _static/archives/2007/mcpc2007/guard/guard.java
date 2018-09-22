// 2007 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem G: "Guard" [moderate/hard]
// Andy Harrington, Loyola University Chicago


/* Guard

Basic idea:  The number of possible places that guards might be 
places can be limited to p(p+1)/2, and then all combinations of 
g guards can be tested recursively.  With the problem limiting
c to 11 and g to 4, the number of combinations <= 66C4 = 720720.

Details of determining the places:  All the intersections and 
places where there are valuables are possibilities.  These are a 
subset of the p labeled points.  If a guard is not placed at one 
of these points and minimizes the risk for one item that has the 
maximum risk for the whole site, then the guard could be moved 
closer unless there is another item being guarded by this person 
with the same risk.  (It cannot be higher, since the assumption 
was the first item caused the maximum risk.  It could not be 
less or it would not inhibit the guard from moving closer to the 
first item.)  The risk formula is a moment with the value as 
weight, so the point between two items balancing the risk is the 
centroid (center of mass).  Thus the further points to be considered 
are the centroids of pairs of distinct points with items that are 
along the same corridor.  There would be the most places to check if 
all p items were on one corridor, with p(p-1)/2 centroid locations.  
Adding the original p locations the most to consider is p(p+1)/2.

In the solution the array of points pt, does double duty:  The 
first p location are the possible locations of valuables, and 
are referenced when distances to valuables are considered.  Then 
the rest of the locations used in the array are taken by the 
centroids, so all positions used in the array contains possible 
locations for guards.  One listed location might balance maximal risks 
for more than one pair of items, but that will just make the point 
appear more than once in the list, which does not hurt.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*; 

public class guard
{
  static int p, c, g;  //  number of points specified, corridors, guards
  static final int PMAX = 11, CMAX = 11, GUARD_TRY_MAX = (PMAX+1)*PMAX/2;
  static final double BIG = Double.MAX_VALUE;
  static int totPt; //total number of points to try guards at
  static double[][] pt = new double[GUARD_TRY_MAX][2]; //space for these points
  static int[] v = new int[PMAX]; // values at first p points in pt
        // dist[i][j] is distance from guard at pt[i] to valuable at pt[j]
  static double[][] dist = new double[GUARD_TRY_MAX][PMAX]; //BIG if not visible  
  static double minmax;  // best answer so far from placing g guards 

  public static void main(String[] args) throws Exception {
    String file = (args.length > 0) ? args[0] : "guard.in";
    Scanner in = new Scanner(new File(file));
    p  = in.nextInt();
    while (p  > 0) {
      c = in.nextInt();
      g = in.nextInt();
      for (int i = 0; i < p; i++) {
        in.next(); // skip letter
        pt[i] = new double[] {in.nextInt(), in.nextInt()};
        v[i] = in.nextInt();
      }
      for (double[] row : dist) // Max out distances to start.  Pairs  
        Arrays.fill(row, BIG);  //   not on the same corridor will stay BIG.
      totPt = p;  // first points where guard can be are the given points
      for (; c > 0; c--) { // process each corridor for more guard points
        int firstOnCorridor = totPt;
        String labels = in.next();
        int[] indices = new int[labels.length()]; // indices in pt labels
        for (int i = 0; i < labels.length(); i++) 
          indices[i] = labels.charAt(i) - 'A';        
        for (int i : indices) // distance for all pairs labeled on corridor
          for (int j : indices)
            dist[i][j] = realDist(pt[i], pt[j]);
        for (int i : indices) //add guard pts at centroid of each pair of values
          if (v[i] > 0)
            for (int j : indices) 
              if (i > j && v[j] > 0) {
                pt[totPt] = new double[2];
                for (int k = 0; k < 2; k++)
                  pt[totPt][k] = (v[i]*pt[i][k] + v[j]*pt[j][k])/(v[i]+v[j]); 
                totPt++;
              }
        for (int i = firstOnCorridor; i < totPt; i++) //centroid-valuable dist
          for (int j : indices)
            dist[i][j] = realDist(pt[i], pt[j]);
      }
      double[] risk = new double[p]; // init for recursive solution
      Arrays.fill(risk, BIG);        // no valuables initially visible
      minmax = BIG;
      placeGuards(0, 0, risk);  // place all combinations of guards recursively
      if (minmax == BIG)
        System.out.println("too few guards");
      else { 
        System.out.format("%.2f%n", minmax);
        judgeChecks();
      }
      p = in.nextInt();
    }
  }
  
  // Assume the combination of guards is at an increasing seq of pt indices.
  // Guards before number nGuard are already placed at indices before iPt
  //  and the guards so far reduce the risk to the item at pt[i] to lastRisk[i].
  //  Add the next guard directly, and the rest recursively.
  static void placeGuards(int iPt, int nGuard, double[] lastRisk) {
    if (nGuard == g) { // all placed; check result
      double largestRisk = 0;  // find largest of the risks
      for (double r : lastRisk)
        if (largestRisk < r)
          largestRisk = r;
      if (largestRisk < minmax) {  // remember if lower
          minmax = largestRisk;
          bestGuardPt = guardPt.clone(); // for judge data only
      }
      return;
    }
    for ( ; iPt <= totPt - (g - nGuard); iPt++) {// try places for next guard
      double[] risk = lastRisk.clone();
      guardPt[nGuard] = iPt;        // for judge check: remember guard's place 
      for (int j = 0; j < p; j++)           // see if this guard reduces risks
        risk[j] = min(risk[j], v[j]*dist[iPt][j]);
      placeGuards(iPt+1, nGuard+1, risk);   // on to next guard
    }      
  }
  
  static double realDist(double[] v, double[] w) {
    double dx = v[0] - w[0], dy = v[1] - w[1];
    return sqrt(dx*dx + dy*dy);
  }

///////// rest is only for judging data checks

  static int[] guardPt = new int[PMAX];  // indices in pt for guard placement
  static int[] bestGuardPt; // indices in pt  (only for judge display)
  static int totSets = 0;

  static void judgeChecks() {
    totSets++;
    double diff = abs(round(minmax*100)/100.0 - minmax);
    if (diff > .0049)
      System.err.format("Poor roundoff for %f: %f%n", minmax, diff);
    if (totSets > 16)
      System.err.format("Too many data sets");
    printGuardPoints();  
  }

  static void printGuardPoints() {
    for (int ig = 0; ig < g; ig++) {
      int i = bestGuardPt[ig];
      System.err.format("(%f, %f) ", pt[i][0], pt[i][1]);
    }
    System.err.println();  
  }
}
