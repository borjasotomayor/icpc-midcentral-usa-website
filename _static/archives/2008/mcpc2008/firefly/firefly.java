import java.io.*;
import java.util.Scanner;

class firefly {

  public static void main( String[] args ) throws Exception {
    String inFile = args.length > 0 ? args[0] : "firefly.in";
    Scanner in = new Scanner( new File( inFile ) );
    double s = in.nextInt(); // my speed
    double x = in.nextInt(); // my x
    double y = in.nextInt(); // my y
    int n = 0;

    while ( s > 0 ) {
      n++;
      int fx = in.nextInt(); // firefly x
      int fy = in.nextInt(); // firefly y
      boolean caught = false;
      while ( fx >= 0 ) {
        double d = Math.sqrt( (fx-x)*(fx-x) + (fy-y)*(fy-y) );
        if ( d - s <= 1 && !caught ) {
          System.out.format( "Firefly %d caught at (%d,%d)%n", n, fx, fy );
          caught = true;
          /* sanity check */ if ( d - s > 0.999 ) System.err.format( "Borderline! D=%f%n", d - s );
        }
        else if ( !caught ) {
          x = x + s/d * (fx-x);
          y = y + s/d * (fy-y);
        }
        fx = in.nextInt();
        fy = in.nextInt();
      }
      if ( !caught ) {
        System.out.format( "Firefly %d not caught%n", n );
      }
      s = in.nextDouble();
      x = in.nextInt();
      y = in.nextInt();
    }
  }

}