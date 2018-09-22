// 2003 ACM Mid-Central Regional Programming Contest
// Problem A: Clay Bully
// by John Cigas, Rockhurst University

/*
clay.java

Problem:  
  Find the largest and smallest block and print the corresponding names

*/

import java.io.*;
import java.util.*;

class clay {
 
  public static final int maxLump = 250;
  public static int maxClay = 0;
  public static int minClay = 2*maxLump+1;
  public static String victim, bully;

  public static void main(String[] arg) {
    String FILE = "clay";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }


    int numStudents = in.intRead(); 
    while ( numStudents > 0) {
      victim = "";
      bully = "";
      minClay = 2*maxLump+1;
      maxClay = 0;

      for (int i=0; i<numStudents; i++) {
        int x = in.intRead();
        int y = in.intRead();
        int z = in.intRead();
        String name = in.stringReadln();

        int volume = x * y * z;
        if (volume < minClay) {
           minClay = volume;
           victim = name;
        }
        if (volume > maxClay) {
           maxClay = volume;
           bully = name;
        }
      }
      out.println(bully + " took clay from " + victim + ".");
      numStudents = in.intRead(); 
    }
    out.close();
  }

}
