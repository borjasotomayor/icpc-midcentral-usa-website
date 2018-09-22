// alternate Gorelian solution with Floyd/Warshall (shorter but slower)
import java.io.*;
import java.util.Scanner;

class gorelian2 {

  public static void main( String[] args ) throws Exception {
    String inFile = args.length > 0 ? args[0] : "gorelian.in";
    Scanner in = new Scanner( new File( inFile ) );
    int r = in.nextInt();
    while ( r > 0 ) {
      int c = in.nextInt();
      city( in, r + 1, c + 1 );
      r = in.nextInt();
    }
  }
    
  public static void city( Scanner in, int rows, int cols ) {

    // ----- Read Input -----

    // Allocate graph adjacency matrix
    int nodes = rows * cols;
    int[][] g = new int[nodes][nodes];
    for ( int r = 0; r < nodes; r++ )
      for ( int c = 0; c < nodes; c++ )
        g[r][c] = 0;

    // Input by row
    for ( int i = 0; i < 2 * rows - 1; i++ ) {
      int r = i / 2;

    // East/west row
    if ( i % 2 == 0 ) {
      for ( int c = 0; c < cols - 1; c++ ) {
        int l = in.nextInt(); // speed limit
        String d = in.next(); // direction indicator
        int w = r * cols + c; // west node
        int e = w + 1;        // east node
        if ( d.equals( "*" ) || d.equals( ">" ) )
          g[w][e] = l;
        if ( d.equals( "*" ) || d.equals( "<" ) )
          g[e][w] = l;
      }
    }
    
    // North/South row
    else {
      for ( int c = 0; c < cols; c++ ) {
        int l = in.nextInt(); // speed limit
        String d = in.next(); // direction indicator
        int n = r * cols + c; // north node
        int s = n + cols;     // south node
        if ( d.equals( "*" ) || d.equals( "v" ) )
          g[n][s] = l;
        if ( d.equals( "*" ) || d.equals( "^" ) )
          g[s][n] = l;
        }
      }
    }
    // Floyd/Warshall
    int infinity = 1000000;
    // Convert rates (rels per blip) to times (blips)
    for ( int r = 0; r < nodes; r++ )
      for ( int c = 0; c < nodes; c++ )
        g[r][c] = g[r][c] > 0 ? 2520 / g[r][c] : 
                    ((r == c) ? 0 : infinity);

    for (int k = 0; k < nodes; k++)
      for (int i = 0; i < nodes; i++)
        for (int j = 0; j < nodes; j++)
          g[i][j] = Math.min(g[i][j], g[i][k]+g[k][j]);

    // ----- Output -----
    if ( g[0][nodes-1] < infinity ) {
      System.out.print( g[0][nodes-1] );
      System.out.println( " blips" );
    }
    else
      System.out.println( "Holiday" );
  }
}
