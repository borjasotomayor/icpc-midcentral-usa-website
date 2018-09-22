/*  ACM Mid-Central Programming competition 2013 Problem B: Round Robin
    solution by Andy Harrington

    If anyone has an excess of turns, the person with the latest turn 
    must be one of them, so if only one person has an extra turn, it is the 
    one quitting, and everyone else has the same number, so we track the
    number of players with one excess turn.  Of course if every player
    including the one about to quit has the same number of turns, we are also done.
    It is unnecessary to process a single turn at a time: use division and
    remainders.  Alternate, turn by turn processing is shown in slowSolve
    and the C++ solution.
*/

import java.util.*;
import java.io.*;

public class round
{
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "round.in";
        Scanner in = new Scanner(new File(file));
        int N = in.nextInt();
        while (N != 0) {
            solve(N, in.nextInt());
            N = in.nextInt();
        }
    }

    static void solve(int N, int T) {   
        judgeCheck(N, T); //judge testing
        int minTurns = 0, // minimum turns for all player in game
            excess = 0;   // number of players with one more than the min.
        while(true) {
            minTurns += (excess + T) / N;
            excess = (excess + T) % N; 
            N--;          // person leaving
            if (excess < 2) { //at most the person leaving has excess
                System.out.println(N + " " + minTurns);
                return;
            }
            excess--;  // person leaving among those with the excess turn
        }
    }
    
    // Done with quick solution: now an alternate to solve, one turn at a time
    static void slowSolve(int N, int T) {   
        int[] turns = new int[N];  // count individual turns for all
        int i = -1;  // initialize so next turn at i+1
        while(true) {
            for (int j = 0; j < T; j++) { // add turn for T players
                i = (i+1) % N ;
                turns[i]++;
            }
            // i in position of last turn
            for (int j = i; j < N-1; j++) // remove ith, shift
                turns[j] = turns[j+1];  
            N--; // no longer use all of the array turns
            if (allSame(turns, N)) {
                System.out.println(N + " " + turns[0]);
                return;
            }
            i--; // now next turn at i+1 again
        }
    }   

    static boolean allSame(int[] turns, int N) { // used by slowSolve
        // true if turns[0] == turns[1] ==...==turns[N-1]
        for (int i = 1; i < N; i++)
            if (turns[0] != turns[i])
                return false;
        return true;
    }  
   
    ////////// rest for judges' testing ///////////
    static int MAX_T = 100, MAX_N = 100; //  judge problem limits
    static void judgeCheck(int N, int T) {
        if (N > MAX_N || T > MAX_T)
            System.err.println("bad parameters");
    }
}