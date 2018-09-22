// 2007 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem E: "Here We Go(relians) Again" [moderate]
// Ron Pachecho

import java.io.*;
import java.util.Scanner;

class gorelian {

    public static void main( String[] args ) throws Exception {
	String inFile = args.length > 0 ? args[0] : "gorelian.in";
	Scanner in = new Scanner( new File( inFile ) );
	int r = in.nextInt();
	while ( r > 0 ) {
	    int c = in.nextInt();
	    new City( in, r + 1, c + 1 );
	    r = in.nextInt();
	}
    }

    private static class City {

	public City( Scanner in, int rows, int cols ) {

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

	    // Convert rates (rels per blip) to times (blips)
	    for ( int r = 0; r < nodes; r++ )
		for ( int c = 0; c < nodes; c++ )
		    g[r][c] = g[r][c] > 0 ? 2520 / g[r][c] : 0;

	    // ----- Dijkstra's algorithm -----
	    
	    // D is best distance to a node; init all to infinity
	    // Q is the list of unvisited nodes
	    int infinity = 1000000;
	    int[] D = new int[nodes];
	    boolean[] Q = new boolean[nodes];
	    for ( int i = 0; i < nodes; i++ ) {
		D[i] = infinity;
		Q[i] = true;
	    }
	    // Distance from start node to itself is zero
	    D[0] = 0;
	    // Main algorithm loop
	    for ( int n = 0; n < nodes; n++ ) {
		// Find best unvisited node u and remove it from Q
		int u = nodes;
		for ( int i = 0; i < nodes; i++ )
		    if ( Q[i] && ( u == nodes || D[i] < D[u] ) ) u = i;
		Q[u] = false;
		// Relax all neighbors
		for ( int v = 0; v < nodes; v++ )
		    if ( g[u][v] != 0 ) {
			int a = D[u] + g[u][v];
			if ( a < D[v] ) D[v] = a;
		    }
	    }

	    // ----- Output -----
	    if ( D[nodes-1] < infinity ) {
		System.out.print( D[nodes-1] );
		System.out.println( " blips" );
	    }
	    else
		System.out.println( "Holiday" );
	}
    }
}
