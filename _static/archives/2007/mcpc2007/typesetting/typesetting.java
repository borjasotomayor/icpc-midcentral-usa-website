// 2007 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem A: "Typesetting" [moderate/hard]
// Ron Pacheco
import java.io.*;
import java.util.Scanner;
import java.lang.Math;

class typesetting {

    public static void main( String[] args ) throws Exception {
	String inFile = args.length > 0 ? args[0] : "typesetting.in";
	Scanner in = new Scanner( new File( inFile ) );
	int n = 0;
	int rows = in.nextInt();
	while ( rows > 0 ) {
	    n++;
	    System.out.println( n );
	    new GString( in, rows );
	    rows = in.nextInt();
	}
    }

    private static class GString {

	public GString( Scanner in, int rows ) {

	    // ----- Read Input -----
	    int cols = 0;
	    char[][] g = null; // This is the glyph array
	    in.nextLine();
	    for ( int row = 0; row < rows; row++ ) {
		// At the left edge we add a string of "artificial" blanks to
		// keep from having to code the left edge as a special case.
		String s = ( new String( "#...................." ) ).concat( in.nextLine() );
		// Create the glyph array once we know the length of the input
		if ( cols == 0 ) {
		    cols = s.length();
		    g = new char[rows][cols];
		}
		// Put the string characters into the glyph array
		for ( int col = 0; col < cols; col++ )
		    g[row][col] = s.charAt( col );
	    }

	    // ----- Solve -----

	    /* The first glyph is considered to be the start of the typeset
	       string. The solution finds two columns, s1 and s2, that are the
	       columns to the immediate left and immediate right of the next
	       glyph to move onto the solution string. We then proceed down
	       column s1 row by row, adding together the total number of
	       adjacent empty pixels to the right and left. Ignoring rows where
	       all pixels to the right are blank, the smallest of these sums is
	       the amount that the new glyph can be moved left to bring it into
	       the correct solution position. The rest of the solution is just a
	       matter of the busywork of actually moving the pixels. */

	    // Locate the first s1 position
	    int s1 = 2;
	    while ( g[0][s1] != ' ' ) s1++;

	    // When s1 is the end of the glyph array we know we're done
	    while ( s1 < cols ) {

		// Search to the right of s1 for s2
		int s2 = s1 + 1;
		while ( s2 < cols && g[0][s2] != ' ' ) s2++;

		// Glyph width for later use
		int w = s2 - s1 - 1;

		// Go down s1 row by row and add the number of adjacent empty
		// cells to the right and to the left; results stored in tr[]
		int[] tr = new int[rows];
		for ( int r = 0; r < rows; r++ ) {
		    // Convenient place to fill in s1 with '.' pixels as we go
		    g[r][s1] = '.';
		    // Left side
		    int c = s1 - 1;
		    while ( g[r][c] == '.' ) c--;
		    tr[r] = s1 - c - 1;
		    // Right side
		    c = s1 + 1;
		    while ( c < s2 && g[r][c] == '.' ) c++;
		    // Sum ( if all pixels to the right were empty, we just add a
		    // big number so this row will not limit the movement )
		    tr[r] += c < s2 ? c - s1 - 1 : 9999;
		}

		// The number of pixels we can move is the minimum of the above sums
		int track = tr[0];
		for ( int r = 1; r < rows; r++ )
		    track = tr[r] < track ? tr[r] : track;

		// Move the glyph into position
		for ( int r = 0; r < rows; r++ )
		    for ( int c = s1 - track; c < s1 - track + w + 1; c++ )
			if ( c >= s1 || g[r][c] == '.' )
			    g[r][c] = g[r][c+track];

		// Compact the rest of the glyph string
		int p = Math.max( s1, s1 - track + w + 1 );
		int m = Math.min( track, w + 1 );
		for ( int r = 0; r < rows; r++ )
		    for ( int c = p; c < cols - m; c++ )
			g[r][c] = g[r][c+m];
		cols -= m;
		s1 = s2 - m;
	    }

	    // ----- Display -----
	    // Find the first and last non-blank columns
	    int s = 0;
	    for ( int c = 1; s == 0 && c < cols; c++ )
		for ( int r = 0; s == 0 && r < rows; r++ )
		    if ( s == 0 && g[r][c] == '#' )
			s = c;
	    int t = cols;
	    for ( int c = cols - 1; t == cols && c > 0; c -- )
		for ( int r = 0; t == cols && r < rows; r++ )
		    if ( t == cols && g[r][c] == '#' )
			t = c;
	    // Dump the array
	    for ( int r = 0; r < rows; r++ ) {
		// Skip blank columns
		for ( int c = s; c <= t; c++ )
		    // Output, remembering to hide invisible pixels
		    System.out.print( g[r][c] == '0' ? '.' : g[r][c] );
		System.out.println();
	    }
	}
    }
}