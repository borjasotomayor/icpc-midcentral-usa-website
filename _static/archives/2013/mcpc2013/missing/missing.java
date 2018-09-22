/*  
    missing.java
    Missing Pages, MCPC 2013, Problem C
    Java solution by Andy Harrington
*/

import java.util.*;
import java.io.*;

public class missing
{
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "missing.in";
        Scanner in = new Scanner(new File(file));
        int N = in.nextInt();
        while (N != 0) {
            int P = in.nextInt();
            if (P <= N/2)
                if (P%2 == 0)
                    System.out.format("%d %d %d%n", P-1, N+1-P, N-P+2);
                else
                    System.out.format("%d %d %d%n", P+1, N-P, N-P+1);
            else
                if (P%2 == 0)
                    System.out.format("%d %d %d%n", N-P+1, N-P+2, P-1);
                else
                    System.out.format("%d %d %d%n", N-P, N-P+1, P+1);
            N = in.nextInt();
        }
    }
}