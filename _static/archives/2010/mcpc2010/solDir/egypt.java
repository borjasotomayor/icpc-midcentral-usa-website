// Egyptian Fractions, Java version by Andy Harrington 
import java.util.*;
import java.io.*;

public class egypt  
{
    static long MAX = 1000000;

    static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a  % b;
            a = temp;
        }
        return a;
    }
    
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "egypt.in";
        Scanner in = new Scanner(new File(file));
        while(true) {
            long m  = in.nextInt(), n = in.nextInt();// m/n is rest to process
            if (m == 0) break;
            String sep = "";
            while (m > 0) {
                 long c, denom = (n + m - 1) / m;  // integer ceiling
                 while (true) { // m/n - 1/denom = (m*denom-n)/(n*denom)
                     c = gcd(m*denom-n, n*denom);                    
                     if (n*denom/c < MAX) // reduced denominator small enough
                         break;
                     denom++;
                 }
                 System.out.print(sep + denom);
                 sep = " ";
                 m = (m*denom - n)/c;
                 n = n*denom/c;
            }
            System.out.println();
        }
    }
}

