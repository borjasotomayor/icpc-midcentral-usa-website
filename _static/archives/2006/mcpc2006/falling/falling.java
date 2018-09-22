// 2006 ACM Mid-Central USA Regional Programming Contest
// Solution to "Falling Ice" [hard]
// Andy Harrington, Loyola University Chicago

/* 
Given an integer w, 1 < w < 100, consider the region where 
0 <= x <= w, y >= 0.  Call this region the box.
Individually add n disks of specified diameters, d1, d2, ..., dn.  
1 <= n < 10, where the diameters are positive integers less than w.  

Each disk is imagined to "fall" into the box from above, ending up as low 
as possible without overlapping any disk in place so far.  The added disk 
neither overlaps at its final position nor in a path down from the top.

If the disk can fall all the way to the bottom of the box, then it rolls 
as far left as possible.  The data is chosen so that the lowest position 
is unique whenever it is higher than the bottom.  Also the data is chosen 
so there are no "tight fits": every possible resting point will have 
exactly two contact points with the walls and previously positioned disks.

Once a disk has found its low point, it is "frozen" and makes no further 
moves as later disks are added.  The task is to find the highest point on 
any disk in the box after all disks are added.

Solution algorithm:
Each resting point must be supported from either side of the center, or it 
would fall to a lower place.  Once a disk has landed, any part of the 
boundary between the two contact points are removed, to be replaced by the 
new circle.  
    The walls and circles making up the boundary are kept in a list 
(called BOUNDARY) in an order following along the boundary, always 
starting with the left wall, with zero of more circles next, then possibly 
the bottom wall, and always ending with the right wall.  From the 
discussion above, the circles will always have increasing center x 
coordinates.
    The assumptions on the placement of the next disk allows a single 
'badness' measurement, which increases with the height of the center of 
the disk if it is above the bottom, and decreases further along the bottom 
as the disk rolls to the left.  The resting place will be the unique 
accessible place with the least badness.
    If a new disk enters and could come to rest as a disk at a trial 
position between boundary elements called LEFT and RIGHT with associated 
indices being START and END, then there are the following requirements.  
Call the new disk TRIAL:
  1.  Disks LEFT and RIGHT must be close enough to support TRIAL.
  2.  The boundary elements in between, which have indices between START 
and END, must not overlap TRIAL, and they must go underneath, not over 
TRIAL.  In particular to be underneath TRIAL, the rays from TRIAL toward 
the intervening elements must be less than half a turn counterclockwise 
from the ray from TRIAL to LEFT.
  3.  A disk of TRIAL's diameter must not stick on any other pairs of 
boundary elements, where one part of the pair is no further than LEFT 
along the boundary, and the other is at least as far along as RIGHT.
  4.  Among all such disk positions, choose the one with minimum badness.
    With the new disk detrmined, the BOUNDARY sequence changes to include 
the new disk after LEFT and before RIGHT, and eliminating any BOUNDARY 
elements in between.
    To keep the Java type of each boundary element the same, they are all 
called circles, and real circles have the obvious meaning to the center 
(x, y) and radius r.  There are just three other specific possible 
boundary components, the walls, and they are each identified with a 
specific 'circle' with negative (impossible for a circle) radius. To give 
some useful information in the (x, y) part, a normal vector is stored, 
used in test (2) above, but in all other places they are tested for by 
name, and the special calculation associated with them are worked out.
    Requirement (3) above refers to all possible blocking pairs of 
boundary elements.  Once one legal resting point is found, there can be no 
legal resting points going between elements with indices between START and 
END.  When one legal resting point is found, all such intervening index 
pairs are marked as blocked off in an array alreadyBlocked.  This is all 
done systematically by exploring index pairs furthest apart first, which 
implies a traversal of index pairs along diagonals, with greatest 
separation first.
   To reduce code with special cases for the resting position for a cirle 
between a line and a circle, the calculation is worked out for the y axis 
and a circle, and the cases with the right and bottom walls with a circle are 
generated succinctly by inverse coordinate transformations.
   Judge's checking features are discussed in judges.html.  
   The checking code comes at the end, and has flags you can reset to only 
show what you like.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*; 

public class falling
{
  static int width;
  static List<Circle> boundary;   // in traversal order from left ot right
  static Circle bottomWall = new Circle(0, -1.0, -1); // lines artificial:
  static Circle leftWall = new Circle(-1.0, 0, -2);   //    'center' is
  static Circle rightWall = new Circle(0, 1.0, -3);   //    outward normal 
  static double OkSep = 0; // logically 0 but twiddle to check data robustness
	
  static double otherLeg(double leg, double r) { return sqrt(r*r - leg*leg); }

  static double hypotenuse(double dx, double dy) { return sqrt(dx*dx + dy*dy); }

  static class Circle {
    double x, y, r;
    public Circle(double x_, double y_, double r_) {
      x = x_;
      y = y_;
      r = r_;
    }
	              // for judge's check
    public String toString() {return String.format("(%.6f, %.6f) r = %.6f", x,y,r);}
	    
    public Circle flipSides() { return new Circle(width-x, y, r); }
	    
    public Circle flipXY() { return new Circle(y, x, r); }

    public double badness() { // reflects height and placement rule on the bottom
        if (y > r) return y + width;
        return 2*r + x;
    }
    /** Return the amount of open space between this boundary element and another.
     *  Either this and c are boundary parts, this before c, (possibly sides) 
	 *  or this is a real circle (a candidate for addition).  */
    public double clearance(Circle c) {
    //
 	  if (this == leftWall) { // this never rightWall
	    if (c == rightWall) //c never leftWall
	      return width;
	    if (c == bottomWall)
	      return 0;
        return c.x - c.r;
      }
      if (this == bottomWall) // then c is rightWall, not allow sticking
        return 1.0E200;
      if (c == rightWall) 
        return width - x - r;
      if (c == bottomWall) 
        return y - r;
        
      double sep = hypotenuse(c.x - x, c.y - y) - r - c.r; // real circles
      return sep;
    }
	
    /** Return true if a circle of given radius can pass between this and c. */
	public boolean canPass(Circle c, double radius) {
	  double sep = clearance(c) - 2*radius;
      updateMinSep(sep); // judge's check
      return sep > OkSep; // OkSep is logically 0: twiddle for a judge check
    }
        
	/** return circle of radius rNew that would rest on this circle and c. */  
	public Circle circleRestingOn(Circle c, double rNew) {
	  if (this == leftWall) {
	    if (c == bottomWall) 
	      return new Circle(rNew, rNew, rNew); // first, in corner
	    // center x = rNew, on circle of radius c.r+rNew
	    double yNew = c.y + otherLeg(c.x-rNew, c.r + rNew);    
	    return new Circle(rNew, yNew, rNew);
	  }
	  if (c == rightWall) // reflect to use solution against leftWall
	    return leftWall.circleRestingOn(flipSides(), rNew).flipSides();
	  if (c == bottomWall) // flip to use solution against leftWall
	    return leftWall.circleRestingOn(flipXY(), rNew).flipXY();	       
	  double dx = c.x - x, dy = c.y - y; // case on real circles remains
	  double D = hypotenuse(dx, dy);  // distance between centers
	  double r1 = r + rNew, r2 = c.r + rNew; // distances to new center
	  double E = (r1*r1 - r2*r2 + D*D)/(2*D);// solve intersection if radii r1, r2,
	  double F = otherLeg(E, r1); //            centers (0,0), (D,0): get (E, F)
	           // Now rotate less than 90 degrees and translate to actual centers:
      return new Circle(x + (E*dx - F*dy)/D, y + (F*dx + E*dy)/D, rNew);
	} 
    // used only by alternate algorithm
    public double[] vectorToward(Circle c) { // assume receiver is a real circle
      if (c.r < 0) // wall line
        return new double[] {c.x, c.y}; // normal encoded in 'center'
      return new double[] {c.x - x, c.y - y};
    }

    // true if traverse around this less than 1/2 turn clockwise from c1 to c2 
    public boolean clockwiseTo(Circle c1, Circle c2) {
      double[] v1 = vectorToward(c1), v2 = vectorToward(c2);
      return v1[0]*v2[1] < v1[1]*v2[0]; // use sign of cross product
    }
  } // end of inner class Circle

  public static void addCircle(double r) { // add falling circle of radius r
    int i, lastI = boundary.size() - 1;
    Circle lowCircle = new Circle(0, 1.0e200, 0);  // too high, must be altered
    int indexBeforeLow = 0, indexAfterLow = 0;  // changed with lowCircle
    boolean[][] alreadyBlocked = new boolean[lastI][lastI+1];//impossible pairs
    
    for (int indexSep = lastI - 1; indexSep > 0; indexSep--) // check further apart
      for (int start = 0; start <= lastI - indexSep ; start++) {//index pairs first
        Circle left = boundary.get(start); // left candidate to bump against
        int end = start + indexSep;       
        if (!alreadyBlocked[start][end]) { //You know what is blocked from above
            Circle right = boundary.get(end); // right candidate to bump against
            if (!left.canPass(right, r)) { // if really would block the circle...
              Circle trial = left.circleRestingOn(right, r);
              for (i  = start+1; i < end; i++) { // is it unobstructed below
                Circle between = boundary.get(i);  //   by intervening boundary?
                double sep = trial.clearance(between);
                updateMinSep(sep); // judge's check
                if (sep < OkSep || trial.clockwiseTo(left, between))
                  break; // could not get this far down
              }
              if (i == end) { // no overlaps: landing here is an option!       
                rememberBadness(trial);  // judge's check          
                if (trial.badness() < lowCircle.badness()) { //remember best
                  lowCircle = trial;
                  indexBeforeLow = start;
                  indexAfterLow = end;
                }
                // blocks everything lower down between indices start, end
                for (int j = start; j < end; j++)
                  for (int k = j+1; k <= end; k++)
                    alreadyBlocked[j][k] = true;
              }
            }
          }                            
      } // end finding sticking positions.  Now insert best:
    for (i = indexAfterLow - 1; i > indexBeforeLow; i--) // remove covered boundary
      boundary.remove(i); // backward so index meanings do not change
    boundary.add(indexBeforeLow + 1, lowCircle);
    addCircleCheck(lowCircle);  //judge's check
  }
 
 public static void addCircleB(double r) { // add falling circle of radius r
    int lastI = boundary.size() - 1;
    Circle trial[][] = new Circle[lastI+1][lastI+1];
    double bad[][] = new double[lastI+1][lastI+1]; // badness of trial
    for (int start = 0; start < lastI; start++) {
      Circle left = boundary.get(start); // left candidate to bump against
      for (int end = start + 1; end <= lastI; end++ ) {
         Circle right = boundary.get(end); // right candidate to bump against
         if (!left.canPass(right, r)) { // if really would block the circle...
           trial[start][end] = left.circleRestingOn(right, r);
           bad[start][end] = trial[start][end].badness();
         } 
      }
    }
    double maxBad[][] = new double[lastI+1][lastI+1];
    for (int i = 0; i <= lastI; i++) {
      int iPrev = max(0,i-1);
      for (int j = lastI; j >= 0 ; j--) {
        int jPost = min(lastI, j+1);
        maxBad[i][j] = max(bad[i][j], max(maxBad[i][jPost], maxBad[iPrev][j]));
      }
    }    
    int indexBeforeLow = 0, indexAfterLow = 1;  
    double lowBadness = 1.0E200;
    for (int start = 0; start < lastI; start++) 
      for (int end = start + 1; end <= lastI; end++ ) 
        if (bad[start][end] > 0 && bad[start][end] < lowBadness &&  
            bad[start][end] == maxBad[end-1][start+1]) {
          lowBadness = bad[start][end]; 
          indexBeforeLow = start;
          indexAfterLow = end;
          rememberBadness(trial[start][end]);  //judge test
        } // end finding sticking positions.  Now insert best:
    for (int i = indexAfterLow - 1; i > indexBeforeLow; i--) // remove covered boundary
      boundary.remove(i); // backward so index meanings do not change
    boundary.add(indexBeforeLow + 1, trial[indexBeforeLow][indexAfterLow]);
    addCircleCheck(trial[indexBeforeLow][indexAfterLow]);  //judge's check
  }
 
  public static void main(String[] args) {
    String file = (args.length > 0) ? args[0] : "falling.in";
    Scanner in = null;
    try {
      in = new Scanner(new BufferedReader(
                       new FileReader(file)));
    } catch(Exception e) {
      System.err.println("Can't open input " + file);
      System.exit(-1);
    }
    if (args.length == 2) OkSep = Double.parseDouble(args[1]);  // judge check
    width  = in.nextInt();
    while (width  > 0) {
      int N = in.nextInt();
      boundary = new ArrayList<Circle>();
      boundary.add(leftWall);
      boundary.add(bottomWall);
      boundary.add(rightWall);
      for (int i = 0; i < N; i++)
        addCircle(in.nextInt()/2.0);  // use radii, not diameters
      double high = 0.0;  // initialize to fine maximum height
      for (Circle c : boundary) 
         if (c.r >= 0)  // if really a circle, not an artificial line
           high = max(high, c.y + c.r);
      System.out.format("%.2f%n", high);
      dataSetCheck(high); //judge's check
      width = in.nextInt();
    }
    finalStats(); // judge's check
  }
  
  // The rest is judge's checking code, not solution code. ///////////////////////

  // flags:   dump what you like!
  static boolean hideFinalStats = false; 
  static boolean hideDataSetStats = true;
  static boolean hideGraphics = true; //false;
  static boolean hideCircleList = true;
  
  // cumulative results
  static int dataSet = 0;
  static double overallMinBadnessDiff = 1.0E200;
  static double overallMinSep = 1.0E200;  
  static double minRoundoffLeeway = 1; 

  static void finalStats() {
    if (!hideFinalStats)
      System.err.format("Overall minimums:%n" +
           "Roundoff Leeway: %.6f; Circle Separation: %.6f; Badness diff: %.6f%n",
           minRoundoffLeeway, overallMinSep, overallMinBadnessDiff);
    if (!hideGraphics)
      System.err.println("!!!! Close a graphics window to kill the program!!!!!");
  }     

  // for a single data set
  static List<Circle> all = new ArrayList<Circle>(); // for graphics
  static double minSep = 1.0E200; 
  static double minBadnessDiff = 100;
  
  static void dataSetCheck(double high) {
    dataSet++;
//    if (!hideGraphics)
//      (new DisplayCircles(width, high, dataSet, all)).setVisible(true); 
    if (!hideCircleList)
      System.err.format("List of all circle positions in order:%n%s%n", all);
    all = new ArrayList<Circle>();
    double roundoffLeeway = 0.005 - abs(high - round(high*100.0)/100.0);   
    minRoundoffLeeway = min(minRoundoffLeeway, roundoffLeeway); 
    overallMinBadnessDiff = min(overallMinBadnessDiff, minBadnessDiff); 
    overallMinSep = min(overallMinSep, minSep);
    if (!hideDataSetStats) 
      System.err.format("dataSet %d minimums:%n" + 
          "Roundoff Leeway: %.6f; Circle Separation: %.6f; Badness diff: %.6f%n",
          dataSet, roundoffLeeway, minSep, minBadnessDiff); 
    minSep = 1.0E200; 
    minBadnessDiff = 100; 
  }

  static void updateMinSep(double sep) {
    if (abs(sep) <= min(minSep, .001))
      System.err.format("sep: %.6f, index: %d%n", sep, all.size());
    minSep = min(minSep, abs(sep));
  }
  
  // for a single circle addition
  static List<Circle> trials = new ArrayList<Circle>(); // for badness diff 

  static void rememberBadness(Circle trial) {
    trials.add(trial);
  }

  static void addCircleCheck(Circle lowCircle) {
    all.add(lowCircle); 
    double minTrialDiff = 1.0E200;
    double lowBadness = lowCircle.badness();
    for (Circle trial: trials)
      if (trial != lowCircle)
        minTrialDiff = min(minTrialDiff, trial.badness() - lowBadness);
    if ( !hideDataSetStats && minTrialDiff <   .01) 
      System.err.format("Low diff: %.6f, %d: %s%n", 
                        minTrialDiff, all.size(), lowCircle);
    minBadnessDiff = min(minBadnessDiff, minTrialDiff); 
    trials = new ArrayList<Circle>();
  } 
  
}
