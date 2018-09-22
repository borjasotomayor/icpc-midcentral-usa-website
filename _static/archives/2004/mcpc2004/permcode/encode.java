// 2004 ACM Mid-Central Regional Programming Contest
// Problem E: Permutation Code
// by John Cigas, Rockhurst University

/*
encode.java

Problem: Encode data for problem E 
         Input is from file encode.in
         Output to file permcode.in

*/

import java.io.*;
import java.util.*;

class encode {

  public static void main(String[] arg) {
    String FILE = "permcode";
    ACMIO in = new ACMIO("encode" + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".in")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }
    int dataSet = 1;

    int x = in.intReadln(); 
    while ( x > 0) {
      String S = in.stringReadln();
      String P = in.stringReadln();
      String m = in.stringReadln();

      // check that P is a complete permutation of S
      char [] sPrime = S.toCharArray();
      char [] pPrime = P.toCharArray();
      Arrays.sort(sPrime);
      Arrays.sort(pPrime);
      if (!Arrays.equals (sPrime, pPrime)) {
         System.out.println("P is NOT a permutation of S in dataSet "+dataSet);
      }

      int N = m.length();
      char [] c = new char[N];
      int d = ((int)(Math.pow (N, 1.5) + x) % N);

      System.out.println("d = " + d + "  x = " + x);

      for (int j=0; j<N; j++) {
        if (d == j) {
	   System.out.println("j: "+j+"  charAt = " + P.indexOf(m.charAt(d)));
           c[j] = S.charAt(P.indexOf(m.charAt(d)));
	}
        else {
	   System.out.println("j: "+j+"  charAt = " + P.indexOf(m.charAt(j)) +"  "+S.indexOf(m.charAt((j+1)%N)) );
           c[j] = S.charAt(P.indexOf(m.charAt(j)) ^ S.indexOf(m.charAt((j+1)%N)));
	}
      }

      out.println(x);
      out.println(S);
      out.println(P);
      out.println(c);

      x = in.intReadln(); 
      dataSet++;
    }
    out.println("0");
    out.close();
  }

}
