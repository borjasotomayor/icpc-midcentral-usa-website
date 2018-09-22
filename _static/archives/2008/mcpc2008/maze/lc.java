/* Lines and Circles, MCPC 2008 Solution by Andy Harrington
 *
 * pi + t*vi = pj + s*vj
 * (pi + t*vi)Xvj = (pj + s*vj)Xvj  X means z comp of X prod
 * piXvj + t*(viXvj) = pjXvj
 * t = (pj - pi)Xvj/viXvj
 * intersection pt = pi + t*vi
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class lc
{
    static Scanner in;
    static final int MAX_SHAPES = 25;
    static final int MAX_PT = MAX_SHAPES * (MAX_SHAPES + 2);
    static int dataset = 1;
    static double[][] D;// = new double[MAX_PT][MAX_PT];
    static int[] R = new int[MAX_SHAPES];
    static double[][] end0 = new double[MAX_SHAPES][]; //line init endpt
    static double[][] center = end0;                   // or center
    static double[][] end1 = new double[MAX_SHAPES][]; // line endpt2
    static double[][] segV = new double[MAX_SHAPES][];// (endpt1 - endpt0)
    static double[][] uVec = new double[MAX_SHAPES][];// (endpt1 - endpt0)/length
    static boolean[] isLn = new boolean[MAX_SHAPES];
    static double[][] pt = new double[MAX_PT][];
    static ArrayList<ArrayList<Integer>> intercept =
                                               new ArrayList<ArrayList<Integer>>();
    static int pts, shapes;

    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "maze.in";
        in = new Scanner(new File(file));
        char type = in.next().charAt(0);
        while (type  != '*') {
            intercept.clear();
            shapes = 0;
            while (type == 'L' || type == 'C') {
                intercept.add(new ArrayList<Integer>());
                isLn[shapes] = type == 'L';
                end0[shapes] = new double[]{in.nextInt(), in.nextInt()};
                if (isLn[shapes]) {
                    end1[shapes] = new double[]{in.nextInt(), in.nextInt()};
                    segV[shapes] = dif(end1[shapes], end0[shapes]);
                    uVec[shapes] = unit(segV[shapes]);
                }
                else
                    R[shapes] = in.nextInt();
                shapes++;
                type = in.next().charAt(0);
            }
            solve();
            dataset++;
            type = in.next().charAt(0);
        }
    }

    // dot product
    static double dot(double[] v1, double[] v2) {
        return v1[0]*v2[0] + v1[1]*v2[1];
    }

    static double angle(double[] v) {
        return atan2(v[1], v[0]);
    }

    // z coord of cross product
    static double cross(double[] v1, double[] v2) {
        return v1[1]*v2[0] - v1[0]*v2[1];
    }

    // vector difference of two points
    static double[] dif(double[] p1, double[] p2) {
        return new double[] {p1[0] - p2[0], p1[1] - p2[1]};
    }

    // return p +tv
    static double[] shift(double[] p, double t, double[] v) {
        return new double[] {p[0] + t * v[0], p[1] + t * v[1]};
    }

    // normal vector (for when want both so sign does not matter)
    static double[] normal(double[] v) {
        return new double[] {-v[1], v[0]};
    }

    static double length(double[] v) {
        return sqrt(dot(v, v));
    }

    static double[] unit(double[] v) {
        double m = length(v);
        return new double[] {v[0]/m, v[1]/m};
    }

    static void addIntercept(double[] p, int i, int j) {
        if ((!isLn[i] || onSeg(i, p)) && (!isLn[j] || onSeg(j, p)) ) {
            intercept.get(i).add(pts);
            if (i!=j)
                intercept.get(j).add(pts);
            pt[pts] = p;
            pts++;
        }
    }

    // assume p is on line i - now check if in segment
    static boolean onSeg(int i, double[] p) {
        return dot(segV[i], dif(p, end0[i])) >= 0 &&  // right ray1
               dot(segV[i], dif(p, end1[i])) <= 0;  // right ray2
    }

    static void solve() {
        final double BIG = 1000000;
        for (int i = 0; i < shapes; i++) {
          if (isLn[i]) {
              addIntercept(end0[i], i, i);
              addIntercept(end1[i], i, i);
          }
          for (int j = i+1; j < shapes; j++) {
              if (isLn[i] && isLn[j]) {  /// line intersection
                  double denom = cross(segV[i], segV[j]);
                  if (denom != 0) {  // not parallel
                      double t = cross(dif(end0[j], end0[i]), segV[j])/denom;
                      addIntercept(shift(end0[i], t, segV[i]), i, j);
                  }
              }
              else if (!isLn[i] && !isLn[j]) {  // circles
                  double[] dc = dif(center[j], center[i]);
                  double sep = length(dc),
                         s = .5*(sep*sep + R[i]*R[i] - R[j]*R[j])/sep,
                         t2 = R[i]*R[i] - s*s;
                  if (t2 >= 0) {
                      double[] dcUnit = unit(dc),
                               n = normal(dcUnit),
                               mid = shift(center[i], s, dcUnit);
                      addIntercept(shift(mid, sqrt(t2), n), i, j);
                      addIntercept(shift(mid, -sqrt(t2), n), i, j);
                  }
             }
             else { // one of each
                 int ic = i, il = j;
                 if (isLn[i]) {
                     ic = j;
                     il = i;
                 }
                 double t = dot(dif(center[ic], end0[il]), uVec[il]);
                 double[] close = shift(end0[il], t, uVec[il]);
                 double s = length(dif(center[ic], close)),
                         t2 = R[ic]*R[ic] - s*s;
                 if (t2 >= 0) {
                     addIntercept(shift(close, sqrt(t2), uVec[il]), i, j);
                     addIntercept(shift(close, -sqrt(t2), uVec[il]), i, j);
                 }
            }
          }
        }
        D = new double[pts][pts];
        for (int i = 0; i < pts; i++)
            for (int j = 0; j < pts; j++)
                D[i][j] = BIG;
        // find distances between points on same object
        for (int i = 0; i < shapes; i++)
            for (int j : intercept.get(i))
                for (int k : intercept.get(i))
                    if (isLn[i])
                        D[j][k] = length(dif(pt[j], pt[k]));
                    else {
                        double a = abs(angle(dif(pt[j], center[i])) -
                                       angle(dif(pt[k], center[i])));
                        if (a > PI)
                            a = 2*PI - a;
                        D[j][k] = min(D[j][k], R[i]*a);
                    }
        for (int k = 0; k < pts; k++)
            for (int i = 0; i < pts; i++)
                for (int j = 0; j < pts; j++)
                    D[i][j] = min(D[i][j], D[i][k] + D[k][j]);
        double longest = 0;
        for (int i = 0; i < pts; i++)
            for (int j = 0; j < pts; j++)
                if (D[i][j] < BIG && D[i][j] > longest)
                    longest = D[i][j];
        System.out.format("Case %d: %.1f%n", dataset, longest);
    }
}
