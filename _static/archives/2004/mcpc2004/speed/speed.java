// 2004 ACM Mid-Central Regional Programming Contest
// Problem G: Speed Limit
// by John Cigas, Rockhurst University

/*
speed.java

Problem:  
  Given a list of {mph, total elapsed hours}, compute the total distance driven

*/

import java.io.*;
import java.util.*;

class speed {
 
  public static void main(String[] arg) {
    String FILE = "speed";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }

    int mph;
    int hours;
    int numSigns = in.intReadln(); 
    while ( numSigns > 0) {
      int distance = 0;
      int oldHours = 0;

      for (int i=0; i<numSigns; i++) {
         mph = in.intRead();
         hours = in.intReadln();
         distance += mph * (hours - oldHours);
         oldHours = hours;
      }

      out.println(distance + " miles" ); 
      numSigns = in.intReadln(); 
    }
    out.close();
  }

}
