/*  ACM Mid-Central Programming competition 2013 Problem D
    solution by Andy Harrington
    
This is a Finite State Machine problem, where transitions between states
are determined by the next toss, H or T, each occuring with 1/2 probability.

Let a and b be the first and second patterns given.
Let pre be a list of all prefixes of a or b, including "" (empty string).

For each p in pre there is a state where p is longest element of pre which is 
a suffix in the sequence of tosses so far.   There are also terminal states for 
a and b.  The initial state is when p = "".  I refer to the intermediate states
just by this longest p.

The successor state of p is one where the sequence of tosses ends with suffix p 
followed by T or H, which I will write pH and pT.   

Let longEndPre(s) be the longest suffix in s that is also an element of pre. 
Then pH and pT could be a or b (terminal states), or the successor states for 
pH and pT are intermediate states longEndPre(pH) and longEndPre(pT)

Let A be the event that a appears first in the tosses.
P(A | p) = .5 P(A | pH) + .5 P(A | pT) 
or with integer coefficients
2 P(A | p) = P(A | pH) + P(A | pT)
where
P(A | a) = 1, P(A | b) = 0
This generates a linear system of the probabilities for the different states 
in pre.  The terminal states give values for the constant terms.

Note that each equation has at most 3 nonzero coeficients or constants, 
2, 1, or -1, and in most cases one reference is to a very short prefix,
so most variables have only 2 references.
This sparseness allows solution for P(A | "") by simple determinant quotient, 
all in integer arithmetic, with on the order of 2^(2n) steps, which is
under a million with the constraints on n.

The final answer merely needs to be reduced to lowest terms using gcd. 

Alternately linear system row reduction could be used, but with 
long arithmetic, not double. (See paradoxGauss.java)  The C++ version
uses similar equations, but solves by variable substitution, using
rational arithmetic.

Simple Monte Carlo testing can check that the answer is approximately correct,
but the standard deviation of the Monte Carlo sample is proportional to 
(repetitions)^(-1/2),
and the difference betwee adjacent fractions is of the order of 1/denom^2,
so to be close to dependably accurate as a replacemnt to direct solution, 
the number of repetitions would need to be O(denom^4) and there will be
denominators on the order of 2^n. Also it is likely to take a string
of tosses of length about 2^n to reach one of the given strings.  
With n=9, this means on the order of 2^45 steps to get close: 
not feasible.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class paradox
{
    static char[] HT = {'H', 'T'};
    static String a, b,            // first and second given strings
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
            b= in.next();
            n = a.length();
            pre = new ArrayList<String>();
            addPre(a);  addPre(b);
            pn = pre.size();
            aug = new ArrayList<String>(pre);
            aug.add(C);  // augmented to label constant term.
            int[][] m = makeSys();  // augmented matrix from linear system
            int denom = det(m);
            m[0] = m[pn];    // to solve for first variable, replace variable
            int num = det(m);//     coefficients with constants, take det
            judgeCheck(num, denom); // judge
            num = abs(num);  // probability positive
            denom = abs(denom);
            int f = gcd(num, denom);
            num /= f;
            denom /= f;
            System.out.println(num + "/"  + denom);
            a = in.next();
        }
        judgeWrapup();  // judge
    }

    static void addPre(String s) { // Add to pre all new prefixes of s
        for (int i=0; i<n; i++) {  //   empty string *first* in this version
            String p = s.substring(0, i);
            if (!pre.contains(p))
                pre.add(p);
        }
    }

    static void mInc(int[][] m, String q, String p, int val) { // increment m
        m[aug.indexOf(q)][pre.indexOf(p)] += val; // in location for p and q
    }
    
    static int[][] makeSys() { //create the linear system, including constants
        int[][] m = new int[pn+1][pn];  // all 0, transposed from usual way
        for (String p: pre) {
            mInc(m, p, p, 2);
            for (char ch: HT) {
                String x = p+ch;
                if (x.equals(a)) 
                    mInc(m, C, p, 1);
                else if (!x.equals(b)) 
                    mInc(m, longEndPre(x), p, -1);
            }
        }
        showMat(m); // judge display
        return m;
     }

     static String longEndPre(String s) {
        //return longest p in pre which is a suffix in s.
        for (int i=0; i < s.length(); i++) 
            if (pre.contains(s.substring(i)))
                return s.substring(i);
        return "";
    }

    static int det(int[][] m) {
        //return determinant of square first part of augmented matrix m
        int size = m[0].length;  // assume #rows >= row size
        int[] perm = new int[size];  
        for (int i=0; i<size; i++)   // initial identity permutaion
            perm[i] = i;
        return detRec(m, size, perm);                             
    }
    
    static int detRec(int[][] m, int k, int[] perm) {
   //return the determinant of matrix m[i][perm[j]] for i, j < k
        if (k == 1)
            return m[0][perm[0]];
        int tot = 0;
        int[] vlast = m[k-1];
        int plast = perm[k-1];
        for (int j=0; j < k-1; j++) { // swap last perm index
            int ptemp = perm[j];
            if (vlast[ptemp] != 0) {  // ? check with this speedup and below omitted
                perm[j] = plast;
                tot -= vlast[ptemp]*detRec(m, k-1, perm); // odd inversions: -
                maxCalc = max(maxCalc, abs(tot));
                perm[j] = ptemp; //undo change made for recursive call
            }
        }
        if (vlast[plast] != 0)   // leave last in place, no inversions: +
            tot += vlast[plast]*detRec(m, k-1, perm);
        maxCalc = max(maxCalc, abs(tot));
        return tot;
    }

    static int gcd(int x, int y) {
        if (y == 0) return x;
        return gcd(y, x % y);
    }

    ///////// Judge Testing Below ///////////////////////////////////
    static int MAXLEN = 9, MAXSETS = 20, totSets = 0;
    static boolean debug= false,  //with true: lots of output to System.err
                   doMonte = false; //with true: time-consuming Monte Carlo
    static int monteCarloRep = 10000; // not very accurate, but not too int
    static Random rand = new Random();
    static int maxCalc = 0;

    static void judgeCheck(int num, long denom) { 
        totSets++;
        if (totSets > MAXSETS)
            System.err.println("Too many datasets");
        if (n > 9)
            System.err.println("string too long");
        if (n != b.length())
            System.err.println("string lengths do not match");
        if (a.equals(b))
            System.err.println("strings same!");
        show(String.format("n: %d, a: %s, b: %s", n , a, b));
        if (doMonte || debug) 
            System.err.format("num %s denom %s%n", num, denom);
        if(doMonte) {  
            int tot = 0;
            for (int i=0; i<monteCarloRep; i++) 
                if (winner()) 
                    tot += 1;
            double diff = (double)tot/monteCarloRep - (double)num/denom;
            System.err.format("MonteCarlo diff  %s%n", diff);
        }
    }

    static void judgeWrapup() {
        show("MaxCalc: " + maxCalc); 
    }  

    static boolean winner() {
        int count = 0;
        String tail = "";
        for (int i=0; i<n; i++)
            tail += ((1 == rand.nextInt(2)) ? 'H' : 'T');
        while(true) {
            if (tail.equals(a)) return true;
            if (tail.equals(b)) return false; 
            tail = tail.substring(1) + (1 == rand.nextInt(2) ? 'H' : 'T');
            count++;
        }
    }

    static void sho(String s) {
        if (debug) System.err.print(s);
    }

    static void show(String s) {
        sho(s+'\n');
    }

    static void showMat(int[][] m)
    {
        int j = 0;
        for (int[] r:m) {
            for (int i: r)
                sho(String.format("%2d ", i));
            show(aug.get(j));
            j++;
        }
    }

}