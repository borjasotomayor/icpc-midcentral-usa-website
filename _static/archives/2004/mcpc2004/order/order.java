// 2004 ACM Mid-Central Regional Programming Contest
// Problem C: Symmetric Order
// by John Cigas, Rockhurst University

/*
order.java

Problem:  
  Given a list of strings in non-descending order by length, print them out so
  the shortest strings are at the ends and the longest are in the middle.

*/

import java.io.*;
import java.util.*;

class order {
 

  public static void main(String[] arg) {
    String FILE = "order";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }


    int set = 1;
    int numNames = in.intReadln(); 
    while ( numNames > 0) {
      String [] allNames = new String[numNames];

      for (int i=0; i<numNames; i++) {
        String aName = in.stringReadln();
        if (i%2 == 1)
            allNames[numNames-((i+1)/2)] = aName;
        else
            allNames[i/2] = aName;
      }

      out.println("SET " + set++);
      for (int i=0; i<numNames; i++) 
        out.println(allNames[i]);

      numNames = in.intReadln(); 
    }
    out.close();
  }

}
