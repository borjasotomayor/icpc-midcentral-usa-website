/* Mad Scientist, MCPC 2010 Problem B Java version */

import java.util.*;
import java.io.*;

public class mad {
    static int MAX = 26;
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("mad.in"));
        int[] P = new int[MAX], // P-seq (leaving P[0] empty for convenience)
              M = new int[MAX]; // Original sequence of measurements
        while (true) {
            int k = in.nextInt();
            if (k==0) break;     // all done
            for (int j=1; j <= k; j++)
                P[j] = in.nextInt();
            int n = 0;
            for (int j = 1 ; j <= k; j++) 
                for ( ; n < P[j]; n++)
                    M[n] = j;   
            System.out.print(M[0]);
            for (int i=1; i < n; i++)
                System.out.print(" " + M[i]);
            System.out.println();
        }
    }
}
