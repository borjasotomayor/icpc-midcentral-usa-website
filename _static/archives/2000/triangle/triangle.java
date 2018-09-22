//triangle.java
import java.io.*;

class triangle {
  static int[] rot = new int[6];  //rot[i] is current rotaton of tri[i]
                                  // 0 - 2 sides
  static int[][] tri = new int[6][3];  // the six triangles' edge values
  static int maxScore; // set by fit method  
  
  static int getEdge(int n, int shift) {
    // return data for triangle n  
    //   shift sides from the current rotation, 
    //     shift 0: side against previous triangle
    //     shift 1: side against next triangle
    //     shift 2: outer side used in score
    return tri[n][(rot[n] + shift) % 3];
  }
  
  static boolean matchNext(int n) {
    // true if current rotation of triangle n 
    //   fits with next triangle, n = 0 - 5 
    return getEdge(n, 1) == getEdge((n+1)%6, 0);
  }
  
  static void swap(int i, int j) {
    // swap triangle[i] and triangle[j];
    int[] temp = tri[i];
    tri[i] = tri[j];
    tri[j] = temp;
  }
  
  static void fit(int n) {
    // triangles < n fit together, try the rest n = 1 - 6
    // if n = 6, just check match of tri[5]with tri[0]
    // uses and sets static rot array at indices >= n 
    // swaps and unswaps triangles in static tri array
    // updates static maxScore
    int i;
    if (n == 6) { // no more triangles
      if (matchNext(5)) {
        int score = 0;
        for (i = 0; i < 6; i++)
          score += getEdge(i,2);
        // System.out.print(" " + score);  // check solutions
        maxScore = Math.max(score, maxScore);
      }
    }
    else { // fit next triangle
      for(i = n; i < 6; i++) { // swap in next remaining triangle
        swap(n,i);
        for (int r = 0; r < 3; r++) { // try all 3 rotations
          rot[n] = r;
          if (matchNext(n-1))
            fit(n+1);
        }
        swap(n,i); // undo swap so calling method sees no change
      }
    }
  } 
         
  public static void main(String[] args) {
    String FILE = "triangle";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out"))); 
    } catch(Exception e) {
        System.out.println("can't open output");
    }
    
    do { // read all data sets
      int r;
      for (int n = 0; n < 6; n++)
        for (r = 0; r < 3; r++)
          tri[n][r] = in.intRead();
      maxScore = 0;
      for (r = 0; r < 3; r++) {
        rot[0] = r;
        fit(1);
      }
      // System.out.println();
      if (maxScore == 0)
         out.println("none");
      else
         out.println(maxScore);
    } while (in.charReadln() == '*');
    out.close();
  }
}
         
          
