/*  
    digitsum.java
    Digit Sum, MCPC 2013, Problem F
    Java solution by Andy Harrington
*/

import java.util.*;
import java.io.*;

public class digitsum
{
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "digitsum.in";
        Scanner in = new Scanner(new File(file));
        int N = in.nextInt();
        while (N != 0) {
            int[] dig = new int[N];
            for (int i = 0; i < N; i++)
                dig[i] = in.nextInt();
            Arrays.sort(dig);
            int nzi = 0;  // will be index of first nonzero digit
            while (dig[nzi] == 0) 
                nzi++;
            for (int i = 0; i < 2; i++)    // swap up to 2 leading 0's 
                if (dig[i] == 0) {         //   for smallest non-zero digits
                    dig[i] = dig[nzi + i];
                    dig[nzi+i] = 0;
                }
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < dig.length; i += 2) { // do place value with
                sum1 = sum1*10 + dig[i];              //    every other digit
                if (i + 1 < dig.length)               // length may be odd
                    sum2 = sum2*10 + dig[i+1];
            }
            System.out.println(sum1+sum2);
            N = in.nextInt();
        }
    }
}