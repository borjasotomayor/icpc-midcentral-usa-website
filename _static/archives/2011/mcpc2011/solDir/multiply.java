/* Grade School Multiplication, MCPC 2011 Problem A, Java solution by Andy Harrington */
import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class multiply {
    
    static String rep(char ch, int n) {
        String s= "";
        for (; n > 0; n--)
            s += ch;
        return s;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("multiply.in"));
        int x = in.nextInt();
        int ds = 1;
        while (x > 0) {
            System.out.println("Problem " + ds);
            long y = in.nextInt();
            String ans = "" + x*y;
            int n = ans.length();
            String fstr = "%"+n+"s%n";  
            System.out.format(fstr, x);
            System.out.format(fstr, y);
            System.out.println(rep('-', n));
            int len = n, lines = 0, zeroes = 0; 
            while (y > 0) {
                long dig = y % 10;
                if (dig == 0) 
                    zeroes++;
                else {
                    System.out.format("%"+len+"s" , x*dig);
                    System.out.println(rep('0', zeroes));
                    zeroes= 0;
                    lines++;
                }
                len--;
                y /= 10;
            }
            if (lines>1) {
                System.out.println(rep('-', n));
                System.out.println(ans);
            }
            ds++;
            x = in.nextInt();
        }
    }
}
