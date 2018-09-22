// 2005 ACM Mid-Central Regional Programming Contest
// Problem C: Connect
// by Andy Harrington, Loyola University Chicago

import java.io.*;
import java.util.*;

class Seg {
  int x1, x2;   // x1 <= x <= x2
  double m, b;  // on line y = m*x + b   No vertical knight's moves
  
  Seg(int x1, int y1, int x2, int y2) {
      this.x1 = x1; this.x2 = x2;
      m = (y2 - y1)/(double)(x2 - x1);
      b = m*(0 - x1) + y1;
  }
  
  boolean intersects(Seg s) { 
      if (Math.abs(m - s.m) < .001) return false;  // if parallel
      double x = -(b - s.b)/(m - s.m);
      //avoid counting end match with inexact arithmetic 
      // intersection coordinates are a multiple of 1/2, 1/3 or 1/5
      return (x - x1)*(x - x2) < -.0001 && (x - s.x1)*(x - s.x2) < -.0001;
  }   // x between end coordinates of both
}  
    
public class connect {
  static int[][] knightMoves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, 
                                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
  static int[][] comp; //0 unoccupied, White <0, Black>0 and shows component
  static List<Seg> segList; // all segments on the board
  static int N;  // max board coordinate  

  static boolean legal(int x, int y) { // true if coordinates are on the board
    return x >= 0 && y >= 0 && x <= N && y <= N;
  }
  
  static boolean isOpen(Seg seg) {
    for (Seg s : segList)
      if (seg.intersects(s))
        return false;
    return true;
  }

  // return true if x = 0 and x = N are connected 
  static boolean wins() { 
    for (int y = 1; y < N; y++)
       if (comp[0][y] > 0)
         for (int y2 = 1; y2 < N; y2++)
           if (comp[0][y] == comp[N][y2])
             return true;
    return false;
  } 

  public static void main(String[] args) {
    //  standard I/O setup follows
    String file = (args.length > 0) ? args[0] : "connect.in";
    Scanner in = null;
    try {
      in = new Scanner(new BufferedReader(  
                       new FileReader(file))); 
    } catch(Exception e) {
      System.err.println("Can't open input " + file);
      System.exit(-1);
    }
    
    N = in.nextInt();
    while (N > 0) {
      System.out.println(solve(in) ? "yes" : "no");
      N = in.nextInt();
    }
  }   
  
  static boolean solve(Scanner in) {
    int newComp = 1; // assign a new component number of each play 
    int M = in.nextInt();
    debug(String.format("N: %d M: %d%n", N, M));
    comp = new int[N+1][N+1]; // so initially 0:  unowned
    segList = new ArrayList<Seg>();   
    for (int i = 0; i < M; i++) {
      int player = (i % 2 == 0) ? 1: -1; // 1 for Black, -1 for White
      int x = in.nextInt(), y = in.nextInt();
      comp[x][y] = player*newComp++; //new component number with player's sign
      debug(String.format("%2d: (%d, %d): ", comp[x][y], x, y));
      for (int[] del : knightMoves) {
        int kx = x + del[0], ky = y + del[1];
        if (!legal(kx, ky))
          continue;
        int otherComp = comp[kx][ky];
        Seg seg = new Seg(x, y, kx, ky);
        if (otherComp*player > 0 && isOpen(seg)) {
          segList.add(seg);
          debug(String.format("(%d, %d) ", kx, ky));
          if (otherComp != comp[x][y])
            for (int px = 0; px <= N; px++)        
              for (int py = 0; py <= N; py++) 
                if (comp[px][py] == otherComp) 
                  comp[px][py] = comp[x][y];  
        }
      }
      debug(String.format("%n"));
    }
    if (whiteWins()) { // to validate judge's data -- not needed by teams
      System.out.println("ERROR! White wins!!");
      return false;
    }
    return wins();
  }

  // rest for debugging and judge's data validation
  static boolean whiteWins() { 
    for (int y = 1; y < N; y++)
       if (comp[y][0] > 0)
         for (int y2 = 1; y2 < N; y2++)
           if (comp[y][0] == comp[y2][N])
             return true;
    return false;
  } 

  static void debug(String s) {
      // System.err.print(s);
  }    
} 
