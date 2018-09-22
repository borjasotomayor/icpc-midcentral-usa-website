// 2004 ACM Mid-Central Regional Programming Contest
// Problem D: Flow Layout
// by John Cigas, Rockhurst University

/*
flow.java

Problem:  
  Given a list of rectangle dimensions and a maximum window width, calculate
  size of the resulting window, using a left to right, top to bottom flow layout
  manager.

*/

import java.io.*;
import java.util.*;

class flow {
 

  public static void main(String[] arg) {
    String FILE = "flow";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }

    int winWidth = in.intReadln(); 
    while ( winWidth > 0) {
      int maxWidth = 0;
      int maxHeight = 0;
      int rowWidth = 0;
      int rowHeight = 0;

      int currWidth = in.intRead();
      int currHeight = in.intReadln();

      while (currWidth != -1 && currHeight != -1) {
        if (rowWidth+currWidth > winWidth) {
           maxHeight += rowHeight;
           rowHeight = 0;
           if (rowWidth > maxWidth)
               maxWidth = rowWidth;
           rowWidth = 0;
        }

        if (currHeight > rowHeight)
           rowHeight = currHeight;
        rowWidth += currWidth;

        currWidth = in.intRead();
        currHeight = in.intReadln();
      }

      maxHeight += rowHeight;
      if (rowWidth > maxWidth)
         maxWidth = rowWidth;
      out.println(maxWidth + " x " + maxHeight);

      winWidth = in.intReadln();
    }
    out.close();
  }

}
