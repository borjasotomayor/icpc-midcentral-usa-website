/* Bulletin Board, MCPC 2008 Problem E by Andy Harrington
Idea:  If all the coordinates used on each axis are listed in order, 
the rectangles between two successive coordinates in each list partition the
bulletin board space, and each little rectangle is either entirely under a 
poster or does not overlap at all.

Only a simple nested loop is needed to count how many posters cover each such
atomic rectangle.  O(n^3) running time.
*/

import java.util.*;
import java.io.*;

public class bulletin
{
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "bulletin.in";
        Scanner in = new Scanner(new File(file));
        int n  = in.nextInt();
        while (n  > 0) {
            int[] x = new int[2*n+2]; // for all x coordinates used, including edges
            int[] y = new int[2*n+2]; // for all y coordinates used, including edges
            x[2*n+1] = in.nextInt();  // include largest coordinates used
            y[2*n+1] = in.nextInt();
            x[2*n] = y[2*n] = 0; // lowest coordinate - will sort later
            int[] xl = new int[n]; // poster coordinates
            int[] yl = new int[n];
            int[] xh = new int[n];
            int[] yh = new int[n];
            for (int i = 0; i < n; i++) {// save each rect and all coord.
                x[i] = xl[i] = in.nextInt();
                y[i] = yl[i] = in.nextInt();
                x[i+n] = xh[i] = in.nextInt();
                y[i+n] = yh[i] = in.nextInt();
            }
            int nx = uniqueSeq(x); // get sorted sequences of unique elements
            int ny = uniqueSeq(y);
            int clearArea = 0;
            int maxDepth = 0;
            int maxDepthArea = 0;
            for (int i = 1; i < nx; i++) // check each atomic rectangle
                for (int j = 1; j < ny; j++) {
                    int depth = 0;
                    for (int k = 0; k < n; k++) // check each poster
                        if (xl[k] <= x[i-1] && x[i] <= xh[k]  &&
                              yl[k] <= y[j-1] && y[j] <= yh[k]) 
                            depth++;
                    int area = (x[i] - x[i-1])*(y[j] - y[j-1]);
                    if (depth == 0)
                        clearArea += area;
                    else if ( depth == maxDepth)
                        maxDepthArea += area;
                    else if (depth > maxDepth) {
                        maxDepthArea = area;
                        maxDepth = depth;
                    }
                }
            System.out.format("%d %d %d%n", clearArea, maxDepth, maxDepthArea);
            n = in.nextInt();
        }
    }

    // put all the unique elements of an array in order at the front
    // and return the count of unique numbers.
    static int uniqueSeq(int[] a) {
        Arrays.sort(a);
        int inSeq = 0;  // smallest already in place
        for (int next = 1; next < a.length; next++) 
            if (a[inSeq] != a[next]) {
                inSeq++;
                a[inSeq] = a[next];
            }
        return inSeq+1;
    }    
}