// Laser Tag, MCPC 2011 Problem B
//   Java version by Andy Harrington

/* simple, but not fastest running version: *no* pruning of the tree of line
reflections.
Initially recursively add reflection across all sequences of the *lines* the 
mirror are on.  Then trace paths back from the origin to make sure they hit the 
proper mirror segments and avoid all others.

This could be done with general algebra with or without trig, but I chose to use 
vector parametric equations.

A faster and more sophisticated version would keep track of the angle sector
that hits a sequence of mirror segments without blockages in the middle, 
(not just reflected on the whole line the the mirror is on).
This would prune out most sequences.
 
An alternate would start by shooting in a few angles and testing if the 
function is continuous in between two angles (seeing if the same seqence of 
mirror sides are hit and if no endpoints of mirrors have switched sides of the 
beam path),subdividing the angles if there is not continuity or the rounded
angle values are not the same until two paths with continuity pass on either 
side of the origin after hitting the same sequence of mirrors).
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class laser{
    static final int M = 7, R = 7; // max mirrors and reflections
    static int n, tot;  //number of mirrors, total angles
    static double[][] me = new double[M][2]; // [mirror][x or y] for one end
    static double[][] mv = new double[M][2]; // [mirror][x or y] for second end 
    static boolean[] badAng; //badAng[a] true if a returning shot angle
    static double[][] vp= new double[R+2][2]; //vp[mi] is virtual source pt
                                              //if reflect mi times; vp[0]=(0,0)
    static int[] mseq = new int[R+2]; //index of mirrors in reflection sequence
                                      // start at index 1, ignore index 0
    static double[][] pt = new double[R+2][2]; // vert in polygonal bounce path
                                               // origin at either end   
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("laser.in"));
        mseq[0] = -1; //so matches no mirror
        n = in.nextInt();
        while (n > 0) {
            for (int i = 0; i < n; i++) { 
                me[i][0] = in.nextInt();
                me[i][1] = in.nextInt();
                mv[i][0] = in.nextInt() - me[i][0];
                mv[i][1] = in.nextInt() - me[i][1];
            }
            badAng = new boolean[360];
            tot = 0;
            ap("me", me, n);
            ap("mv", mv , n);
            bounce(0);
            if (tot == 0)
                System.out.println("no danger");
            else {
                String line = "";
                for (int i=0; i < 360; i++)
                    if (badAng[i])
                        line += " " + i;
                System.out.println(line.substring(1));
            }
            n = in.nextInt();
        }
    }

    /** recursive routine handling next bounce, where
        b is the number of previous bounces. Assumes vp[b] 
        contains the apparent source.
    */
    static void bounce(int b) {
        checkForHit(b);
        if (b == R) return;
        for (int mi = 0; mi < n; mi++) {
            if (mi == mseq[b]) continue;
            mseq[b+1] = mi;
            vp[b+1] = reflect(vp[b], me[mi], mv[mi]); //reverse side of mirror
            bounce(b+1);
        }
    }
    
    /** return reflected image of p across line e+tv */
    public static double[] reflect(double[] p, double[] e, double[] v) {
        double[] del = onLine(p, e, -1), //difference
                 comp = onLine(del, v, -dot(del, v)/dot(v, v)); //perp component
        return onLine(p , comp, -2); //reverse side of mirror      
    }

    /** See of path of recorded bounces hits the expected mirrors and misses all 
        other.  If OK, set a bad angle.        
    */
    static void checkForHit(int b) {
        if (b == 0) return;   // can't get back to origin with no bounces
        pt[b+1] = new double[2]; // to origin after pouncing off last mirror
        mseq[b+1] = -1;
//        ap("virtual start points pts:", vp, b+1);
//        prp("Pts on mirrors in reverse:");
        for (int j = b; j >0; j--) { // for each mirror b..1 follow path back 
            double[] del = onLine(pt[j+1], vp[j], -1); //difference
            int mj = mseq[j];
            double[] st = crossParam(vp[j], del, me[mj], mv[mj]);
            if (st == null) {
//                pr(String.format("missed mirror %s%s\n::::", 
//                        vStr(me[mj]), vStr(mv[mj])));
                return; // path does not hit proposed mirror
            }
            pt[j] = onLine(vp[j], del, st[0]); 
            if (mirrorBlocks(j))
                return; //another mirror in the way
//            prp(vStr(pt[j]));
        }
        if (mirrorBlocks(0))
            return; //mirror in the way before supposed first mirror
//        pr("");
        int ang = (int)(round(atan2(pt[1][1], pt[1][0])*180/PI));
        if (ang < 0)
            ang += 360;
        if (!badAng[ang]) {
            ap("ang " + ang, pt, b+1);
            badAng[ang] = true; 
            tot++;
        }         
    }

    /* true if any mirror interior to segment between v+ s*del and v + del 
       s is chosen to gve the location of the previous bounce.
    */
    static boolean mirrorBlocks(int j) {
        double[] del = onLine(pt[j+1], pt[j], -1);  // diff
        for (int mi = 0; mi < n; mi++) {
            if (mi != mseq[j] && mi != mseq[j+1]  && 
                crossParam(pt[j], del, me[mi], mv[mi]) != null)
                return true;
//            if (mi == mseq[j] || mi == mseq[j+1]) continue;
//            double[] st = crossParam(pt[j], del, me[mi], mv[mi]);
//            if (st != null) {
//                pr(String.format("Mirror %d: %s%sblocks path %sto %s\n///", 
//                 mi, vStr(me[mi]), vStr(mv[mi]), vStr(pt[j]), vStr(pt[j+1])));
//                return true;                 
//            }
        }
        return false;
    }
    
    // Vector operations follow
    /** return p +t*v */
    static double[] onLine(double[] p, double[] v, double t) {
        return new double[]{p[0] + t* v[0], p[1] + t * v[1]};
    }

    /*+ Return z component of cross product */    
    static double xprodz(double[] v, double[] w) {
        return v[0]*w[1] - w[0]*v[1];
    }

    /*+ Return dot poduct */    
    static double dot(double[] v, double[] w) {
        return v[0]*w[0] + v[1]*w[1];
    }

    /** Return {s,t} so p + s*v = q + t*w, if 0 <= s, t <= 1: 
        parameters for intersection of segments p to p+v, q to q+w; else null
    */  
    static double[] crossParam(double[] p, double[] v, double[] q, double[] w){
        double vxw = xprodz(v, w);
        if (vxw == 0) {
            return null;
        }
        double s = (xprodz(q, w) - xprodz(p, w))/vxw,
               t = (xprodz(q, v) - xprodz(p, v))/vxw;
        if (0 <= s && s <= 1 && 0 <= t && t <= 1)
            return new double[]{s, t};
        return null;
    }
    
    // only judge checks follow
    static boolean DEBUG = true;
    static final double EPS = 0;  // JUDGE: compare different values
    
    static String vStr(double[] v) {
        return String.format("(%.3f, %.3f) ", v[0], v[1]);
    } 
    static void ap(String prompt, double[][] a, int n) {
        System.err.print(prompt + " ");
        for (int i = 0; i < n; i++)
            System.err.print(vStr(a[i]));
        System.err.println();
    }
    
    static void prp(String msg) {
        if (DEBUG) System.err.print(msg);
    }
    
    static void pr(String msg) {
        prp(msg+"\n");
    }
}
