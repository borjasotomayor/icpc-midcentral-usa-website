// variant on serials.java with ArrayList by Andy Harrington
import java.io.*;
import java.util.*;

class as {
  static ArrayList<Rec> rec = new ArrayList<Rec>();

  public static void main( String[] args ) throws Exception {
    String inFile = args.length > 0 ? args[0] : "serials.in";
    Scanner in = new Scanner( new File( inFile ) );
    String s = in.nextLine();
    while ( !s.equals( "END" ) ) {
      System.out.println( s );
      rec.clear(); 
      rec.add(new Rec(0,0,'0', 0)); // dummy header, before everything real
      int a = in.nextInt();
      while ( a != 0 ) {
        insert(new Rec(a, in.nextInt(), in.next().charAt(0), in.nextInt()));
        a = in.nextInt();
      }
      for (Rec r : rec)
         if (r.c != '0')
             System.out.format( "%d %d %c %d%n", r.a, r.b, r.c, r.t );
      in.nextLine();
      s = in.nextLine();
    }
  }

  static void insert(Rec r) { 
    int i = 1;  // adjusted to be the index where r inserted
    while (i < rec.size() && rec.get(i).a <= r.a)
      i++;
    Rec p = rec.get(i-1),  // starts no later than r does
        q = new Rec(r.b+1, p.b,p.c,p.t);  // in case p goes past r
    if ( p.a < r.a ) // keep part before
        p.b = java.lang.Math.min(p.b, r.a-1);
    else  // quick and dirty - deletions not minimized
        rec.remove(--i);  // i still where r will go
    if (q.b > r.b) // add part of p extending past r        
        rec.add(i, q);
    while (i < rec.size() && rec.get(i).b <= r.b)
        rec.remove(i); // remove all totally superceded later ones
    if (i < rec.size() && rec.get(i).a <= r.b)
        rec.get(i).a = r.b + 1; // if part overlap next
    // merge adjacent serial number ranges with identical codes
    p = rec.get(i-1);
    if ( p.b + 1 == r.a && p.c == r.c && p.t == r.t ) {
        r.a = p.a;   // extend r, remove previous Rec
        rec.remove(--i);  // i still index of r
    }
    if (i < rec.size()){
        p = rec.get(i);  
        if ( r.b + 1 == p.a && p.c == r.c && p.t == r.t ) {
            r.b = p.b;  // extend r, remove following Rec
            rec.remove(i);
        }
    }
    rec.add(i, r);
  }  
}

class Rec {
  int a;  // beginning serial number
  int b;  // ending serial number
  char c; // status code
  int t;  // transfer code;
  
  Rec( int aIn, int bIn, char cIn, int tIn) {
    a = aIn;
    b = bIn;
    c = cIn;
    t = tIn;
  }
}