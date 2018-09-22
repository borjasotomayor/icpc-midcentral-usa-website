/* 2004 ACM Mid-Central Regional Programming Contest
   Problem F: Blots
   by Andy Harrington, Loyola University Chicago

blots.java

Problem:
 Distinct circles are specified by their center and radius, positive integers
 no more than 1,000,000.  No circles are tangent and no three intersect at one
 point.  They represent the outer boundaries of disks filled with black ink
 blots on a white surface that extends beyond all the blots.  How many distinct
 white regions are determined by these blots?

 Parameters are set so double arithmetic accurately determines which circles
 intersect and the order of the intersection points around any circle:
   circles overlap or are separared by at least one
   all angles of intersection on any one circle differ by at least .001 radian

Algorithm:
 1. Read all circles (at most 100)
 2. Eliminate all circles inside another, and any intersecting no other circle
 3. For each circle left
     for each previous circle in the list
      if they intersect
        mark them as in the same component
        remove arcs and parts of arcs in the interection with the other circle
        link arcs with a common endpoint in the other circle
 4. Count boundary components:
    For each unmarked arc in each circle
      increment the boundary count
      follow the links to traverse the whole boundary, marking each arc
 5. Return the number of white regions:  1 for the outer white region +
      the number of closed arc paths – the number of connected black components.
 The final subtraction is because each connected black component has one outer
 boundary, which does not contribute to the number of white regions.

Math:  If circles
   C1 with center (x1, y1) and radius r1
   C2 with center (x2, y2) and radius r2
 intersect at exactly 2 points and
    s is the distance between their centers, let
    A = atan2(y2-y1,x2-x1)
    B = acos((r1^2 + s^2 - r2^2)/(2*r1*s))  : law of cosines
 Then the intersection points on C1 are at angles A+B and A-B.

 The angles removed are the interval (A-B, A+B).  Always 0 < B < pi.
 The part saved from an initial whole circle is [A+B, A-B + 2pi].

 After considering the first intersection of C1, the saved part lies in
 some interval of length less than 2pi.  We keep that situation as follows:
 We maintain the arc endpoint angles in an increasing sequence, from a to b,
 with a < b <= a + 2pi - .001.  Let k = a - .001/2, then the endpoint angle
 also lie in increasing sequence in the range I = [k, k+2pi) and there is no
 boundary point at k or k+2pi.  The intersection interval with another circle
 may be stated initially as [c', d'], which can be shifted by a multiple of 2pi
 to [c, d] so c is in I.  If d is in I, then there is no issue of distinctions
 mod 2pi.  If d is not in I, then the part to be removed can be considered in
 two parts: [k, d-2pi] and [c, k+2pi), both within I for easy comparisons.
 When d > k+2pi, the program actually replaces k+2pi with d, since nothing more
 is removed.

 By uncommenting the display line, the input can be viewed graphically.
 In that version, closing any one display window terminates the program.
 To see other displays, working backwards through the datasets, move or
 minimize the one on top.  The displays may be resized which rescales and can
 show more detail.  The display uses the included file DisplayCircles.java.
*/

import java.io.*;
import java.util.*;

public class blots {

  static final int MAX_CIRCLES = 100;
  static final double PI = Math.PI;
  static final double ANG_CONV = 1.0;  // make it 180/PI to view in degrees
  static final double PERIOD = ANG_CONV*PI*2;
  static final double MIN_SEP = 0.001*ANG_CONV;
  static Disk[] disk = new Disk[MAX_CIRCLES];
  static int nDisks;
  static int dataSet = 0;

  public static void main(String[] arg) {
    String FILE = "blots";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }
    nDisks = in.intRead();
    int i, j;
    while ( nDisks != 0) { // one dataset per loop
      dataSet++;
      for (i = 0; i < nDisks; i++) // get whole dataset
        disk[i] = new Disk(in.intRead(), in.intRead(), in.intRead(), i);
      dataIntegrityCheck();  // This line is not needed in student solutions
      // UNCOMMENT the next line to see all the datasets displayed graphically
//      (new DisplayCircles(disk, nDisks, dataSet)).setVisible(true);
      i = 0;
      while (i < nDisks) // remove nested and isolated disks from the list
        if (inside(i) || outside(i)) {
          nDisks--;
          disk[i] = disk[nDisks];
        }
        else
          i++;
      // now each circle intersects at least one other
      int totComponents = nDisks;
      for (i = 1; i < nDisks; i++) { // process intersecions
        Disk c1 = disk[i];
        for (j = 0; j < i; j++) {
          Disk c2 = disk[j];
          double d = c1.centerDist(c2);
          if (d > c1.r + c2.r)
            continue;   // no intersection for these two disks

          if (c2.component != c1.component) { // merge components
            totComponents--;
            int toChange = c2.component;
            for (int k = 0; k < i; k++)  // quick and dirty merging
              if (disk[k].component == toChange)
                disk[k].component = c1.component;
          }
          Arc[] startEnd1 = c1.reduceArcs(c2);
          Arc[] startEnd2 = c2.reduceArcs(c1);
          if (startEnd1[1] != null) // link intersecting arcs
            startEnd1[1].next = startEnd2[0];
          // This may set a next field to null if c has already been intersected
          //   with a circle c3, removing the point.  Untimately that endpoint
          //   will also be removed from c1, when c3 or some other circle gets
          //   intersected with c1.  The final boundary arcs left will share
          //   endpoints with other arcs that are left, and none of their next
          //   fields will be null.
          if (startEnd2[1] != null)
            startEnd2[1].next = startEnd1[0];
        } // inner disk loop
      } // outer disk loop

      // traverse and count all the boundary paths
      int boundaryCount = 0;
      for (i = 0; i < nDisks; i++)
        for(j = disk[i].arcs.size()-1; j >= 0; j--) {
          Arc arc = (Arc)disk[i].arcs.get(j);
          if (!arc.counted) {
            boundaryCount++;
            do {
              arc.counted = true;
              arc = arc.next;
            } while (!arc.counted);
          }
        }

      out.println(1 + boundaryCount - totComponents);
      nDisks = in.intRead();
    } // end of dataset
    out.close();
  }

  // true if disk[n] is totally inside any other disk
  static boolean inside(int n) {
    Disk c = disk[n];
    for (int i = 0; i < nDisks; i++)
      if (i != n &&  c.centerDist(disk[i]) + c.r < disk[i].r)
        return true;
    return false;
  }

  // true if disk[n] is totally outside every other disk
  static boolean outside(int n) {
    Disk c = disk[n];
    for (int i = 0; i < nDisks; i++)
      if (i != n &&  c.centerDist(disk[i]) < c.r + disk[i].r)
        return false;
    return true;
  }

  //-------------------------------------------------------------------------
  static class Arc {
    double a1, a2; // arc includes angles [a1, a2]
    Arc next; // arc on another circle with the point at a2 of this arc as initial point
    boolean counted; // true if this arc has been traversed in a final boundary

    Arc(double ang1, double ang2, Arc next) {
      a1 = ang1;
      a2 = ang2;
      this.next = next;
    }
  } // end of inner class Arc

  //-------------------------------------------------------------------------
  public static class Disk {
    int component;
    double x, y, r;
    ArrayList arcs; // arcs in boundary
                    // null if whole circle; empty if no boundary left
                    // all angles in increasing order
                    // angle of end of final arc < PERIOD + start of initial arc

    Disk(double xx, double yy, double rr, int comp) {
      x = xx;
      y = yy;
      r = rr;
      component = comp;
    }

    double centerDist(Disk c) { // distance between centers
      double dx = c.x - x, dy = c.y - y;
      return Math.sqrt(dx * dx + dy * dy);
    }

    // angle range in this circle of the intersection with c
    double[] angRange(Disk c) {
      // Apply law of cosines to the triangle whose vertices are
      //  an intersection point and both centers.
      double d = centerDist(c);
      double midAngle = Math.atan2(c.y - y, c.x - x) * ANG_CONV;
      double halfAngle = Math.acos((r*r + d*d - c.r*c.r)/(2*r*d)) * ANG_CONV;
      return new double[] {midAngle - halfAngle, midAngle + halfAngle};
    }

    // Remove from the remaining boundary arcs on this circle everything inside
    //   the intersection with c.
    // Return the arcs whose initial or final point intersects c.
    // Return null where the intersection point with c is not in a boundary arc.
    Arc[] reduceArcs(Disk c) {
      // find the arc of intersection: the part to delete
      double[] ang = angRange(c);
      if (arcs == null) { // still whole circle
        arcs = new ArrayList();
        Arc arc = new Arc(ang[1], ang[0] + PERIOD, null); //complement of gap
        arcs.add(arc);
        return new Arc[] {arc, arc};  // both ends are intersection points
      }
      // this boundary has 0 or more arcs left
      Arc[] startEnd = new Arc[2]; // for arcs with start or end intersection pt
      // find range for all arc angles a:  branchCut < a < branchCut + PERIOD
      double branchCut = ((Arc)arcs.get(0)).a1 - MIN_SEP/2;
      double branchShift = PERIOD*Math.floor((ang[0]-branchCut)/PERIOD);
      ang[0] -= branchShift; // now in [branchCut, branchCut+PERIOD)
      ang[1] -= branchShift;   // ang[1] right in relation to ang[0]
      if (ang[1] > branchCut + PERIOD)
        // The full arc to be deleted wraps and is split in two at branchCut.
        //   No arc is assigned to startEnd[1],
        //   since the branchCut angles were arranged to be in no arc.
        cutGap(branchCut, ang[1] - PERIOD, startEnd);
      cutGap(ang[0], ang[1], startEnd);
      return startEnd;
    }

    // Remove from boundary arcs any angle a in (startDel, endDel).
    // This interval is set so no further modifications mod 2pi are needed.
    // If the angle startDel becomes the final pt of an arc, startEnd[1] is
    //   set to the arc; similarly for startEnd[0] if endDel is an intial pt.
    void cutGap(double startDel, double endDel, Arc[] startEnd) {
      if (arcs.isEmpty())
        return;
      int i = 0;
      Arc arc = (Arc)arcs.get(i);
      while(arc.a2 < startDel) {  // skip what is completely before the deletion
        i++;
        if(i == arcs.size())
          return;
        arc = (Arc)arcs.get(i);
      }
      if (arc.a1 < startDel) { // chop starting in arc, into 1 or 2 pieces
        double oldA2 = arc.a2;
        arc.a2 = startDel;
        startEnd[1] = arc;
        if (endDel < oldA2) {  // middle to be removed from arc
          // It is important that the kept and modified arc is the one that
          //   includes the initial point and the new arc is after it, so the
          //   linked list can be maintained.
          Arc newArc = new Arc(endDel, oldA2, arc.next);
          startEnd[0] = newArc;
          arcs.add(i+1, newArc);
          return;  // came to the end of the deletion
        }
        i++;  // pass this (single if the code reached here) chopped arc
        if (i == arcs.size())
          return;
        arc = (Arc)arcs.get(i);
      }
      while(arc.a2 < endDel) {  // arcs to totally remove
        arcs.remove(i);  // quick to code and fast enough
        if(i == arcs.size())
          return;
        arc = (Arc)arcs.get(i); // next arc after renumbering
      }
      // at the last arc that might be affected -- possibly chop its start
      if (arc.a1 < endDel) {
        arc.a1 = endDel;
        startEnd[0] = arc;
      }
    }
  }  // end of inner class Disk

  // The rest is judge's data test, not needed in a student solution -----------
  static void dataIntegrityCheck() {
    double[] a = new double[2 * MAX_CIRCLES + 1];
    for (int i = 0; i < nDisks; i++) {
      Disk c1 = disk[i];
      int nAng = 0;
      for (int j = 0; j < nDisks; j++) {
        if (i == j)
          continue;
        Disk c2 = disk[j];
        double d = c1.centerDist(c2);
        if (i < j && Math.abs(c1.r + c2.r - d) < 1.2) // strengthen restrictions
          System.out.println("Bad radii: " + dataSet + " " + i + " " + j);
        if (d > c1.r + c2.r)
          continue; // no intersection for these two circles
        if (d == 0.0 && c1.r == c2.r)
          System.out.println("Duplicates: " + dataSet + " " + i + " " + j);
        double[] ang = c1.angRange(c2); // abs val of angles < 1.5*PERIOD
        for (int k = 0; k < 2; k++) { // make -PERIOD/2 < angle <= PERIOD/2
          if (ang[k] <= -PERIOD/2)
            ang[k] += PERIOD;
          if (ang[k] > PERIOD/2)
            ang[k] -= PERIOD;
          a[nAng] = ang[k];
          nAng++;
        }
      }
      Arrays.sort(a, 0, nAng);
      a[nAng] = a[0] + PERIOD; // so can compare on either end
      for (; nAng > 0; nAng--)
        if (a[nAng] - a[nAng - 1] <= MIN_SEP*1.2) // strengthen restrictions
          System.out.println("close intersections: " + dataSet + " " + i);
    }
  } // end of judge's data integrity check
} // end of class blots
