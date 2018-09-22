// 2005 ACM Mid-Central Regional Programming Contest
// Problem G: Consecutive Digits in a Rational Number
// Posed by Tim Rolfe, Eastern Washington University
// Written by Tim Rolfe and John Cigas, Rockhurst University
// Coded by Andy Harrington, Loyola University Chicago
import java.io.*;
import java.util.Scanner;

public class digits {
   public static void main ( String[] args ) {
      int BASE = 7;
      String filename = "digits.in";
      BufferedReader inp = null;
      if ( args.length > 0 )
         filename = args[0];
      try {  
          inp = new BufferedReader(new FileReader(filename));  
      } catch (Exception e) {
          System.err.println(e);  
          System.exit(-1);  
      }

      Scanner in = new Scanner(inp);
      int nPasses = in.nextInt();

      for ( int pass = 1; pass <= nPasses; pass++ ) {
         int top = in.nextInt();
         int bottom = in.nextInt();
         int start = in.nextInt();
         int stop = in.nextInt();
         //BASE = input.nextInt(); 
         System.out.print ("Problem set " + pass + ": "
         + top + " / " + bottom + ", base " + BASE + " digits " + start +
         " through " + stop + ": ");

         for (int i = 0; i <= stop; i++) {
            top = (top % bottom) * BASE;
            if (i >= start)
                System.out.print(top/bottom);
         }
         System.out.println();
      }
   }
}
