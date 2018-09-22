// 2005 ACM Mid-Central Regional Programming Contest
// Problem D: Copier Reduction
// Posed by John Cigas, Rockhurst University
// Written by Eric Shade, Missouri State University
// by Andy Harrington, Loyola University Chicago

// integer arithmetic loop solution

import java.io.*;
import java.util.*;

    
public class copier {
    
  public static void main(String[] args) {
    //  standard I/O setup follows
    Scanner in = null;
    String file = (args.length > 0) ? args[0] : "copier.in";
    try {
      in = new Scanner(new BufferedReader(  
                       new FileReader(file))); 
    } catch(Exception e) {
      System.out.println("Can't open input " + file);
    }
    
    int imX = in.nextInt();
    while (imX > 0) {
      int imY = in.nextInt(), 
          pX = in.nextInt(), 
          pY = in.nextInt(),
          percent = 100;
      
      while ((percent*imX > pX*100 || percent*imY > pY*100) && // not rotate
             (percent*imY > pX*100 || percent*imX > pY*100))   // do rotate
         percent--;
      System.out.println(percent + "%");
      imX = in.nextInt();
    }
  }   
} 
