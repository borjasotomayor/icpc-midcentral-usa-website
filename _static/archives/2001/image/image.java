//image.java
import java.io.*;

class image {
  static int MAXDIM = 20;
  static int IMAGE_NEIGHBORS = 8;
  static int SIDES = 4;
  static int[][] del = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
  static char[][] grid = new char[MAXDIM][MAXDIM];
  static char EMPTY_CHAR = '.';
  static char FILL_CHAR = 'I';
  static int rows, cols;

  public static  void main(String[] arg) {
    String FILE = "image";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    }catch(Exception e) {
        System.out.println("can't open output");
    }
    rows = in.intRead(); cols = in.intRead();
    while (rows > 0) { // read all data sets
      int r, c;
      int rstart = in.intRead() - 1, cstart = in.intRead() - 1;
      for (r = 0; r < rows; r++) //grid with minimum 0 components, not 1
        for (c = 0; c < cols; c++)
          grid[r][c] = in.charRead();
      if(grid[rstart][cstart] != 'X') System.err.println("Bad start pt");
      out.println(fill(rstart, cstart));
      rows = in.intRead(); cols = in.intRead();
    }
    out.close();
  }

  static int fill(int r, int c){
    // Fills for 8 neighbors and counts the number of neighbors that share
    // boundary sides rather than a vertex.
    // Assume r,c are row and column of a place to be filled (an 'X')
    int count = 0;
    grid[r][c] = FILL_CHAR;
    for (int dir = 0; dir < IMAGE_NEIGHBORS; dir++) {
      int rn = r+del[dir][0], cn = c+del[dir][1];
      if (( rn < 0 || rn >= rows || cn < 0 || cn >= cols
             || grid[rn][cn] == EMPTY_CHAR ) ) {
        if (dir < SIDES)
          count++;
      }
      else if (grid[rn][cn] != FILL_CHAR)
          count += fill(rn, cn);
    }
    return count;
  }
}
