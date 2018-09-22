/* Up and Down, ACM MCPC 2009 Problem D by Andy Harrington
input:

Each dataset seq of tokens:
t                         // t number of turns
XXXXX YYYYY ZZZZ ???      // hands for players 1-3, hidden cards, using A-R
p XXX n  // t times       // turns by player 1-3 in rotation t times, where you
...                       //    interogate player p (1-3) with cards XXX, ans n

0                         // after all datasets

possible algorithm

bestSolved = MAX_TURNS
for each player:
    combine two other hands + gang
    solved = 0
    for each combination of the 2 hands and the gang:
        if the chosen gang is not the real gang:
            for each turn:
                if impossible interogation
                    solved = max(solved, turn)
                    break
            if no interrogation impossible:
                solved = total turns + 1
    if solved < bestSolved:
        bestSolved = solved
if bestSolved == total turns + 1
   print "?"
else
   print bestSolved
   
For the set combinations:  All player know their own cards but initially the
others could anywhere.  Recursively add and remove each remaining letter
to each set if the set is not full.  At the point where all sets are full,
do the checks for impossible interrogations.

Brute force is sufficient given the number of parameters:  Players
only needs to figure out based on the 13 cards not in their hand.
There are just 72072 combinations for the two hands of 5 and 3 in gang.
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class vienna
{
    static int PLAYERS = 3,  // check against final problem statement!
               MAX_TURNS = 15, HAND = 5, HID = 3, UNK = HID+HAND*(PLAYERS-1),
               turns;
    static char[][] quest = new char[MAX_TURNS][], // interrogations
                    hand = new char[PLAYERS + 1][], //actual hands, gang at end
                    maybe = new char[PLAYERS + 1][HAND]; //possible hands, gang
    static char[] unk = new char[UNK]; // letters not in one player's hand
    static int[] who = new int[MAX_TURNS],  // who interrogated
                 matches = new int[MAX_TURNS], // matches in interogation
                 used = new int[PLAYERS+1]; // amount of maybe hand filled
    static int solved;  // max turns needed for current player for comb. so far
    
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "vienna.in";
        Scanner in = new Scanner(new File(file));
        String title = "";  // for verbose judge dataset
        if (args.length > 0 && args[0].endsWith("v.in")) //judge notes only
            title = in.nextLine();  // descriptive version only
        turns  = in.nextInt();
        maybe[PLAYERS] = new char[HID]; // replace so right length
        while (turns > 0) {
            for(int i = 0; i <= PLAYERS; i++) 
                hand[i] = in.next().toCharArray();
            for(int t = 0; t < turns; t++) {
                who[t] = in.nextInt() - 1;  // internal 0 based
                quest[t] = in.next().toCharArray();
                matches[t] = in.nextInt();
            }
            if (args.length > 0 && args[0].endsWith("v.in")) //judge notes only
                System.out.println(title);
            if (args.length > 0) judgeCheckData();  // judge integrity check
            int bestSolved = MAX_TURNS;
            for (int p = 0; p < PLAYERS; p++) {
                String unkStr = "";
                for (int j = 0; j <= PLAYERS; j++)
                    if (j != p)
                        unkStr += new String(hand[j]);
                maybe[p] = hand[p]; // player knows own
                used[p] = HAND;     // no further characters to choose
                solved = 0; // after recursion max turns to eliminate a maybe
                solve(0, unkStr.toCharArray());  
                maybe[p] = new char[HAND];  // undo use of 
                used[p] = 0;                //     actual hand
                if (solved < bestSolved) 
                    bestSolved = solved;
            }
            if (bestSolved < turns)
                System.out.println(bestSolved+1);  // 1-based count          
            else
                System.out.println("?");
            if (args.length > 0 && args[0].endsWith("v.in")) { // judge notes
                in.nextLine();
                title = in.nextLine();  // descriptive version only
            }
            turns = in.nextInt();
        }
    }

    // accumulate combinations recursively, check when one is complete
    static void solve(int i, char[] unk) {
        if (i == unk.length) { // assigned all choices, now check
            if (maybe[PLAYERS][0] == unk[i-HID]) return; //match end gang char
            solved = max(solved, countConsistent(maybe));
        }
        else 
            for (int j = 0; j <= PLAYERS; j++)   
                if (used[j] < maybe[j].length) { //add char to partial hand
                    maybe[j][used[j]] = unk[i];
                    used[j]++;
                    solve(i+1, unk);
                    used[j]--;      // undo change before trying next player
                }
    }

    // return first inconsistent turn (0 based) or total turns if consistent
    static int countConsistent(char[][] choice) {
        int t = 0;
        while (t < turns && 
               countDups(choice[who[t]], quest[t]) == matches[t])
             t++;
        return t;
    }
    
    static int countDups(char[] a1, char[] a2) {
        int n = 0;
        for (char ch1 : a1)
            for (char ch2 : a2)  // slow but quick to write!
                if (ch1 == ch2) 
                    n++;
        return n;
    }
    
    /////// only judge's tests follow:  ////////////////////////////
    static void judgeCheckData() {
        // check constants against final problem statement!
        int TOT_CHAR = HID + HAND*PLAYERS;
        char PAST = (char)('A' + TOT_CHAR);
        int MIN_TURNS = 2, Q_LENGTH = 3; 
        if (turns < MIN_TURNS) System.err.println("Bad turns param!");
        Set<Character> s = new HashSet<Character>();
        for (int p = 0; p <= PLAYERS; p++) {
            int n = (p < PLAYERS) ? HAND : HID;
            if (hand[p].length != n)
                System.err.println("Bad token length " + p );
            for (int i = 1; i < n; i++)
                if (hand[p][i-1] >= hand[p][i])
                    System.err.println("not increasing " + p );
            for (char ch: hand[p])
                s.add(ch);
        }
        if (s.size() != TOT_CHAR)    
             System.err.println("repetition!");
        for (Character ch : s)
            if (ch < 'A' || ch >= PAST)
                System.err.println("char out of range");
        int tot = countConsistent(hand);
        if (tot != turns)  
            System.err.println("question answer wrong " + tot);
        for (int t = 0; t < turns; t++) {
            if ( who[t] == t % PLAYERS || who[t] < 0 || who[t] >= PLAYERS)
                System.err.println("who questioned is wrong " + t);
            if ( quest[t].length != Q_LENGTH)
                System.err.println("question length wrong " + t);
            for (char ch : quest[t])
                if ( 'A' > ch || ch >= PAST)
                    System.err.println("question char out of range " + t);
            for (int i = 1; i < Q_LENGTH; i++)
                if ( quest[t][i-1] >= quest[t][i])
                    System.err.println("question char out of order " + t);
        }
    }
}
