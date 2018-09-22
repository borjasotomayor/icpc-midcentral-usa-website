// 2009 ACM Mid-Central USA Regional Programming Contest
// Solution to "B. Gnome Sequencing" [very easy]
// John Cigas

import java.io.*;
import java.util.Scanner;

public class gnome {

   public static void main( String[] args ) throws Exception {
      Scanner in = new Scanner( new File( "gnome.in" ) );
      int A, B, C;
      int N = in.nextInt();
      System.out.println("Gnomes:");

      for (int i=0; i<N; i++) {
         A = in.nextInt();
         B = in.nextInt();
         C = in.nextInt();
         if (((A > B) && (B > C)) || ((A < B) && (B < C)))
            System.out.println("Ordered");
         else
            System.out.println("Unordered");
      }
   }
}
