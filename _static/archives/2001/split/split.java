//split.java
import java.io.*;

class split {
  static char[][] grid;
  final static char H_BOUND = '-';
  static char V_BOUND = '|';
  static char CORNER = '*';
  static int ROW = 0;  // indices in arrays corner, dim, minDim
  static int COL = 1;

  char ch;                // letter label, '-' or '|'
  split child1, child2;   // child1 is top or left,  child2 is bottom or right
  int[] corner, dim, minDim; // index 0 for row, index 1 for column
    // top left corner, dimensions, and minimum dimensions of the window
    // each is not mutated after it is set, so references may be reused
  int iSplit, // index 0 or 1 for arrays corner, dim, and minDim direction
      iSame;  // iSplit is the index of the split coordinate, iSame of the other
              // these variables allow much code to be common for subwindows,
              // no matter what the direction of the split

  split(ACMIO in) { // recursive constructor, creates subwindows
                    //  does not set corner and dim arrays
    ch = in.charRead();
    if (Character.isLetter(ch))
      minDim = new int[] {2, 2};
    else {
      child1 = new split(in);
      child2 = new split(in);
      if (ch == H_BOUND)
        iSplit = ROW;
      else
        iSplit = COL;
      iSame = 1 - iSplit;
      minDim = new int[2];
      minDim[iSplit] = child1.minDim[iSplit] + child2.minDim[iSplit];
      minDim[iSame] = Math.max(child1.minDim[iSame], child2.minDim[iSame]);
    }
  }

  void setWalls(int[] nextCorner, int[] nextDim) {
    // set corner and dim arrays recursively and set walls in undivided windows
    corner = nextCorner;
    dim = nextDim;
    if (child1 == null) {
      for (int r = corner[0]+1; r < corner[0] + dim[0]; r++){
        grid[r][corner[1]] = V_BOUND;
        grid[r][corner[1]+dim[1]] = V_BOUND;
      }
      for (int c = corner[1]+1; c < corner[1] + dim[1]; c++){
        grid[corner[0]][c] = H_BOUND;
        grid[corner[0]+dim[0]][c] = H_BOUND;
      }
    }
    else {
      int splitDim1 = (int)Math.ceil(
               child1.minDim[iSplit]*((double)dim[iSplit])/minDim[iSplit] );
      nextDim = (int[])dim.clone();
      nextDim[iSplit] = splitDim1;
      child1.setWalls(corner, nextDim);
      nextDim = (int[])dim.clone();
      nextDim[iSplit] -= splitDim1;
      nextCorner = (int[])corner.clone();
      nextCorner[iSplit] += splitDim1;
      child2.setWalls(nextCorner, nextDim);
    }
  }

  void setCorners() {
    // recursively sets label and asterisks for undivided windows
    // Asterisks may be overwritten by other labels later, but because of the
    //   recursive order, top left corners are not overwritten later.
    if (child1 == null) {
        grid[corner[0]][corner[1]] = ch;
        grid[corner[0]+dim[0]][corner[1]] = CORNER;
        grid[corner[0]][corner[1]+dim[1]] = CORNER;
        grid[corner[0]+dim[0]][corner[1]+dim[1]] = CORNER;
    }
    else {
      child1.setCorners();
      child2.setCorners();
    }
  }

  public static  void main(String[] arg) {
    String FILE = "split";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }
    int r, c, total = in.intRead();
    for (int i = 1; i <= total; i++) {
      out.println(i);
      split mainWindow = new split(in);
      int[] mainDim = mainWindow.minDim;
      grid = new char[mainDim[0]+1][mainDim[1]+1];
      for (r = 1; r < mainDim[0]; r++) // fill inside main window
        for (c = 1; c < mainDim[1]; c++)
          grid[r][c] = ' ';
      mainWindow.setWalls(new int[]{0,0}, mainDim);
      mainWindow.setCorners(); // overwrites some walls
      for (r = 0; r <= mainDim[0]; r++) {
        for (c = 0; c <= mainDim[1]; c++)
          out.print(grid[r][c]);
        out.println();
      }
    }
    out.close();
  }
}
