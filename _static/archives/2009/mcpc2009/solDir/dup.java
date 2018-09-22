// 2009 ACM Mid-Central USA Regional Programming Contest
// Solution to "E. Duplicate Removal" [easy]
// John Cigas

import java.io.*;
import java.util.Scanner;

public class dup {

   public static void main( String[] args ) throws Exception {
      Scanner in = new Scanner( new File( "dup.in" ) );
      int N = in.nextInt();

      while (N > 0) {
         int P = in.nextInt();
         for (int i=1; i<N; i++) {
            int A = in.nextInt();
            if (A != P)
               System.out.print(P + " ");
            P = A;
         }
         System.out.println(P + " $");
         N = in.nextInt();
      }
   }
}
