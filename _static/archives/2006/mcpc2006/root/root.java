// 2006 ACM Mid-Central USA Regional Programming Contest
// Solution to "Root of the Problem" [very easy]
// Ron Pacheco

import java.io.*;
import java.util.Scanner;

public class root {

    public static void main( String[] args ) throws Exception {
	Scanner in = new Scanner( new File( "root.in" ) );
	int B = in.nextInt();
	int N = in.nextInt();
	int A = 0;
	int D = B;
	while ( B > 0 || N > 0 ) {
	    int T = abs( pow( A + 1, N ) - B );
	    while ( T < D ) {
		A++;
		D = T;
		T = abs( pow( A + 1, N ) - B );
	    }
	    System.out.println( A );
	    B = in.nextInt();
	    N = in.nextInt();
	    A = 0;
	    D = B;
	}
    }
    
    private static int abs( int i ) {
	if ( i < 0 )
	    return -i;
	else
	    return i;
    }
    
    private static int pow( int a, int n ) {
	int p = 1;
	for ( int i = 0; i < n; i++ )
	    p *= a;
	return p;
    }
    
}
