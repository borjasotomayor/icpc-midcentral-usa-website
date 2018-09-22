// 2002 ACM Mid-Central Regional Programming Contest
// Problem B: Hilbert Curve Intersections
// by Andy Harrington, Loyola University Chicago
/*
hilbert.java

problem:  given integers n, x1, x2, y,
 find the number of times the segment from (x1/2^n, y/2^n) to (x2/2^n, y/2^n)
 intersects H(n), the nth approximation to the Hilbert curve.
 Assume x1 < x2, 0 < n < 31.  Each of x1, x2, and y lie in [0, 2^n].
 Input terminates when n is 0.

An O(n) dynamic programming algorithm:

Though the problem is stated for horizontal segments, in the recursive
solution involving rotated regions, both horizontal and vertical segments
need to be considered.

To keep all number as integers as in the input data, modify the nth Hilbert
curve H(n) to lie in [0, 2^n] X [0, 2^n], so the transformations from H(n) to
H(n-1) only rotate, reflect and translate, but do not scale, and x1, x2, and y
are coordinates without any further scaling.

There are a few simple special cases:  Segments that go up the edges, at 0 or
2^n, cross no segments of H(n).  Segments that go up or across the middle,
cross at most two of the three linking segments, and these checks can be done
directly.  These cases take care of any segment in H(1).

For segments other than the special cases above, in a higher iterate H(n), we
use the recursive definition:  If the segment lies totally within one of the
four quadrants of the square, its crossing count is the same as for its
transformation into H(n-1).  Other segments cross the midline, and can be split
into two pieces in two different quadrants, and both can be reduced to segments
in H(n-1).  The sums are counted recursively in the method crossings, but to
keep the time from being exponential in n, use a standard dynamic programming
table.  In fact the count for no more than 6n different segments need to be
calculated.

Consider segments in two catagories:  those that go all the way across a curve,
and those that go only part way.  At all steps in reducing segments to the
previous Hilbert iterate, there is only one time that a segment can be split into
two segments, neither going all the way across:  the first time it crossses the
middle.  After that, if there is a further split, one piece goes all the way
across its half.  Hence there are no more than 2n segments that do not go all the
way across.  Finally we note that the number of distinct segments needed that go
all the way across a curve is no more that 4n:

Consider a horizontal segment, and imagine all the square regions in H(n) where
there are transformations of H(j) that intersect the segment.  The segment is at
the same distance up from the bottom of each of these smaller squares.  Some
squares involve rotation and reflection, however, so if all these transformations
of H(j) along this line in H(n) were mapped back to H(j), along with the segment
through them, all the segments would end up in one of four positions, each same
distance from an edge.  For all H(j), 1 <= j <= n, this no more than 4n segments.

The Seg class represents a segment and the Hilbert iterate it is associated with.
To keep track of Seg keys for a Hashtable, we must override hashCode and
equals for the Seg class.
*/

import java.io.*;
import java.util.Hashtable;

class hilbert {
  static final int NMAX = 30; // maximum value of n for H(n)
  static final int X = 0, Y = 1; // indices for points stored in array of length 2
  static int[] size = new int[NMAX+1]; //size of the square containing H(n): is 2^n;
  static int[] mid = new int[NMAX+1]; // mid[n] = size[n]/2;

  static class Seg {// records n of H(n) and a horizontal or vertical segment
    int n; // consider H(n) to be magnified to 2^n on a side.
    int[] start, end; // contain the two coordinates of the endpoints
                      // start is directly to the left of or directly under end.
    Seg(int n, int sx, int sy, int ex, int ey) {
      this.n = n;
      start = new int[] {sx, sy};
      end = new int[] {ex, ey};
    }

    int varyCoord() { // return index of the coord. that varies in this segment
      return (start[X] != end[X]) ? X : Y;
    }

    int fixedCoord() { // return index of the coord. that is constant
      return (start[X] == end[X]) ? X : Y;
    }

    int low() { // return the lowest value of the varying coordinate
      return start[varyCoord()];
    }

    int high() { // return the highest value of the varying coordinate
      return end[varyCoord()];
    }

    int fixedVal() { // return the constant value of the fixed coordinate
      return start[fixedCoord()];
    }

    Seg toPrevIterate(){
      // take seg in ONE quadrant in H(n) and replace by equiv. in H(n-1);
      boolean highx = (start[X] >= mid[n] && end[X] >= mid[n]);
      boolean highy = (start[Y] >= mid[n] && end[Y] >= mid[n]);
      ptToPreviousIterate(highx, highy, start);
      ptToPreviousIterate(highx, highy, end);
      if (high() < low()) {
        int[] temp = start;
        start = end;
        end = temp;
      }
      n--;
      return this;
    }

    void ptToPreviousIterate(boolean highx, boolean highy, int[] pt) {
      // contains all the transformational geometry for H(n) to H(n-1)
      // relating the four quadrants with low/high x coords and low/high y coords
      // to H(n-1).
      if (highx) pt[X] = size[n] - pt[X]; //reflect x
      if (highy) { // rotate 90 deg clockwiseabout (0, mid[n])
        int origY = pt[Y];
        pt[Y] = mid[n] - pt[X];
        pt[X] = origY - mid[n];
      }
    }

    int crossings(Hashtable t) { // Return # times H(n) crossed by this seg.
      // direct seg count at either edge or middle:
      if (fixedVal() == size[n] || fixedVal() == 0) return 0;
      if (fixedVal() == mid[n])  { // count connecting segments added
         if (fixedCoord() == X)
            return (low() < mid[n] && high() >= mid[n]) ? 1 : 0;
         return ((low() == 0) ? 1 : 0) + ((high() == size[n]) ? 1 : 0);
      }
      Object o = t.get(this);  // may already be in the table
      if ( o != null) return ((Integer)o).intValue();
      int count = 0;
      Seg seg1 = new Seg(n, start[X], start[Y], end[X], end[Y]); //quick clone
      if (low() < mid[n] && high() > mid[n]){ // if split between two quadrants
        Seg seg2 = new Seg(n, start[X], start[Y], end[X], end[Y]);
        seg1.end[varyCoord()] = mid[n]; // set lower or left piece of seg
        seg2.start[varyCoord()] = mid[n]; // set upper or right piece of seg
        count = seg2.toPrevIterate().crossings(t);
      }
      count += seg1.toPrevIterate().crossings(t);
      t.put(this, new Integer(count));
      return count;
    }

    public boolean equals(Object o) { // needed for Hashtable key
      if (!(o instanceof Seg)) return false; // to be complete
      Seg s = (Seg)o;
      return n==s.n && start[0]==s.start[0] && start[1]==s.start[1] &&
         end[0]==s.end[0] && end[1]==s.end[1];
    }

    public int hashCode() { // needed for Hashtable key
      return n + 5*start[0] + 11111*start[1] + 24579*end[0] + 123456781*end[1];
      // even if this is a bad algorithm, and Hashtable lookup goes to O(n)
      // the total time only goes to O(n^2)
    }
  }

  public static void main(String[] arg) {
    String FILE = "hilbert";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }
    int n;
    for (n=1; n <= NMAX; n++) {
      size[n] = 1 << n;  // 2^n
      mid[n] = size[n]/2;
    }
    n = in.intRead();
    while ( n > 0) {
      int x1 = in.intRead(),
          x2 = in.intRead(),
          y = in.intRead();
      Seg seg = new Seg(n, x1, y, x2, y);
      out.println(seg.crossings(new Hashtable()));
      n = in.intRead();
    }
    out.close();
  }
}
