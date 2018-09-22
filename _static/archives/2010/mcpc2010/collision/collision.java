/* Queen Collison, MCPC 2010 Problem D by Andy Harrington
input:

Each dataset:
n g, where n indicates an n x n board size, and g is the number linear patterns of queens 
g lines:
k x0 y0 dx dy  for k points at equal intervals

*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import java.awt.Point;


public class collision
{
    static int n, g, nPt, tot,// board size, tot linear seq, # of queens, tot collisions
               k, x0, y0, dx, dy, x, y, // pattern line, current point
               MAX_N = 29999, MAX_G = 249; // check against final problem statement!
    
    
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "collision.in";
        Scanner in = new Scanner(new File(file));
        while (true) {
            n  = in.nextInt();
            if (n == 0) break;
            HashSet<Point> pts = new HashSet<Point>(); // for judge test
            boolean[] xVal = new boolean[n+1], yVal = new boolean[n+1],
                      sum = new boolean[2*n+2], 
                      dif = new boolean[2*n];  // dif offset by n 
            tot = nPt = 0;   
            g = in.nextInt();
            for (int i = 0; i < g; i++) { 
                k = in.nextInt();
                x = x0 = in.nextInt();
                y = y0 = in.nextInt();
                dx = in.nextInt();
                dy = in.nextInt();
                nPt += k;
                for (int j=0; j < k; j++) {
                    test(x, y, pts);  // judge tests
                    set (xVal, x); 
                    set (yVal, y); 
                    set(sum, x+y); 
                    set(dif, x-y+n);
                    x += dx; y += dy; 
                }
            }
            System.out.println(tot);
            judgeCheck();
        }
    }
  
    static void set(boolean[] a, int i) {
        if (a[i]) 
            tot++;
        else
            a[i] = true;
    }  

// only judge's tests follow:  

    static void test(int x, int y, HashSet<Point> pts) {
        if (x <= 0 || x > n || y <= 0 || y > n)  
            System.out.format("Out of range!! %s, %s in %s %s %s %s %s%n",
                                               x,  y,    k,x0,y0,dx,dy);
        Point xy = new Point(x, y);
        if (pts.contains(xy)) 
            System.out.format("Dup!! %s, %s in %s %s %s %s %s%n",
                                      x,  y,    k,x0,y0,dx,dy);
        else
            pts.add(xy);
    }

    static void judgeCheck() {
        // check against final problem statement  (also MAX's at top)!
        if (n > MAX_N || g > MAX_G  || nPt > n) 
             System.err.println("Bad param!");
    }

}
