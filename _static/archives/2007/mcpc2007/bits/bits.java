// 2007 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem D: "Persistent Bits" [easy/moderate]
// Andy Harrington, Loyola University Chicago

import java.io.*;
import java.util.*;

public class bits
{
  public static void main(String[] args) throws Exception {
    String file = (args.length > 0) ? args[0] : "bits.in";
    Scanner in = new Scanner(new File(file));
    int a  = in.nextInt();
    while (a  > 0) {
      int b = in.nextInt();
      int c = in.nextInt();
      int x = in.nextInt();
      int ones = ~0;   // 1 in all bits that have not become 0 (all at start)
      int zeroes = ~0; // 1 in all bits that have not become 1 (all at start)
      for (int i = 0; i < c; i++) {
        ones &= x;
        zeroes &= ~x;
        x = (a*x+b) % c;
      }
      for (int bit = 1<<15; bit != 0; bit >>= 1)
        if ((ones & bit) != 0)
          System.out.print("1");
        else if ((zeroes & bit) != 0)
          System.out.print("0");
        else  
          System.out.print("?");
      System.out.println();
      a = in.nextInt();
    }
  }
}
