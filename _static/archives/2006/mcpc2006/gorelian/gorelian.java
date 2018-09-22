// 2006 ACM Mid-Central USA Regional Programming Contest
// Solution to "Go Go Gorelians" [moderate/hard]
// Ron Pacheco
import java.io.*;
import java.util.Scanner;

class gorelian {

    public static void main( String[] args ) throws Exception {
	String  filename = args.length > 0 ? args[0] : "gorelian.in";
	Scanner in = new Scanner( new File( filename ) );
	int N = in.nextInt();
	while ( N > 0 ) {
	    ( new GorelianPuzzle( in, N ) ).solve();
	    N = in.nextInt();
	}
    }

    private static class GorelianPuzzle {

	private int _N;
	private boolean[][] _adj;
	private int [] _deg, _x, _y, _z;
	
	public GorelianPuzzle( Scanner in, int N ) {
	    _N = 0;
	    _adj = new boolean[1001][1001];
	    _deg = new int[1001];
	    _x = new int[1001];
	    _y = new int[1001];
	    _z = new int[1001];
	    int id1 = in.nextInt();
	    addNode( id1, in.nextInt(), in.nextInt(), in.nextInt() );
	    int id2 = in.nextInt();
	    addNode( id2, in.nextInt(), in.nextInt(), in.nextInt() );
	    link( id1, id2 );
	    N -= 2;
	    while ( N > 0 ) {
		id1 = in.nextInt();
		addNode( id1, in.nextInt(), in.nextInt(), in.nextInt() );
		id2 = nearestNeighbor( id1 );
		link( id1, id2 );
		N--;
	    }
	    // dumpGraph();
	}

	/**/
	private void dumpGraph() {
	    for ( int i = 1; i <= 1000; i++ )
		if ( _deg[i] > 0 ) {
		    System.out.print( i );
		    System.out.print( "(" );
		    System.out.print( _deg[i] );
		    System.out.print( "):" );
		    for ( int j = i + 1; j <= 1000; j++ )
			if ( _adj[i][j] ) {
			    System.out.print( " " );
			    System.out.print( j );
			}
		    System.out.println();
		}
	}
	/**/

	public void addNode( int id, int x, int y, int z ) {
	    _adj[id][id] = true;
	    _x[id] = x;
	    _y[id] = y;
	    _z[id] = z;
	    _N++;
	}

	public void link( int id1, int id2 ) {
	    _adj[id1][id2] = true;
	    _adj[id2][id1] = true;
	    _deg[id1]++;
	    _deg[id2]++;
	}

	public void removeNode( int id ) {
	    for ( int i = 1; i <= 1000; i++ )
		if ( _adj[id][i] ) {
		    _adj[id][i] = false;
		    _adj[i][id] = false;
		    _deg[i]--;
		}
	    _deg[id] = 0;
	    _N--;
	}

	public int nearestNeighbor( int id ) {
	    int nearestId = 0, nearestDist = 999999999;
	    for ( int i = 1; i <= 1000; i++ ) {
		if ( i != id && _deg[i] > 0 && dist( i, id ) < nearestDist ) {
		    nearestId = i;
		    nearestDist = dist( i, id );
		}
	    }
	    return nearestId;
	}

	public int dist( int id1, int id2 ) {
	    int dx = _x[id1] - _x[id2];
	    int dy = _y[id1] - _y[id2];
	    int dz = _z[id1] - _z[id2];
	    return dx * dx + dy * dy + dz * dz;
	}

	public void solve() {
	    boolean leaf[] = new boolean[1001];
	    while ( _N > 2 ) {
		for ( int i = 1; i <= 1000; i++ )
		    if ( _deg[i] == 1 )
			leaf[i] = true;
		    else
			leaf[i] = false;
		for ( int i = 1; i <= 1000; i++ )
		    if ( leaf[i] )
			removeNode( i );
	    }
	    if ( _N == 1 ) {
		for( int i = 1; i <= 1000; i++ )
		    if ( _adj[i][i] )
			System.out.println( i );
	    }
	    else {
		char n = 0;
		for ( int i = 1; i <= 1000; i++ )
		    if ( _adj[i][i] ) {
			System.out.print( i );
			if ( n == 0 )
			    System.out.print( ' ' );
			n++;
		    }
		System.out.println();
	    }
	}

    }

}
