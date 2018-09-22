import java.io.*;
import java.util.Scanner;

class serials {

  public static void main( String[] args ) throws Exception {
    String inFile = args.length > 0 ? args[0] : "serials.in";
    Scanner in = new Scanner( new File( inFile ) );
    String s = in.nextLine();
    while ( !s.equals( "END" ) ) {
      System.out.println( s );
      list serials = new list();
      int a = in.nextInt();
      while ( a != 0 ) {
        int b = in.nextInt();
        char c = in.next().charAt(0);
        int t = in.nextInt();
        serials.insert( a, b, c, t );
        a = in.nextInt();
      }
      serials.display();
      in.nextLine();
      s = in.nextLine();
    }
  }
  
}

class list {

  node head;
  
  list() {
    head = new node( 0, 0, '0', 0, null );
  }
  
  void insert( int x, int y, char c, int t ) {
  
    // find the insert point
    node p = head;
    node q = head.next;
    while ( q != null && q.a <= x ) {
      p = q;
      q = q.next;
    }
    
    // insert the new node
    q = new node( p.a, p.b, p.c, p.t, p.next );
    if ( x == q.a ) {
      p.b = y;
      p.c = c;
      p.t = t;
      if ( y < q.b ) {
        p.next = new node( y+1, q.b, q.c, q.t, p.next );
      }
    }
    else if ( x <= q.b ) {
      p.b = x-1;
      p.next = new node( x, y, c, t, p.next );
      p = p.next;
      if ( y < q.b ) {
        p.next = new node( y+1, q.b, q.c, q.t, p.next );
      }
    }
    else {
      p.next = new node( x, y, c, t, p.next );
      p = p.next;
    }
    
    // cleanup any successor nodes overlapped by the new node
    while ( p.next != null && y >= p.next.a ) {
      if ( y >= p.next.b ) {
        p.next = p.next.next;
      }
      else {
        p.next.a = y + 1;
      }
    }
    
    // merge adjacent serial number ranges with identical codes
    p = head;
    while ( p.next != null ) {
      if ( p.b + 1 == p.next.a && p.c == p.next.c && p.t == p.next.t ) {
        p.b = p.next.b;
        p.next = p.next.next;
      }
      else {
        p = p.next;
      }
    }
    
  }
  
  void display() {
    node p = head.next;
    while ( p != null ) {
      System.out.format( "%d %d %c %d%n", p.a, p.b, p.c, p.t );
      p = p.next;
    }
  }
  
}

class node {

  int a;  // beginning serial number
  int b;  // ending serial number
  char c; // status code
  int t;  // transfer code;
  node next;
  
  node( int aIn, int bIn, char cIn, int tIn, node nextIn ) {
    a = aIn;
    b = bIn;
    c = cIn;
    t = tIn;
    next = nextIn;
  }
    
}