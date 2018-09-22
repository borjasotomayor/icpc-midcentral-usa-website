// 2002 ACM Mid-Central Regional Programming Contest
// Problem F: Tanning Salon
// by John Cigas, Rockhurst University
//salon.java
import java.io.*;
import java.util.HashSet;

class salon {
  static String customers;
  static int beds;

  public static void main(String[] args) {
    String FILE = "salon";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }

    beds = in.intRead();
    while (beds > 0) {
      customers = in.stringReadln();

      int mad = countWalkers();
      if (mad != 0)
         out.println(mad+" customer(s) walked away.");
      else
         out.println("All customers tanned successfully.");

      beds = in.intRead();
    }
    out.close();
  }

  static int countWalkers() {
    int frustrated;
    HashSet tanning = new HashSet();
    HashSet fuming = new HashSet(); // customers still in store, but mad

    frustrated = 0;
    for (int i = 0; i < customers.length(); i++) {
      Character c = new Character(customers.charAt(i));

      if (fuming.contains(c))
        fuming.remove(c);
      else if (tanning.contains(c))
        if ( fuming.isEmpty())
          tanning.remove(c);
        else
          System.out.println // Data check
                ("SNAFU - Irked customers not all gone when "+c+" leaves");
      else if (tanning.size() >= beds) {
             frustrated++; fuming.add(c);
           }
      else
          tanning.add(c);
    }
    // sanity check
    if (tanning.size() != 0)
      System.out.println("SNAFU - stack is not emtpy - mismatched data set");

    return frustrated;
  }
}
