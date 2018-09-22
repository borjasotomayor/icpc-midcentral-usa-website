// Shut the Box, MCPC 2011 Problem F Alternate Java version by Andy Harrington

/* Store sets of numbers shut as bitfields, n -> bit 1 << (n-1)
   Such bitfield sets are use both for possible summands in a roll
   and also for a set of all numbers shut through some turn.
   For each possible roll, precalculate the set of bitfields for all
   sum combinations.
   
   Dynamic programming solution.
   Store all shut combinations through r rolls in a HashSet of bitfields
   Then accumulate all shut combinations through roll r+1 by
   considering all sum sets from roll r+1 with all combinations through roll r,
   keeping the union if the intersection is empty (not shut same number twice).
   Keep track of the maximum number of bits set.
   Stop early if nothing is added due to the latest roll.
*/   

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class shutAndy {
    static final int NMAX = 22, VMAX = 22, SET_LIM = 1 << NMAX; 
    static int N, T;  //highest number, max turns
    static int[] turns;
    static int[] bitCount = new int[SET_LIM]; //precalc size of bitfield sets
    //choices.get(v) is list of all bitfield sets for roll of v, allowing NMAX 
    static List<List<Integer>> choices = new ArrayList<List<Integer>>();
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("shut.in"));
        preCalc();
        N = in.nextInt();
        int dataSetN = 1;
        while (N > 0) {
            T = in.nextInt();
            turns = new int[T];
            for (int i = 0; i < T; i++)  
                turns[i] = in.nextInt();
            System.out.format("Game %d: %d%n", dataSetN, solve());
            dataSetN++;
            N = in.nextInt();
        }
    }

    /** 
    return best count
    */
    static int solve() {
        int unusedBits = ~((1 << N) - 1); //bits for (illegal) numbers above N
        HashSet<Integer> r = new HashSet<Integer>(), rLast;
        r.add(0);        
        int mostThroughLast = 0; // most shut through last completed roll 
//        int hSize=0;  // judge statistic
        for (int roll: turns) {
            rLast = r;
            r = new HashSet<Integer>();
            int mostThisRoll = 0; // most shut so far *using* this roll
            for (int s: choices.get(roll)) {
                if (0 != (s & unusedBits)) continue; //not use bit > N
                for (int t : rLast) 
                    if ((s&t) == 0 && !r.contains(s+t)) {
                        r.add(s+t);
                        mostThisRoll = max(mostThisRoll, bitCount[s+t]);
                    }
            }
            if (mostThisRoll == N) return N;// won
            if (mostThisRoll == 0) return mostThroughLast; // lost
            mostThroughLast = max(mostThisRoll, mostThroughLast);
//            hSize = max(hSize, r.size());  // judge statistic
        }
//        pr("Hashset size: " + hSize);
        return mostThroughLast;
    }
    
    /**  calc bitCount, choices */
    static void preCalc() {
        for (int i = 0; i < SET_LIM; i++) { // calc bitCount
            int tot = 0, n = i;
            while (n != 0) {
               if ((n & 1) != 0)
                   tot++;
               n >>= 1;
            }
            bitCount[i] = tot;
        }
        ArrayList<Integer> noRoll = new ArrayList<Integer>();
        noRoll.add(0);
        choices.add(noRoll); //initial entry for 0 roll, just 0 
        for (int roll = 1; roll <= VMAX; roll++) { // find sums for each roll
            Set<Integer> set = new HashSet<Integer>(); // init for current roll
            for (int mark = 1; mark <= roll; mark++) { // consider a mark   
                int bit = 1 << (mark-1);            // stored in bit field,and
                for (int s : choices.get(roll-mark)) {// a choice for roll-mark
                     if ((s & bit) == 0)      // if mark not used in the choice
                         set.add(s|bit);      // include it choice set for roll
                }
            }
            choices.add(new ArrayList<Integer>(set));//list iteration efficient
        }
    }
   
    // only judge checks follow ////////////////////////////////

    static void shut(int t) {//expand bitfield to string
        int n = 1;
        while (t != 0) {
            if ((t&1) == 1)
              prp(" " + n);
            n++;
            t >>= 1;
        }
        pr("");
    }
    
    static boolean DEBUG = true;
    
    static void prp(String msg) {
        if (DEBUG) System.err.print(msg);
    }
    
    static void pr(String msg) {
        prp(msg+"\n");
    }
}
