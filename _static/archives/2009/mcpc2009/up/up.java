/* Up and Down, MCPC 2009 Problem A by Andy Harrington
input:

Each dataset:
W S P    Winning space number, max size of a move, number of pairs. 
pairs    fromSpot toSpot ... 

2 < W <= 1000000000; 1 < S <= 6; 0 < P <= 40
Pairs have goFrom numbers in increasing order.  
All numbers in the pairs are postive and less than W.
No number in the sequence of pairs is repeated in any way.  
Movement starts from spot 0.  A turn moves 1 to S forward,but if the ending
spot is a goFrom, the turn actually ends up on the corresponding getTo.  
The objective is to get to spot W.  Find the minimum number of turns. 

Extra fancy algorith given the large bound for W:

Add 0->0, W->W to pairs, then any game can be split into groups, from one
getTo to a getTo or goFrom (and immediately to a getTo)
without any *intermediate* turns to goFroms.

Analyse such a group:
The choice of step size if no chutes or ladders is used can be greedy.
With the limited number of pairs, most steps in a big board will be totally
between successive goFrom's (given to you in order), and the number of
those turns can be calculated directly with modular arithmentic.
Then there may be a step over intermediate getFrom's, but the total number
of getFrom's is limited, so this is doable to check, with a greedy calculation
of the longest single jump possible over 1 or more goFroms.

In the end the total number of turns for a group can be calculated in O(P),
and all in O(P^3) (or less with a fancier algorithm, but this is good enough
with the limit given on P).

Using groups, the only locations that need to be nodes for the minimum distance
calculation are getTo's, P+2 of them.  The earier part of the algorithm
is already O(P^3), so O(P^3) Warshall is quick enough, though the quicker,
but longer to code, Dijiksta could be used.

A a check on the algorithm, the judge's solution also tests cases with modest
W by the more straightforward approach with each space as a node and starting
with possible single turns.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class up
{
    static int MAX_P = 40,  // check against final problem statement!
               W, //winning space
               S, // max step size;
               P; // number of pairs for linked spaces
    static int[] goFrom = new int[MAX_P + 2], getTo = new int[MAX_P + 2];
    
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "up.in";
        Scanner in = new Scanner(new File(file));
        String title = "";
        if (args.length > 0 && args[0].endsWith("v.in")) //judge notes only
            title = in.nextLine();  // descriptive version only
        W  = in.nextInt();
        while (W  > 0) {
            S = in.nextInt();
            P = in.nextInt();
            if (args.length > 0 && args[0].endsWith("v.in")) //judge notes only
                System.out.println(title);
            for (int i = 1; i <= P; i++) { // at index 0 remains 0
                goFrom[i] = in.nextInt();
                getTo[i] = in.nextInt();
            }
            if (args.length > 0) judgeCheckData();  // judge integrity check
            goFrom[P+1] = getTo[P+1] = W;
            int[][] D = new int[P+2][P+2];  //D = dist from getTo[i] to getTo[j]
            for (int i = 0; i < P+2; i++)   //     with no link but maybe j
                for (int j = 0; j < P+2; j++) 
                    D[i][j] = min(noJumpTurns(getTo[i], goFrom[j]), // with link
                                  noJumpTurns(getTo[i], getTo[j])); // direct      
            int ans = warshall(D);
            System.out.println(ans);
            if (args.length > 0 && W <= 520) judgeCheckAns(ans); //judge 2nd ans
            if (args.length > 0 && args[0].endsWith("v.in")) { // judge notes
                in.nextLine();
                title = in.nextLine();  // descriptive version only
            }
            W = in.nextInt();
        }
    }

    static int warshall(int[][] D) {                                 
        int size = D.length;
        for (int k = 0; k < size; k++)
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    D[i][j] = min(D[i][j], D[i][k] + D[k][j]);              
        return D[0][size-1];
    }    
    
    // Return the minimum number of turns to go from at to end without landing
    //   on any intermediate goFrom spaces, or return W if there is no route.
    static int noJumpTurns(int at, int end) {
        if (at > end || at == W) return W;// forward without jumps, not back
        int i = nextJumpLoc(at, 1);  // index of next fromSpot after at
        int turns = 0;
        while (end > goFrom[i]) { // while have to skip goFroms to get to end
            // invariant:  next goFrom after at is goFrom[i]
            int bigTurns = (goFrom[i] - 1 - at) / S;
            turns += bigTurns; //greedily add full steps:  needed for big W!
            at += bigTurns*S;  
            if (end - at <= S) break;  // one step at most left
            // next goFrom within reach.  Take max step missing goFroms.
            int nextI = nextJumpLoc(at + S - 1, i); // furthest possible next i
            for (i = nextI; i>0 && goFrom[i]-1 == goFrom[i-1]; i--) 
                ;  // work backwards to find nonempty interval before goFrom[i]
            int atNext = min(goFrom[i] - 1, at + S); //go max dist into interval
            if (at == atNext) return W;  // no way to advance
            at = atNext;
            turns++;
        }  
        // at most one more step, or no bad spots to end; direct calc of turns
        return turns + (end + S - 1 - at)/S;
    }
    
    static int nextJumpLoc(int n, int i) { // n < W 
       while (n >= goFrom[i]) 
           i++;
       return i;
    }

// only judge's tests follow:  

    static void judgeCheckData() {
        // check against final problem statement  (also MAX_P at top)!
        int MIN_W = 3, MAX_W = 1000000000,
            MIN_S = 2, MAX_S = 6,
            MIN_P = 1;
        if (W > MAX_W || W < MIN_W || S > MAX_S || S < MIN_S ||
            P > MAX_P || P < MIN_P) System.err.println("Bad param!");
        Set<Integer> s = new HashSet<Integer>();
        for (int i = 1; i <= P; i++) {
            s.add(goFrom[i]);
            s.add(getTo[i]);
            if (goFrom[i] <= goFrom[i-1])
                    System.err.println("Bad order!");
            if (getTo[i] < 1 || getTo[i] >= W)
                    System.err.println("getTo out of range");
        }
        if (s.size() < P*2) System.err.println("repeat in pairs\n" + s);
    }

    static void judgeCheckAns(int ans) {
        // compare straightforward 1-step initalization algorithm
        int[][] D = new int[W+1][W+1];
        for (int i = 0; i <= W; i++)   // ignore chutes and ladders
            for (int j = 0; j <= W; j++)  //can go to start or end of pair
                D[i][j] = (i < j && j <= i + S) ?  1 : W;
        for (int i = 1; i <= P; i++) { // can't jump from goFrom
            int badStart = goFrom[i];
            for(int j = badStart + 1; j <= min(badStart+S, W); j++)                                        
                D[badStart][j] = W;
        }
        for (int i = 1; i <= P; i++)  // put in chutes, ladders
            D[goFrom[i]][getTo[i]] = 0;
        int ans2 = warshall(D);
        if (ans != ans2) System.err.format("Bad! %s != %s%n", ans, ans2); 
    }
}
