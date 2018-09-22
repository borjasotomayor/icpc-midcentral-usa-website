// 2002 ACM Mid-Central Regional Programming Contest
// Problem E:  Oil Pipeline
// by John Cigas, Rockhurst University
import java.io.*;
import java.util.Vector;
import java.util.Arrays;
import java.awt.Point;

class pipeline {
  static final int DRAWXMAX = 71;
  static final int DRAWYMAX = 21;
  static final int XMAX = 96; // a 94x73 grid
  static final int YMAX = 76; // 74; // well, need 75 for boundary?
  static char [][] field = new char[YMAX][XMAX]; // used for storing output
  static int xin, yin; // tmp variables for reading in from data file
  static int [] lowX = new int [YMAX];
  static int [] highX = new int [YMAX];
  static int [] lowY = new int [XMAX];
  static int [] highY = new int [XMAX];
  static Vector wells = new Vector();
  static int east, west, north, south;

  public static void main(String[] args) {
    int count = 0;
    String FILE = "pipeline";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
      System.out.println("can't open output");
    }

    xin = in.intRead();
    yin = in.intRead();
    do {
      clean(); // initialize an oilfield
      do {
        putIn(xin, yin);
        xin = in.intRead();
        yin = in.intRead();
      } while (xin >= 0);

      // process the oilfield
      int where = findPipeLoc();
      System.out.println("Pipe to be at " + where);

      // draw the oilfield
      count++;
      out.println("OIL FIELD " + count);
      drawField(where, out);

      // Get next field
      xin = in.intRead();
      yin = in.intRead();
    } while (xin != 0);
    out.close();
  }

static int findPipeLoc() {
   int pipeLoc = Integer.MAX_VALUE;
   int pipeLength = Integer.MAX_VALUE;
   int sum;
   for (int i=1; i < YMAX-1; i++) {
      if (highX[i] != 0) {  // well in this row
         sum = 0;
         for (int j=1; j<XMAX-1; j++)
            if (highY[j] != 0) // well in this column
               sum += Math.max(i, highY[j]) - Math.min(i,lowY[j]);

         if (sum < pipeLength) {
            pipeLength = sum;
            pipeLoc = i;
         }
      }
   }
   return pipeLoc;
}

static void putInBorder(int x1, int y1, int x2, int y2) {
    // (x1,y1) and (x2, y2) diagonals of the border
    if ((x1 % 5 != 0) || (x2 %5 != 0) || (y1 %5 != 0) || (y2 %5 != 0))
        System.out.println("OOPS - coords not divisible by 5");
    for (int i=x1; i< x2; i+=5) {
//System.out.println("i = " + i);
        field[y1][i] = '+';
        field[y2][i] = '+';
        field[y1][i+1] = field[y1][i+2] = field[y1][i+3] = field[y1][i+4] = '-';
        field[y2][i+1] = field[y2][i+2] = field[y2][i+3] = field[y2][i+4] = '-';
    }
    field[y1][x2] = '+';
    field[y2][x2] = '+';
    for (int j=y1; j< y2; j+=5) {
        field[j][x1] = '+';
        field[j+1][x1] = '|';
        field[j+2][x1] = '|';
        field[j+3][x1] = '|';
        field[j+4][x1] = '|';
        field[j][x2] = '+';
        field[j+1][x2] = '|';
        field[j+2][x2] = '|';
        field[j+3][x2] = '|';
        field[j+4][x2] = '|';
    }
}
static void drawField( int pipePos  , PrintWriter out) {
System.out.println("EWNS =" + east + " " + west + " " + north + " " +south);

    // Adjust bounding box

    east += (5 - east%5);
    north += (5 - north%5);
    if (west %5 != 0) west -= (west % 5); else west -= 5;
    if (south %5 != 0) south -= (south % 5); else south -= 5;

    if (east > XMAX) System.out.println("OOPS - east too big");
    if (north > YMAX) System.out.println("OOPS - north too big");
    if (west < 0) System.out.println("OOPS - west too small");
    if (south < 0) System.out.println("OOPS - south too small");

System.out.println("EWNS =" + east + " " + west + " " + north + " " +south);

// Check if possible to draw
    if ((east - west > DRAWXMAX) || (north - south) > DRAWYMAX) {
        out.println("Map is too big to draw for pipeline at "+pipePos);
        return;
    }


    // put in pipe first
    for (int j=west; j<east; j++) { // 0 and XMAX-1 are borders
         field[pipePos][j] = '*';
         if (highY[j] > 0) {
            int start = Math.min(lowY[j], pipePos);
            int finish = Math.max(highY[j], pipePos);
            for (int i=start; i < finish; i++)  // 0 and YMAX-1 are borders
                field[i][j] = '*';
         }

    }

    // Border second
    putInBorder (west,south, east, north);

    // put in the wells third
    for (int i=0; i<wells.size(); i++) {
        Point p = (Point)wells.get(i);
        field[(int)p.getY()][(int)p.getX()] = '@';
    }



  // Draw the map
  boolean labels = true;

  String temp=""+north;
  int fw = temp.length();

  for (int i=north; i >=south ; i--) {
     if (labels)
        if (i % 5 == 0)
           out.print(ACMIO.format(i,fw));
        else
           //out.print("  ");
           out.print(ACMIO.format(" ",fw));

     for (int j=west; j<=east; j++)
        out.print(field[i][j]);
     out.println();
  }

  if (labels) {
     //out.print("  ");
     out.print(ACMIO.format(" ",fw));
     for (int j=west; j<east; j+=5)
        out.print(ACMIO.format(j,-5));
     out.println(east);
  }

}

static void putIn(int x, int y){
   wells.add(new Point(x,y));
   if (y < lowY[x]) lowY[x] = y;
   if (y > highY[x]) highY[x] = y;
   if (x < lowX[y]) lowX[y] = x;
   if (x > highX[y]) highX[y] = x;

   // Find the bounding box for all wells
   if (y < south) south = y;
   if (y > north) north = y;
   if (x > east) east = x;
   if (x < west) west = x;

}

static void clean () {
  for (int i=0; i < YMAX; i++)
      for (int j=0; j<XMAX; j++)
    field[i][j] = '.';
  Arrays.fill (lowY, Integer.MAX_VALUE);
  Arrays.fill (highY, 0);
  Arrays.fill (lowX, Integer.MAX_VALUE);
  Arrays.fill (highX, 0);
  // just conveniences for finding bounding box
  south = west = Integer.MAX_VALUE;
  north = east = 0;
  wells.clear();
}

}
