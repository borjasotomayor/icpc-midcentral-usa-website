/* guimaze.java Solution by Ron Pacheco <ron@pacheco.net> 
   2008 ACM Mid-Central Regional Contest
   Problem G: Line & Circle Maze */
   
/* See maze.java for a complete description of the algorithm implemented by
   this program. This is simply a visualization tool. */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

class guimaze {

  private static JFrame guiFrame;
  private static MazeVisualizer maze;
  private static JLabel statusLabel;
  private static Scanner in;
  
  static char ch;
  static TreeMap<Point,HashSet<Object>> points;
  static int caseNumber = 0;
  static ArrayList<Object> shapes;
  static int N;
  static double graph[][];

  public static void main( String[] args ) throws Exception {
    in = new Scanner( new File( args.length > 0 ? args[0] : "maze.in" ) );
    ch = in.next().charAt(0);
    javax.swing.SwingUtilities.invokeLater( new Runnable() { public void run() { gui(); } } );
  }
  
  private static void gui() {
    guiFrame = new JFrame( "Line & Circle Maze" );
    guiFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    guiFrame.getContentPane().setLayout( new BorderLayout() );
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout( new FlowLayout() );
    maze = new MazeVisualizer( 720, 720, -10, -10, 110, 110 );
    //maze = new MazeVisualizer( 400, 400, -10, -10, 110, 110 );
    JPanel statusPanel = new JPanel();
    statusLabel = (JLabel)statusPanel.add( new JLabel( "Ready" ) );
    ((JButton)buttonPanel.add( new JButton( "Next Data Set" ) )).addActionListener( new ButtonListener() );
    guiFrame.getContentPane().add( BorderLayout.NORTH, buttonPanel );
    guiFrame.getContentPane().add( BorderLayout.CENTER, maze );
    guiFrame.getContentPane().add( BorderLayout.SOUTH, statusPanel );
    guiFrame.pack();
    guiFrame.setVisible( true );
  }
  
  private static class ButtonListener implements ActionListener {

    public void actionPerformed( ActionEvent actionEvent ) {

      String buttonName = ((JButton)actionEvent.getSource()).getText();
      if ( buttonName.equals( "Next Data Set" ) ) {
        if ( ch == '*' ) { guiFrame.dispose(); return; }
        shapes = new ArrayList<Object>();
        while ( ch == 'L' || ch == 'C' ) {
          shapes.add( ch == 'L'
            ? new Line( in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt() )
            : new Circle( in.nextInt(), in.nextInt(), in.nextInt() ) );
          in.nextLine();
          ch = in.next().charAt(0);
        }
        in.nextLine();
        ch = in.next().charAt(0);
        maze.clearShapes();
        for ( int i = 0; i < shapes.size(); i++ ) {
          Object s = shapes.get(i);
          if ( s instanceof Line )
            maze.addLine( ((Line)s).x1, ((Line)s).y1, ((Line)s).x2, ((Line)s).y2 );
          else
            maze.addCircle( ((Circle)s).x, ((Circle)s).y, ((Circle)s).r );
        }

        points = new TreeMap<Point,HashSet<Object>>( new PointComparator() );
        for ( int i = 0; i < shapes.size(); i++ ) {
          Object s = shapes.get(i);
          if ( s instanceof Line ) {
            addPoint( new Point( ((Line)s).x1, ((Line)s).y1 ), s );
            addPoint( new Point( ((Line)s).x2, ((Line)s).y2 ), s );
          }
          for ( int j = i + 1; j < shapes.size(); j++ ) {
            Object t = shapes.get(j);
            if ( s instanceof Line && t instanceof Line ) {
              Point p = MyMath.xLineLine( (Line)s, (Line)t );
              if ( p != null ) {
                addPoint( p, s );
                addPoint( p, t );
              }
            }
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
        for ( Iterator i = points.keySet().iterator(); i.hasNext(); ) {
          Point p = (Point)i.next();
          maze.addPoint( p.x, p.y );
        }
        N = points.size();
        graph = new double[N][N];
        for ( int i = 0; i < N; i++ )
          for ( int j = 0; j < N; j++ )
            if ( i == j )
              graph[i][j] = 0;
            else
              graph[i][j] = MyMath.INFINITY;
        Point pointsById[] = new Point[ N ];
        for ( Iterator i1 = points.keySet().iterator(); i1.hasNext(); ) {
          Point p = (Point)i1.next();
          pointsById[ p.n ] = p;
          for ( Iterator i2 = points.get(p).iterator(); i2.hasNext(); ) {
            Object s = i2.next();
            for ( Iterator i3 = points.keySet().iterator(); i3.hasNext(); ) {
              Point q = (Point)i3.next();
              if ( p.n != q.n && points.get(q).contains( s ) ) {
                if ( s instanceof Line )
                  graph[p.n][q.n] = graph[q.n][p.n] = MyMath.linearDistance( p, q );
                else
                  graph[p.n][q.n] = graph[q.n][p.n] = MyMath.arcLength( (Circle)s, p, q );
              }
            }
          }
        }
        for ( int k = 0; k < N; k++ )
          for ( int i = 0; i < N; i++ )
            for ( int j = 0; j < N; j++ )
              graph[i][j] = Math.min( graph[i][j], graph[i][k] + graph[k][j] );

        double m = 0;
        int a = 0, b = 0;
        for ( int i = 0; i < N; i++ )
          for ( int j = 0; j < N; j++ )
            if ( !MyMath.isInfinite( graph[i][j] ) && graph[i][j] > m ) {
              a = i;
              b = j;
              m = graph[i][j];
            }
        statusLabel.setText( String.format( "Case #%d: %f", ++caseNumber, m ) );
        maze.addFarPoints( pointsById[a].x, pointsById[a].y, pointsById[b].x, pointsById[b].y );
        
      }
    }
  }
  
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

class MazeVisualizer extends Component implements ComponentListener {

  private Dimension displaySize;
  private Point2D.Double userLowerLeft, userUpperRight;
  ArrayList<Shape> shapes;
  ArrayList<Point2D.Double> points;
  Point2D.Double farP1, farP2;
  boolean showFarPoints = false;
  
  public MazeVisualizer( int dWidth, int dHeight, double xMin, double yMin, double xMax, double yMax ) {
    displaySize = new Dimension( dWidth, dHeight );
    userLowerLeft = new Point2D.Double( xMin, yMin );
    userUpperRight = new Point2D.Double( xMax, yMax );
    shapes = new ArrayList<Shape>( 25 );
    points = new ArrayList<Point2D.Double>( 500 );
    addComponentListener( this );
  }
  
  public void addShape( Shape s ) {
    shapes.add( s );
    repaint();
  }
  
  public void addLine( double x1, double y1, double x2, double y2 ) {
    addShape( new Line2D.Double( x1, y1, x2, y2 ) );
  }
  
  public void addCircle( double x, double y, double r ) {
    addShape( new Ellipse2D.Double( x - r, y - r, 2 * r, 2 * r ) );
  }
  
  public void addPoint( double x, double y ) {
    points.add( new Point2D.Double( x, y ) );
    repaint();
  }
  
  public void addFarPoints( double x1, double y1, double x2, double y2 ) {
    farP1 = new Point2D.Double( x1, y1 );
    farP2 = new Point2D.Double( x2, y2 );
    showFarPoints = true;
    repaint();
  }
  
  public void clearShapes() {
    shapes = new ArrayList<Shape>( 25 );
    points = new ArrayList<Point2D.Double>( 500 );
    showFarPoints = false;
    repaint();
  }

  public Dimension getPreferredSize() {
    return displaySize;
  }
  
  private void setUserCoordinates( Graphics2D g2d ) {
    double mx = getWidth() / ( userUpperRight.getX() - userLowerLeft.getX() );
    double my = getHeight() / ( userLowerLeft.getY() - userUpperRight.getY() );
    g2d.transform( new AffineTransform( mx, 0, 0, my, -mx * userLowerLeft.getX(), -my * userUpperRight.getY() ) );
  }
  
  private float pixelSize() {
    return (float) ( userUpperRight.getX() - userLowerLeft.getX() ) / getWidth();
  }
  
  private void drawAxes( Graphics2D g2d, double min, double max, double arrowRotDeg, double arrowLen, Stroke stroke, Color color ) {
    g2d.setStroke( stroke );
    g2d.setColor( color );
    g2d.draw( new Line2D.Double( min, 0, max, 0 ) );
    g2d.draw( new Line2D.Double( 0, min, 0, max ) );
  }
  
  private void drawGrid( Graphics2D g2d, double min, double max, double step, Stroke stroke, Color color  ) {
    g2d.setStroke( stroke );
    g2d.setColor( color );
    double p = min;
    while ( p <= max ) {
      g2d.draw( new Line2D.Double( min, p, max, p ) );
      g2d.draw( new Line2D.Double( p, min, p, max ) );
      p += step;
    }
  }
  
  public void paint( Graphics g ) {
    Graphics2D g2d = (Graphics2D)g;
    setUserCoordinates( g2d );
    drawGrid( g2d, 0, 100, 10, new BasicStroke( pixelSize() ), Color.LIGHT_GRAY );
    drawAxes( g2d, -5, 105, 30, 3, new BasicStroke( 3 * pixelSize(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ), Color.GRAY );
    g2d.setStroke( new BasicStroke( 3 * pixelSize() ) );
    for ( int i = 0; i < shapes.size(); i++ ) {
      Shape s = shapes.get(i);
      if ( s instanceof Line2D ) {
        g2d.setColor( Color.BLUE );
      }
      else if ( s instanceof Ellipse2D ) {
        g2d.setColor( Color.MAGENTA );
      }
      else {
        g2d.setColor( Color.RED );
      }
      g2d.draw( shapes.get(i) );
    }
    g2d.setStroke( new BasicStroke( pixelSize() ) );
    g2d.setColor( Color.CYAN );
    for ( int i = 0; i < points.size(); i++ ) {
      Point2D.Double p = points.get(i);
      g2d.fill( new Ellipse2D.Double( p.getX() - 0.75, p.getY() - 0.75, 1.5, 1.5 ) );
    }
    if ( showFarPoints ) {
      g2d.setStroke( new BasicStroke( pixelSize() ) );
      g2d.setColor( Color.RED );
      g2d.fill( new Ellipse2D.Double( farP1.getX() - 1.5, farP1.getY() - 1.5, 3, 3 ) );
      g2d.fill( new Ellipse2D.Double( farP2.getX() - 1.5, farP2.getY() - 1.5, 3, 3 ) );
    }
  }
  
  public void componentResized( ComponentEvent e ) {
    int width = getWidth();
    int height = getHeight();
    if ( width != height ) {
      int setTo = width < height ? width : height;
      setSize( setTo, setTo );
    }
  }

  public void componentMoved(ComponentEvent e) {
  }

  public void componentShown(ComponentEvent e) {
  }

  public void componentHidden(ComponentEvent e) {
  }
  
}

class Edge {
  public double x1, y1, x2, y2, w;
  public Edge( double x1, double y1, double x2, double y2, double w ) {
    this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2; this.w = w;
  }
}

class Line {
  public int x1, y1, x2, y2;
  public Line( int x1, int y1, int x2, int y2 ) { this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2; }
}

class Circle {
  public int x, y, r;
  public Circle( int x, int y, int r ) { this.x = x; this.y = y; this.r = r; }
}

class Point {
  int n;
  public double x, y;
  public Point( double x, double y ) { this.x = x; this.y = y; }
  public Point( int n, Point p ) { this.n = n; this.x = p.x; this.y = p.y; }
}

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

class MyMath {

  public static final double EPSILON = 1E-8;
  public static final double INFINITY = 1E+8;
  
  public static boolean eqDouble( double a, double b ) { return a >= b - EPSILON && a <= b - EPSILON; }

  public static boolean ltDouble( double a, double b ) { return b - a > EPSILON; }
  
  public static boolean gtDouble( double a, double b ) { return a - b > EPSILON; }
  
  public static boolean isInfinite( double a ) { return !ltDouble( a, INFINITY ); }
  
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
    if ( D == 0 ) 
      System.err.format( ">>Tangent? L %d %d %d %d, C %d %d %d%n", l.x1, l.y1, l.x2, l.y2, c.x, c.y, c.r );
    if ( D < 0 ) return null;
    ArrayList<Point> points = new ArrayList<Point>( 2 );
    double t = ( -B + Math.sqrt( D ) ) / ( 2 * A );
    if ( t >= 0 && t <= 1 ) {
      points.add( new Point( l.x1 + t * dx, l.y1 + t * dy ) );
      if ( t < 0.001 || t > 0.999 ) 
        System.err.format( ">>Degenerate? L %d %d %d %d, C %d %d %d%n", l.x1, l.y1, l.x2, l.y2, c.x, c.y, c.r );
    }
    t = ( -B - Math.sqrt( D ) ) / ( 2 * A );
    if ( t >= 0 && t <= 1 ) {
      points.add( new Point( l.x1 + t * dx, l.y1 + t * dy ) );
      if ( t < 0.001 || t > 0.999 ) 
        System.err.format( ">>Degenerate? L %d %d %d %d, C %d %d %d%n", l.x1, l.y1, l.x2, l.y2, c.x, c.y, c.r );
    }
    return points;
  }
  
  public static ArrayList<Point> xCircleCircle( Circle c1, Circle c2 ) {
    if ( c1.x == c2.x && c1.y == c2.y && c1.r == c2.r )
      System.err.format( ">>Degenerate? C %d %d %d, C %d %d %d%n", c1.x, c1.y, c1.r, c2.x, c2.y, c2.r );
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
    if ( Math.abs( rx ) < 0.001 && Math.abs( ry ) < 0.001 )
      System.err.format( ">>Degenerate? C %d %d %d, C %d %d %d%n", c1.x, c1.y, c1.r, c2.x, c2.y, c2.r );
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
