// MCPC 2007 Problem C: Slink.  Alternate solution by Andy Harrington
import java.io.*;
import java.util.*;

/*
The grid is stored initially without the extra horizontal spacing of the 
final output.  It is stored with an outside boundary indicating automatically
blocked edges.  Symbolism:
  y: location of an edge, whether vertical or horizontal depends on the row
  n: cannot be an edge
  <space>: not known yet whether an edge is there or not
  . : vertex
  0,1, 2, 3: number of adjacent edges
  X position on the boundary where there would be a number (at even indices)
For example for dataset 3, it would be initialized to

XnXnXnX
n. . .n
X 2 2 X
n. . .n
X 2 2 X
n. . .n
XnXnXnX

and after all the blanks are filled in it would be

XnXnXnX
n.y.y.n
Xy2n2yX
n.n.n.n
Xy2n2yX
n.y.y.n
XnXnXnX

Then substitutions for the vertices are made inside the grid array,
and the rows are further manipulated as Strings:  The first grid
border is removed, the final border is added, and each row is expanded
into the required printable form.

#############
#           #
# +-------+ #
# | 2   2 | #
# |       | #
# | 2   2 | #
# +-------+ #
#           #
#############

The rule patterns have a related system.  They are first stated
compactly as strings, following Ron Pacheco's idea, with '/' at row
splits.  In pattern strings, the symbols defined above (other than X
which does not appear) are used with similar meanings, except that y
and n and numbers are only put where they are needed for the logic.
Edges that do not affect the logic are left blank, and number
locations where the value does not matter are listed as '#'. Finally
the further edges that are forced or excluded as the rule's concluson
are indicated in capital letters, Y and N.

For example the pattern "#n# # /n. . ./# 3 # / . . ./# # 1N/ . .N."
means the grid below. The slashes would not be stored, but they are
included here to emphasize ending spaces in the first and third rows:
#n# # /
n. . ./
# 3 # /
 . . ./
# # 1N/
 . .N./

For each rotation and flip of the pattern, the rules only save the
offsets of the hypothysis symbols yn0123 and the conclusion symbols
YN. Since even and odd rows have different meanings, the proper parity
for each pattern's offsets is stored in its rule.
*/

class slink2 {

  static int rows, cols; // totals in initial compact grid
  static char[][] grid;  // compact grid array version
  static ArrayList<Rule> rules = new ArrayList<Rule>();

  public static void main( String[] args ) throws Exception {
    String inFile = args.length > 0 ? args[0] : "slink.in";
    Scanner in = new Scanner(new File(inFile));
    int n = 1;
    makeRules();
    int c, r = in.nextInt();
    while ( r > 0 ) {
      rows = 2*r + 3; 
      cols = 2*in.nextInt() + 3;
      in.nextLine();  // clear newline by for line oriented input
      String[] strRow = new String[rows]; //easiest to create as strings
      strRow[0]  = strRow[rows-1] = 
         "XnXnXnXnXnXnXnXnXnXnXnXnXnXnXnXnXnXnXnXnXnX".substring(0,cols);
      strRow[1]  = 
         "n. . . . . . . . . . . . . . . . . . . . .".substring(0,cols-1) + "n";
      for (r = 2; r < rows-1; r+= 2) {
        strRow[r] = "X " + in.nextLine() + " X";
        strRow[r+1] = strRow[1];
      }
      grid = str2grid(strRow); 
      while (haveBlank()) //grid is complete when no blanks left
        for (Rule rule : rules) 
          rule.apply();
      // now convert to output format        
      for (r = 1; r < rows-1; r++) // fix vertices
        for (c = 1; c < cols-1; c++)
          if (grid[r][c] == '.') 
            if (grid[r-1][c] == 'y' && grid[r+1][c] == 'y')
              grid[r][c] = '|';
            else if (grid[r][c+1] == 'y' && grid[r][c-1] == 'y')
              grid[r][c] = '-';
            else if ((grid[r-1][c] == 'y' || grid[r+1][c] == 'y') &&
                     (grid[r][c-1] == 'y' || grid[r][c+1] == 'y'))
              grid[r][c] = '+';
            else
              grid[r][c] = ' ';
      String blanks = "                                                        "; 
      String edge2 = "#" + (blanks+blanks).substring(0,2*cols-3) + '#';
      String edge = edge2.replace(" ","#");
      System.out.println(n++);
      System.out.println(edge);
      System.out.println(edge2);
      for (r = 1; r < rows-1; r++) { // expand and substtute in rows
        String row = "# " + new String(grid[r]).substring(1,cols-1) + " #"; // replace boundary
        if (r%2 == 1) // horizontal edge row
          row = row.replace("y","---").replace("n","   "); // edges 3 long
        else { // number and vertical edge row
          row = row.replace("y","|").replace("n"," ");
          for (char digit = '0'; digit < '4'; digit++) // pad digits with blanks
            row = row.replace("" + digit, " " + digit + " ");
        }
        System.out.println(row);
      }
      System.out.println(edge2);
      System.out.println(edge);
      r = in.nextInt();
	}
  }

  static char[][] str2grid(String[] sRows) {
    char[][] array = new char[sRows.length][];
    for (int i = 0; i < array.length; i++)
      array[i] = sRows[i].toCharArray();
    return array;
  }

  static boolean haveBlank() {
    for (int r = 1; r < rows-1; r++) // fix vertices
      for (int c = 1; c < cols-1; c++)
        if (grid[r][c] == ' ') 
          return true;
    return false;
  }

  static char[][] rotate(char[][] a) {
    int nrows = a.length, ncols = a[0].length;
    char[][] ret = new char[ncols][nrows];
    for (int r = 0; r < nrows; r++)
      for (int c = 0; c < ncols; c++)
        ret[c][nrows - 1 - r] = a[r][c];
    return ret;
  }

  static char[][] flip(char[][] a) {
    int nrows = a.length, ncols = a[0].length;
    char[][] ret = new char[nrows][ncols];
    for (int r = 0; r < nrows; r++)
      for (int c = 0; c < ncols; c++)
        ret[nrows - 1 - r][c] = a[r][c];
    return ret;
  }

  static void makeRules() {
    String[] strRules = { //for more speed separate rules only needed at the beginning
      ".N./N0N/.N.", ".n./Y1n/.n.", ".n./Y2Y/.n.", ".n./Y2n/.Y.", ".Y./Y3Y/.n.", //force edge
                     ".N./y1N/.N.", ".N./y2y/.N.", ".N./y2N/.y.", ".y./y3y/.N.", //exclude edge
      "Y3Y3Y", ".Y. ./Y3 # /. . ./ # 3Y/. .Y.", // side by side or diagonal 3's
      "#N#/y.y/#N#", "#N#/N.y/#y#", // max 2 edges at vertex
      "#n#/n.y/#Y#", "#n#/Y.y/#n#", // if one edge at vertex, then 2
      "#n#/n.n/#N#", // if max 1 edge, then 0
      "#n#/n.Y/#Y3", // 2 exits of 3 blocked 
      "#n#/n. /# 2/n. /#Y#", "#n#/n. /# 2/Y. /#n#", "#n#/n.N/#N1", // corner 2, 2 ways, corner 1
      " .Y./# 3Y/n. ./#y# ", // 3 enter, block
      "#n# # /n. . ./# 3 # / . . ./# # 1N/ . .N.", // diag 3,1 block 3
      "#N# # /N. . ./# 3 # / . . ./# # 1n/ . .n.", // diag 3,1 block 1
      "# #Y#/ . .n/# 2 #/y. . /#n# #", "# #n#/ . .Y/# 2 #/y. . /#n# #", // enter, leave 2, 2 ver
      " .N./# 1N/n. ./#y# "  // 1 enter block
    };
    for (String sr : strRules) {
      char[][] pattern = str2grid(sr.split("/"));
      for (int mirror = 0; mirror < 2; mirror++) {
        for (int rot = 0; rot < 4; rot ++) {
          rules.add(new Rule(pattern)); // should check for duplicate patterns if want more speed
          pattern = rotate(pattern);
        }
        pattern = flip(pattern);
      }
    }
  }
      
  private static class Rule {
    private int r0, c0, // parity of starting locations to apply the rule
                nrows, ncols; // size of pattern
    private ArrayList<int[]> preState = new ArrayList<int[]>(), 
                             postState = new ArrayList<int[]>();
    public Rule(char[][] pattern) {
      nrows = pattern.length;
      ncols = pattern[0].length;
      for (int r = 0; r < nrows; r++)
        for (int c = 0; c < ncols; c++) {
          char ch = pattern[r][c];
          if ("#0123".contains(""+ch)) { // determines (even, even) location in grid
            r0 = r%2;  // match the parity for where the pattern is applied in grid
            c0 = c%2;  // all patterns include a character in #0123
          }
          if ("yn0123".contains(""+ch)) // note location and character to check for
            preState.add(new int[]{r, c, ch}); 
          else if (ch == 'Y' || ch == 'N') // note location and character to add
            postState.add(new int[]{r, c, ch + 'n' - 'N'}); // convert to lower case
        }
    }
        
    private boolean matchesHere(int r, int c) {
      for (int[] v: preState)
        if (grid[v[0]+r][v[1]+c] != (char)v[2]) 
          return false;
      return true;
    }
    
    public void apply() {
      for (int r = r0; r <= rows - nrows; r += 2) // shift rule all over the grid
        for (int c = c0; c <= cols - ncols; c += 2) 
          if (matchesHere(r,c))              // where matches
            for (int[] v: postState)         //   add conclusions
              grid[v[0]+r][v[1]+c] = (char)v[2];
    }
  }    
}
