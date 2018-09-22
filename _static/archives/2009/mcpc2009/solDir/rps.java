// 2009 ACM Mid-Central USA Regional Programming Contest
// Solution to "F. Rock, Paper, Scissors" [easy]
// John Cigas

import java.io.*;
import java.util.Scanner;

public class rps {

   public static void main( String[] args ) throws Exception {
      Scanner in = new Scanner( new File( "rps.in" ) );
      String P1 = in.nextLine();
      String P2 = in.nextLine();

      while (P1.charAt(0) != 'E') {
         int C1 = 0, C2 = 0;
         for (int i=0; i<P1.length(); i++) {
            char P1c = P1.charAt(i);
            char P2c = P2.charAt(i);
            if ((P1c == 'R' && P2c == 'S') || (P1c == 'P' && P2c == 'R') || (P1c == 'S' && P2c == 'P'))
               C1++;
            else if (P1c != P2c)
               C2++;
         }
         System.out.println("P1: " + C1);
         System.out.println("P2: " + C2);
         P1 = in.nextLine();
         P2 = in.nextLine();
      }
   }
}
