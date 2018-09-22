import java.io.*;
import java.util.Scanner;
import java.lang.Math;

public class falling {

    public static class Ice {
	public double x, y, r; // dual use as point/circle
	public int t;          // index of ice we are rolling against
	public Ice( double r ) { x = 0; y = 0; this.r = r; t = -1; }
	public Ice( double x, double y ) { this.x = x; this.y = y; r = 0; t = -1; }
	public Ice( double x, double y, double r ) { this.x = x; this.y = y; this.r = r; t = -1; }
	public Ice( double x, double y, double r, int t ) { this.x = x; this.y = y; this.r = r; this.t = t; }
    }

    public static void main( String[] args ) throws Exception {
	Scanner in = new Scanner( new File( args.length > 0 ? args[0] : "falling.in" ) );
	int w = in.nextInt();
	int dataset = 1;
	while ( w > 0 ) {
	    int n = in.nextInt();
	    Ice[] c = new Ice[n];
	    for ( int i = 0; i < n; i++ )
		c[i] = new Ice( in.nextInt() / 2.0 );
	    double h = solve( w, n, c );
	    System.out.format( "%.2f%n", h );
	    ( new DisplayCircles( w, h, dataset++, c, n ) ).setVisible( true );
	    w = in.nextInt();
	}
    }

    public static double solve( int w, int n, Ice[] c ) {
	c[0].x = c[0].y = c[0].r;
	for ( int i = 1; i < n; i++ ) {
	    place( w, c, i );
	}
	double h = 0;
	for ( int i = 0; i < n; i++ )
	    h = Math.max( h, c[i].y + c[i].r );
	return h;
    }

    public static void place( int w, Ice[] c, int i ) {
	// start by dropping down left wall
	drop( w, c, i );
	// track best position
	Ice p = new Ice( c[i].x, c[i].y, c[i].r );
	// repeatedly roll to the right
	while ( rotate( w, c, i ) ) {
	    if ( c[i].y < p.y ) {
		p.x = c[i].x;
		p.y = c[i].y;
	    }
	}
	// set disk to it's best position
	c[i].x = p.x;
	c[i].y = p.y;
    }

    public static boolean rotate( int w, Ice[] c, int i ) {
	// return if we have hit the box
	if ( c[i].t < 0 ) return false;
	Ice r = null; // closest intersection
	double a = 7; // angle of closest intersection
	// check for intersections in rotational path
	for ( int j = -2; j < i; j++ ) {
	    Ice e;
	    if ( j == -2 ) // right side of box
		e = circle_vline( c[c[i].t].x, c[c[i].t].y, c[c[i].t].r + c[i].r, w - c[i].r );
	    else if ( j == -1 ) // bottom of box
		e = circle_hline( c[c[i].t].x, c[c[i].t].y, c[c[i].t].r + c[i].r, c[i].r );
	    else // disc # j
		e = circle_circle( c[c[i].t].x, c[c[i].t].y, c[c[i].t].r + c[i].r, c[j].x, c[j].y, c[j].r + c[i].r );
	    if ( e != null && e.x > c[i].x ) {
		double a1 = angle( c[c[i].t].x, c[c[i].t].y, c[i].x, c[i].y, e.x, e.y );
		if ( r == null || a1 < a ) {
		    r = new Ice( e.x, e.y, c[i].r, j );
		    a = a1;
		}
	    }
	}
	// did we find a new location?
	if ( r != null ) {
	    c[i].x = r.x;
	    c[i].y = r.y;
	    c[i].t = r.t;
	    return true;
	}
	else
	    return false;
    }

    public static double angle( double h, double k, double x1, double y1, double x2, double y2 ) {
	double t1 = theta( h, k, x1, y1 );
	double t2 = theta( h, k, x2, y2 );
	double t = t1 - t2;
	if ( t < 0 )
	    t += 2 * Math.PI;
	return t;
    }

    public static double theta( double h, double k, double x, double y ) {
	double a = Math.atan( ( y - k ) / ( x - h ) );
	if ( x - h >= 0 )
	    if ( y - k >= 0 )
		return a; // q1
	    else
		return 2 * Math.PI + a; // q4
	else
	    return Math.PI + a; // q2 & q3
    }
    
    public static void drop( int w, Ice[] c, int i ) {
	c[i].x = c[i].r;
	c[i].y = 0;
	for ( int j = 0; j < i; j++ ) {
	    Ice p = circle_vline( c[j].x, c[j].y, c[j].r + c[i].r, c[i].x );
	    if ( p != null && p.y > c[i].y ) {
		c[i].y = p.y;
		c[i].t = j;
	    }
	}
    }

    public static Ice circle_vline( double h, double k, double r, double x ) {
	double y = r * r - ( x - h ) * ( x - h );
	return ( y < 0 ) ? null : new Ice( x, Math.sqrt( y ) + k );
    }

    public static Ice circle_hline( double h, double k, double r, double y ) {
	double x = r * r - ( y - k ) * ( y - k );
	return ( x < 0 ) ? null : new Ice( Math.sqrt( x ) + h, y );
    }

    public static Ice circle_circle( double h1, double k1, double r1, double h2, double k2, double r2 ) {
	double dx = h2 - h1;
	double dy = k2 - k1;
	double D = Math.sqrt( dx * dx + dy * dy );
	double E = ( r1 * r1 - r2 * r2 + D * D ) / ( 2 * D );
	if ( r1 * r1 - E * E >= 0 ) {
	    double F = Math.sqrt( r1 * r1 - E * E );
	    return new Ice( h1 + ( E * dx - F * dy ) / D, k1 + ( F * dx + E * dy ) / D );
	}
	else
	    return null;
    }

}