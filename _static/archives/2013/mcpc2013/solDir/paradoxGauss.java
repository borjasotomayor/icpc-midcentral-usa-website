/*  ACM Mid-Central Programming competition 2013 Problem D
    solution by Andy Harrington

This file, paradoxGauss.java, has the same documentation as for paradox.java,
(see that file) except that paradox.java uses int determinants for linear 
equation solutions, while this code does Gauss elimination with exact 
integer arithmetic.  Only the last step brings in a rational number.

The C++ solution solves a linear system by substitution,
removing one variable at a time, with exact rational arithmetic.

If you alter the code near the bottom to set debug to true, 
you can see all the initial matrices and the final row reduced ones,
and the largest integer used in calculation.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class paradoxGauss  //  version with Gauss elimination in integers
{
    static char H = 'H', T = 'T';  // two symbols
    static char[] HT = {H, T};
    static String a, b,            // given patterns in the input
                  C = "C";         // search key for constant side of equation
    static List<String> pre,       // all prefixes in a, b
                        aug;       // for augmented matrix with keys pre + C
    static int n,                  // a, b length
               pn;                 // pre length
 
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "paradox.in";
        Scanner in = new Scanner(new File(file));
        a = in.next();
        while (!a.equals("$")) {
            b = in.next();
            n = a.length();
            pre = new ArrayList<String>();
            addPre(a); addPre(b);
            pn = pre.size();
            aug = new ArrayList<String>(pre);
            aug.add(C);  // for augmented matrix: C for Constant
            long[][] m = makeSys();
            System.out.println(solve1(m));
            a = in.next();
        }
        judgeWrapup(); // judge
    }
   
    static void addPre(String s) { // Add to pre all new prefixes of s
        for (int i=0; i<n; i++) {  //    empty string first in
            String p = s.substring(0, i);
            if (!pre.contains(p))
                pre.add(0, p);  // later insertions at the beginning
        }                       //     so empty string stays last
    }

    static void mInc(long[][] m, String p, String q, int val) { // increment m
        m[pre.indexOf(p)][aug.indexOf(q)] += val;   // in location for p and q
    }
    
    static long[][] makeSys() {
        long[][] m = new long[pn][pn+1];  // all 0 with ints
        for (String p: pre) {
            mInc(m, p, p, 2);
            for (char ch: HT) {
                String x = p+ch;
                if (x.equals(a))  // only constant side contribution
                    mInc(m, p, C, 1);
                else if (!x.equals(b)) 
                   mInc(m, p, longEndPre(x), -1);
            }
        }
        showMat(m); // judge
        return m;
     }

     static String longEndPre(String s) {
        //return longest p in pre which is a suffix in s.
        for (int i=0; i < s.length(); i++) 
            if (pre.contains(s.substring(i)))
                return s.substring(i);
        return "";
    }

    static long gcd(long a, long b) { //return greatest common divisor
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    
    static String solve1(long[][] m) { // solve for last variable
        for (int nzi=0, p=0; p < pn-1; p++) { //make upper triangular
            if (m[p][p] == 0) { // swap equations for nonzero pivot at nzi
                for (nzi = p+1; m[nzi][p] == 0; nzi++) 
                    ;
                long[] temp = m[nzi];
                m[nzi] = m[p];
                m[p] = temp;
            }
            for (int i = p + 1; i < pn; i++) { // zero out under pivot
                long fac = gcd(m[i][p], m[p][p]), // keep long size down
                     n1 = m[i][p]/fac, n2 = m[p][p]/fac;
                for (int j = p; j <= pn; j++) {
                    maxCalc = max(maxCalc, max(abs(m[i][j]*n2),   // judge
                                               abs(n1*m[p][j])));
                    m[i][j] = m[i][j]*n2 - n1*m[p][j];    // keep as integer!
                    maxCalc = max(maxCalc, abs(m[i][j]));         // judge
                }
            }
        }
        judgeCheck(m); //judge 
        long num =  m[pn-1][pn], denom = m[pn-1][pn-1], fac = gcd(num, denom); 
        num /=fac;  // lowest terms
        denom /= fac;        
        return num + "/" + denom;                // just solve for P(A | "")                          
    }
    
    ///////// Judge Testing Below ///////////////////////////////////
    static boolean debug= false; // if true show original system + row-reduced
    static long maxCalc = 0;    // will be largest integer used in calculation

    static void judgeCheck(long[][] m) { // judge
        show(String.format("n: %d, a: %s, b: %s", n , a, b));
        showMat(m);
    }

    static void judgeWrapup() { 
        show(String.format("maxCalc: %s", maxCalc));
    }

    static void sho(String s) {
        if (debug) System.err.print(s);
    }

    static void show(String s) {
        sho(s+'\n');
    }

    static void showMat(long[][] m)
    {
        int width = m[0].length;
        int[] colWidth = new int[width]; // use minimum widths
        for (long[] r: m)                // calc min widths
            for (int i=0; i < width; i++)
                colWidth[i] = max(colWidth[i], (""+r[i]).length());
        String[] fStr = new String[width];
        for (int i=0; i < width; i++) // Java separate step for variable width
            fStr[i] = " %" + colWidth[i] +"s";  //            in format string
        for (int j = 0; j < m.length; j++) {
            for (int i = 0; i < width; i++)
                sho(String.format(fStr[i], m[j][i]));
            show(" " + aug.get(j));
        }
    }
}

