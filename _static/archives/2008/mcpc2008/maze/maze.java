/* maze.java Solution by Ron Pacheco <ron@pacheco.net> 
   2008 ACM Mid-Central Regional Contest
   Problem G: Line & Circle Maze */

import java.io.*;
import java.util.*;

/* The endpoints of all line segments along with all object intersections form
   a weighted graph on which we use an all-pairs shortest path algorithm. A
   conceptually easy solution but challenging to implement! 
   
   Here is a sketch of this program's approach:
   
   1) Construct a set of points containing all line endpoints and all object
      intersections.
      
   2) Compute the distances (either linear distance or circular arc length)
      between all pairs of points that lie on the same object. To facilitate
      this, Step 1 keeps a list of all the objects incident to each point.
      
   3) Construct an adjacency matrix from the distances found in Step 2.
   
   4) Use the simple Floyd-Warshall all-pairs shortest path algorithm.
   
   5) Search all pairs for the longest minimum path.
 */

class maze {

  // The critical data structure! The points object is a mapping of points to
  // the set of objects incident to that point.
  static TreeMap<Point,HashSet<Object>> points;
  
  public static void main( String[] args ) throws Exception {
  
    // Open file specified on command line, else "maze.in"
    Scanner in = new Scanner( new File( args.length > 0 ? args[0] : "maze.in" ) );
    
    // Track the input case number
    int caseNumber = 0;
    
    // First character of input case; "*" means end of input
    char ch = in.next().charAt(0);
    while ( ch != '*' ) {
    
      // The shapes object holds all the input shapes. When I first coded this
      // solution there wasn't a cap on the number of objects. Later we
      // introduced a maximum of 25 objects, so this could just as easily now
      // be a simple array.
      ArrayList<Object> shapes = new ArrayList<Object>();
      
      // Loop through all the input lines and add the shapes
      while ( ch == 'L' || ch == 'C' ) {
        shapes.add( ch == 'L'
          ? new Line( in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt() )
          : new Circle( in.nextInt(), in.nextInt(), in.nextInt() ) );
        in.nextLine();
        ch = in.next().charAt(0);
      }
      
      // Init points structure for the new test case
      points = new TreeMap<Point,HashSet<Object>>( new PointComparator() );
      
      // Look for intersections between every possible pair of objects
      for ( int i = 0; i < shapes.size(); i++ ) {
        Object s = shapes.get(i);
        
        // For line objects, add their endpoints to the points map
        if ( s instanceof Line ) {
          addPoint( new Point( ((Line)s).x1, ((Line)s).y1 ), s );
          addPoint( new Point( ((Line)s).x2, ((Line)s).y2 ), s );
        }
        
        // Inner loop comparing all pairs of shapes
        for ( int j = i + 1; j < shapes.size(); j++ ) {
          Object t = shapes.get(j);
          
          // Two line segments
          if ( s instanceof Line && t instanceof Line ) {
            Point p = MyMath.xLineLine( (Line)s, (Line)t );
            if ( p != null ) {
              addPoint( p, s );
              addPoint( p, t );
            }
          }
          
          // Two circles
          else if ( s instanceof Circle && t instanceof Circle ) {
            ArrayList<Point> intersections = MyMath.xCircleCircle( (Circle)s, (Circle)t );
            if ( intersections != null ) {
              Iterator k = intersections.iterator();
              while ( k.hasNext() ) {
                Point p = (Point)k.next();
                addPoint( p, s );
                addPoint( p, t );
              }
            }
          }
          
          // A line and a circle
          else {
            ArrayList<Point> intersections;
            if ( s instanceof Line )
              intersections = MyMath.xLineCircle( (Line)s, (Circle)t );
            else
              intersections = MyMath.xLineCircle( (Line)t, (Circle)s );
            if ( intersections != null ) {
              Iterator k = intersections.iterator();
              while ( k.hasNext() ) {
                Point p = (Point)k.next();
                addPoint( p, s );
                addPoint( p, t );
              }
            }
          }
        }
      }
      
      // Initialize the graph to infinitely weighted edges
      int N = points.size();
      double graph[][] = new double[N][N];
      for ( int i = 0; i < N; i++ )
        for ( int j = 0; j < N; j++ )
          if ( i == j )
            graph[i][j] = 0;
          else
            graph[i][j] = MyMath.INFINITY;
            
      // For every point...
      for ( Iterator i1 = points.keySet().iterator(); i1.hasNext(); ) {
        Point p = (Point)i1.next();
        
        // and every object the point intersects...
        for ( Iterator i2 = points.get(p).iterator(); i2.hasNext(); ) {
          Object s = i2.next();
          
          // and every other point on the same object . . .
          for ( Iterator i3 = points.keySet().iterator(); i3.hasNext(); ) {
            Point q = (Point)i3.next();
            if ( p.n != q.n && points.get(q).contains( s ) ) {
              // find the distance between the points.
              if ( s instanceof Line )
                graph[p.n][q.n] = graph[q.n][p.n] = MyMath.linearDistance( p, q );
              else
                graph[p.n][q.n] = graph[q.n][p.n] = MyMath.arcLength( (Circle)s, p, q );
            }
          }
        }
      }

      // Finally, the easiest part of the whole thing! Run the Floyd-Warshall
      // all pairs shortest path algorithm.
      for ( int k = 0; k < N; k++ )
        for ( int i = 0; i < N; i++ )
          for ( int j = 0; j < N; j++ )
            graph[i][j] = Math.min( graph[i][j], graph[i][k] + graph[k][j] );

      // If no lines and no intersection just say "No Solution". (Note that this
      // situation is prevented from occuring in the offical problem statement.)
      if ( N == 0 ) {
        System.out.format( "Case %d: No Solution%n", ++caseNumber );
      }
      
      // Otherwise find the longest of the shortest paths and output its length
      else {
        double m = 0;
        for ( int i = 0; i < N; i++ )
          for ( int j = 0; j < N; j++ )
            if ( !MyMath.isInfinite( graph[i][j] ) )
              m = Math.max( m, graph[i][j] );
        System.out.format( "Case %d: %.1f%n", ++caseNumber, m );
      }
      
      // Try for another test case
      in.nextLine();
      ch = in.next().charAt(0);
    }
  }
  
  // Utility function to add to the points data structure. If the point is
  // is already in the map, just add the object to the set of objects this
  // point intersects, otherwise add a new mapping with this point and object.

  static void addPoint( Point p, Object s ) {
    HashSet<Object> on;
    if ( points.containsKey( p ) )
      on = points.get( p );
    else {
      p = new Point( points.size(), p );
      on = new HashSet<Object>();
    }
    on.add( s );
    points.put( p, on );
  }
  
}

// Classes for Line and Circle so that they can be stored in the same
// collections.

class Line {
  public int x1, y1, x2, y2;
  public Line( int x1, int y1, int x2, int y2 ) { this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2; }
}

class Circle {
  public int x, y, r;
  public Circle( int x, int y, int r ) { this.x = x; this.y = y; this.r = r; }
}

// Point class. In addition to (x,y), we store n, which is just a unique index
// number assigned to the point so that later we can easily create an adjacency
// matrix from the points map.

class Point {
  int n;
  public double x, y;
  public Point( double x, double y ) { this.x = x; this.y = y; }
  public Point( int n, Point p ) { this.n = n; this.x = p.x; this.y = p.y; }
}

// Since we're dealing with floating point approximations to real numbers, use
// a comparator for points that takes our float epsilon into account.

class PointComparator implements Comparator<Point> {
  public int compare( Point a, Point b ) {
    if ( MyMath.ltDouble( a.x, b.x ) )
      return -1;
    else if ( MyMath.gtDouble( a.x, b.x ) )
      return 1;
    else {
      if ( MyMath.ltDouble( a.y, b.y ) )
        return -1;
      else if ( MyMath.gtDouble( a.y, b.y ) )
        return 1;
      else
        return 0;
    }
  }
}

// There was enough math in this problem that I decided to pull it all out into
// a separate class because it was cluttering up the main algorithm.

class MyMath {

  // Epsilon and infinity constants sufficient for this problem

  public static final double EPSILON = 1E-8;
  public static final double INFINITY = 1E+8;
  
  // Floating point epsilon-based comparisons
  
  public static boolean eqDouble( double a, double b ) { return a >= b - EPSILON && a <= b - EPSILON; }

  public static boolean ltDouble( double a, double b ) { return b - a > EPSILON; }
  
  public static boolean gtDouble( double a, double b ) { return a - b > EPSILON; }
  
  public static boolean isInfinite( double a ) { return !ltDouble( a, INFINITY ); }
  
  // The math algorithms. Many other implementations are possible. See any
  // decent reference on geometric algorithms.
  
  public static Point xLineLine( Line l1, Line l2 ) {
    int x1 = l1.x1, y1 = l1.y1, x2 = l1.x2, y2 = l1.y2, x3 = l2.x1, y3 = l2.y1, x4 = l2.x2, y4 = l2.y2;
    int d = ( y4 - y3 ) * ( x2 - x1 ) - ( x4 - x3 ) * ( y2 - y1 );
    if ( d == 0 ) return null;
    double a = ( ( x4 - x3 ) * ( y1 - y3 ) - ( y4 - y3 ) * ( x1 - x3 ) ) / (double)d;
    double b = ( ( x2 - x1 ) * ( y1 - y3 ) - ( y2 - y1 ) * ( x1 - x3 ) ) / (double)d;
    if ( a < 0 || a > 1 || b < 0 || b > 1 ) return null;
    return new Point( x1 + a * ( x2 - x1 ), y1 + a * ( y2 - y1 ) );
  }

  public static ArrayList<Point> xLineCircle( Line l, Circle c ) {
    int dx = l.x2 - l.x1;
    int dy = l.y2 - l.y1;
    int A = dx * dx + dy * dy;
    int B = 2 * ( dx * ( l.x1 - c.x ) + dy * ( l.y1 - c.y ) );
    int C = ( l.x1 - c.x ) * ( l.x1 - c.x ) + ( l.y1 - c.y ) * ( l.y1 - c.y ) - c.r * c.r;
    int D = B * B - 4 * A * C;
    if ( D <= 0 ) return null;
    ArrayList<Point> points = new ArrayList<Point>( 2 );
    double t = ( -B + Math.sqrt( D ) ) / ( 2 * A );
    if ( t >= 0 && t <= 1 ) points.add( new Point( l.x1 + t * dx, l.y1 + t * dy ) );
    t = ( -B - Math.sqrt( D ) ) / ( 2 * A );
    if ( t >= 0 && t <= 1 ) points.add( new Point( l.x1 + t * dx, l.y1 + t * dy ) );
    return points;
  }
  
  public static ArrayList<Point> xCircleCircle( Circle c1, Circle c2 ) {
    int dx = c2.x - c1.x;
    int dy = c2.y - c1.y;
    double d = Math.sqrt( dx * dx + dy * dy );
    if ( d > c1.r + c2.r || d < Math.abs( c1.r - c2.r ) ) return null;
    double a = ( c1.r * c1.r  - c2.r * c2.r  + d * d ) / ( 2 * d );
    double x = c1.x + dx * a / d;
    double y = c1.y + dy * a / d;
    double h = Math.sqrt( c1.r * c1.r - a * a );
    double rx = -dy * h / d;
    double ry = dx * h / d;
    ArrayList<Point> points = new ArrayList<Point>( 2 );
    points.add( new Point( x + rx, y + ry ) );
    points.add( new Point( x - rx, y - ry ) );
    return points;
  }
  
  public static double linearDistance( Point p, Point q ) {
    return Math.sqrt( ( p.x - q.x ) * ( p.x - q.x ) + ( p.y - q.y ) * ( p.y - q.y ) );
  }
  
  public static double arcLength( Circle c, Point p, Point q ) {
    double theta = Math.abs( Math.atan2( p.y - c.y, p.x - c.x ) - Math.atan2( q.y - c.y, q.x - c.x ) );
    return c.r * Math.min( theta, 2 * Math.PI - theta );
  }

}