/* RIPOFF, ACM MCPC 2009 Problem I, alternate solution by Andy Harrington
Each dataset:
n s t  // n number of spaces on the board, 1 to s spaces per turn, max t turns
// followed by n numbers
Input ends with a 0

The score is sum of numbers on spaces where turns end.  Find the maximum.

Dynamic program with parameters for the space and the number of turns
to get to it.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class ripoff
{
    static int MAX_N = 200,  // check against final problem statement!
               CHANGE_BOUND = 9999, n, s, t;
    static int[] amt = new int[MAX_N+2]; //numbers on spaces, at start = 0.
    
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "ripoff.in";
        Scanner in = new Scanner(new File(file));
        int[][] best = new int[MAX_N + 2][MAX_N+2];
        for (int i = 1; i <= MAX_N+1; i++)  
            best[0][i] = -MAX_N * CHANGE_BOUND; // impossibly bad
        n  = in.nextInt();
        while (n  > 0) {
            s = in.nextInt();
            t = in.nextInt();
            for (int i = 1; i <= n; i++)  // at index 0 remains 0
                amt[i] = in.nextInt();
            amt[n+1] = 0;
            if (args.length > 0) judgeCheckData();  // judge integrity check
            for (int tt = 1; tt <= t; tt++) 
                for (int j = tt; j <= n+1; j++) 
                    best[tt][j] = amt[j] +
                                     maxInSeq(best[tt-1], max(tt-1, j - s), j);
            int val = best[t][n+1];
            for (int tt = 1; tt < t; tt++) 
                val = max(best[tt][n+1], val);
            System.out.println(val);
            n = in.nextInt();
        }
    }

    static int maxInSeq(int[] a, int i, int past) {  // return max of 
        int big = a[i];                              // a[i].. a[past-1]
        for ( ; i < past; i++)
            big = max(a[i], big);
        return big;
    }

// only judge's tests follow:  

    static void judgeCheckData() {
        // check against final problem statement  (also bounds at top)!
        int MIN_N = 2, MIN_S = 2, MAX_S = 10;
        if (n > MAX_N || n < MIN_N || s > MAX_S || s < MIN_S ||
            n > s*t || t > n+1) System.err.println("Bad param!");
        for (int i = 1; i <= n; i++) 
            if (abs(amt[i]) > CHANGE_BOUND)
                System.err.println("change out of range " + amt[i]);
    }
}
