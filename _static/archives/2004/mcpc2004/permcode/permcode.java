// 2004 ACM Mid-Central Regional Programming Contest
// Problem E: Permutation Code
// by John Cigas, Rockhurst University

/*
permcode.java

Problem:  

*/

import java.io.*;
import java.util.*;

class permcode {
 

  public static void main(String[] arg) {
    String FILE = "permcode";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }


    int x = in.intReadln(); 
    while ( x > 0) {
      String S = in.stringReadln();
      String P = in.stringReadln();
      String c = in.stringReadln();

      int N = c.length();
      char [] m = new char[N];

      // m[d] is known
      int d = ((int)(Math.pow (N, 1.5) + x) % N);
      m[d] = P.charAt(S.indexOf(c.charAt(d)));

      // Start at j=d-1 and work backwards evaluating the m[j+1] from each c[j]
      for (int k=1; k<N; k++) {
           int j = (d-k + N) % N;
           m[j] = P.charAt(S.indexOf(c.charAt(j)) ^ S.indexOf(m[(j+1)%N]));
      }

      out.println(m);
      x = in.intReadln(); 
    }
    out.close();
  }
}
