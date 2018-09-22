// 2002 ACM Mid-Central Regional Programming Contest
// Problem C:  Magnificent Meatballs
// by John Cigas, Rockhurst University
import java.io.*;

class meatball {
  static int MAXDIM = 30;
  static int diners, send;
  static int [] table = new int[MAXDIM];

  public static  void main(String[] arg) {
    String FILE = "meatball";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    }catch(Exception e) {
        System.out.println("can't open output");
    }

    diners = in.intRead();
    while (diners > 1) { // read all data sets
      int r;
      for (r = 0; r < diners; r++)
          table[r] = in.intRead();

      send = findPosition();
      if (send > 0)
         out.println("Sam stops at position " + send +
      " and Ella stops at position " + (send+1) + ".");
      else
         out.println("No equal partitioning.");

      diners = in.intRead();
    }
    out.close();
  }
  static int findPosition() {
      int s, sum, target;
      sum = 0;
      for (s = 0; s < diners; s++)
         sum += table[s];

      if (sum % 2 == 1) return -1; // how odd

      target = sum/2;
      for (s = 0; s < diners; s++)  {
        sum -= table[s];
        if (sum == target) {
            return s+1;
        }
      }
      return -1;
  }
}
