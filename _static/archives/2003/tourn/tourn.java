// 2003 ACM Mid-Central Regional Programming Contest
// Problem H: Tournament Brackets
// by John Cigas, Rockhurst University

//tourn.java
import java.io.*;
import java.util.*;

class tourn {

  static char[][] grid;
  static Vector teamPairs;
  static int [] maxNameSizes;


  static int bracket(int row, int col, int level, int pos) {
     // draw a name, and if needed, diagonal lines and recursive brackets
     // returns the leftmost column written to

     int maxNameSize = maxNameSizes[level] + 2;
     String aName = (String)teamPairs.elementAt(pos);

     // insert team name with leading _ and trailing _s
     grid[row][col-maxNameSize] = '_';
     for (int j=0; j<aName.length(); j++)
        grid[row][col-maxNameSize+j+1] = aName.charAt(j);
     for (int j=aName.length(); j<maxNameSize-1; j++)
        grid[row][col-maxNameSize+j+1] = '_';
    

     // no prior brackets with this name - stop
     if (teamPairs.indexOf(aName,pos+1) < 0) return (col-maxNameSize);

     int back = (int)Math.pow(2,level-2); // how far back for next bracket

     // Need to decide which end of bracket it goes in??
     int x = teamPairs.indexOf(aName,pos+1);
     int top, bottom;
     if (x % 2 == 0) {
        top = bracket (row-back, col-maxNameSize-back, level-1, x+1); 
        diags(row, col-maxNameSize, back);
        bottom = bracket (row+back, col-maxNameSize-back, level-1, x); 
     }
     else {
        top = bracket (row-back, col-maxNameSize-back, level-1, x); 
        diags(row, col-maxNameSize, back);
        bottom = bracket (row+back, col-maxNameSize-back, level-1, x-1); 
    }
    return Math.min(top,bottom);
  }

  static void diags (int row, int col, int howMany) {
     // draw the diagonal lines connecting this team with previous brackets
     for (int i=0; i<howMany; i++) {
        grid[row-i][col-i-1] = '\\';
        grid[row+i+1][col-i-1] = '/';
     }

  }

  public static  void main(String[] arg) {
    String FILE = "tourn";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }

    int i = 1;
    int r = 0;
    int c = 0;

    int numNames = in.intReadln();
    while (numNames > -1) {

      teamPairs = new Vector();

      for (int j=0; j<numNames; j++) {
         String aName = in.stringRead();
         teamPairs.add(aName);
      }

      // Fill up the 0 slot, so we can start at 1
      teamPairs.add("Anything to start at index 1");
      java.util.Collections.reverse(teamPairs);

      out.println("Tournament " + i++);


     int level = (int)(Math.log(numNames-1)/Math.log(2.0)+1);
     //System.out.println("level = " + level);

     // Calculate maximum name length at each level
     maxNameSizes = new int [level+1];
     for (int a=0; a<level; a++) {
        int tmpMax = 0;
        for (int j= (int)Math.pow(2,a);
                 j<(int)Math.min(Math.pow(2,a+1),numNames+1); j++) {

           String s = (String)teamPairs.elementAt(j);
           if (s.length() > tmpMax) tmpMax = s.length();
        }
        maxNameSizes[level-a] = tmpMax;
     } 

     //   number of rows is 2^level - 1 (no blank row at end)
     //   starting row is (2^(level-1)-1)
     //   number of cols is 2^(level-1) - 1 + (level * (longestname+2))
     //   starting col is one past the last one

     int gridRows = (int)Math.pow(2, level) - 1;
     int gridCols = ((int)Math.pow(2, level-1)-1) + (level * 9);

     grid = new char[gridRows][gridCols];
     for (r = 0; r < gridRows; r++) // Clear
       for (c = 0; c < gridCols; c++)
         grid[r][c] = ' ';

     int minCol = bracket((int)Math.pow(2,level-1)-1,gridCols,level, 1); 

     for (r = 0; r < gridRows; r++) {
        // trim blanks off the end of each line
        int lastCol = gridCols - 1;
        while (lastCol >=minCol && grid[r][lastCol] == ' ') { lastCol--;}

        // make sure there is something to print on a line
        if (lastCol >= minCol) { 
           for (c = minCol; c < lastCol+1; c++)
             out.print(grid[r][c]);
           out.println();
        }
      }
      numNames = in.intReadln();
    }
    out.close();
  }
}
