/* 
    cubes.java
    Letter Cubes, 2013 Mid-Central Programming Competition, Problem E
    Java Solution by Andy Harrington

    Basic recursive search.  Checking along the way and backtracking is
    essential since with 4 dice there are 
       C(23, 5)*C(17, 5)*C(11, 5) = 96197645544 possible die combinations

    The basic constraint is that any two letters in the same word
    cannot be on the same die.  This is recorded in 2D array: sep.

    Initially all the letters from the dice and the possible extra are
    gathered in order into the string: used.

    The recursion makeDice systematically tries to add the next letter to
    the current die, or if a non-terminal die is full,
    remove all those letters from the stock of remaining letters to try in
    the later dice.

    By always trying letters closest to the beginning of the alphabet first,
    there is no need to sort the data in a solution, when all are placed.
*/

import java.util.*;
import java.io.*;

public class cubes
{
    static char FIRST = 'A', LAST = 'Z';  //char range
    static int SIDES= 6,    // sides on die
               nDice,       // nCh/SIDES
               nCh;         // number of symbols used
    static char unused;     // one  die character not in any word, or '-'.
    static String[] words;  // given strings from input
    static int nWords;      // number of words
    static String used;     // all char use, in sorted order, must fill dice
    static boolean[][] sep; // sep[ch1][ch2] is true if ch1, ch2 in same word
    static char[] sol;      // start of potential solution, SIDES per die

    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "cubes.in";
        Scanner in = new Scanner(new File(file));
        nWords = in.nextInt();
        while (nWords != 0) {
            unused = in.next().charAt(0);
            words = new String[nWords];
            for (int i = 0; i < nWords; i++) 
                words[i] = in.next();
            solve();
            nWords = in.nextInt();
        }
    }
   
    static void solve() {
        sep = new boolean[LAST+1][LAST+1]; // pairs forced on separate dice
        nDice = words[0].length();
        for (int i = 0; i < nWords; i++) {                // for each word
            for (int j = 0; j < nDice-1; j++) {           //    mark each 
                char ch1 = words[i].charAt(j);            //    char pair
                for (int k = j+1; k < nDice; k++) {       //    as being
                    char ch2 = words[i].charAt(k);        //    on separate 
                    sep[ch1][ch2] = sep[ch2][ch1] = true; //    dice
                }
            }
        }
        used = "";  // accumulate used characters in alphabetical order
        for (char ch1 = FIRST; ch1 <= LAST; ch1++)
            for (char ch2 = FIRST; ch2 <= LAST; ch2++)
                if (ch1 == unused || sep[ch1][ch2]) {
                    used += ch1;
                    break;
                }
        nCh = used.length();
        judgeCheck();  // judge consistency check
        sol = new char[nCh]; 
        makeDice(null, 0, 0); // recursively build up sol
        judgeWrapup(); // judge check
     }


    static void makeDice(char[] toTry, int iSol, int iTry)  {
        // toTry is ordered array of char left (not in completed dice).
        // If iSol == nCh, note solution, done.
        // IF iSol % SIDES == 0, set up next die search, set lowest char.
        // Otherwise try adding char toTry[iTry] to current die.  if it works,
        //    recurse and try further addition.
        // Also continue seaching for current die location,
        //    if there are enough characters left.
        if (debug) showParam(toTry, iSol, iTry); // judge check
        if (nSol == MAXSOL) return; 
        if (iSol == nCh) {                  // have solution
            String s = new String(sol);     // insert blanks in sol
            for (int i = SIDES; i < nCh; i+= SIDES)
                System.out.print(s.substring(i-SIDES, i) + " ");
            System.out.println(s.substring(nCh-SIDES));
            nSol++;  // judge check
            return;
        }
        int solOffs = iSol % SIDES; // how far into current die
        if (solOffs == 0) { // start new die
            char[] newTry;
            if (iSol == 0)
                newTry = used.toCharArray();
            else {  // have a die done, another die to do
                newTry = new char[toTry.length - SIDES]; //extract latest die
                int sOffs = iSol - SIDES, iOffs = 0;
                for (char c: toTry) 
                    if (c == sol[sOffs]) 
                        sOffs++;
                    else 
                        newTry[iOffs++] = c;
            }
            sol[iSol] = newTry[0]; // must start new die with least char
            makeDice(newTry, iSol+1, 1);
            sol[iSol] = (char)0; // undo completed change for later tests
            return;
        }
        // case where a second or later ch added to a die
        if (fits(iSol, toTry[iTry])) { // try to use current char
            sol[iSol] = toTry[iTry];
            makeDice(toTry, iSol+1, iTry+1);
            sol[iSol] = (char)0; // undo completed change for later tests
        }
        iTry++;
        if (iTry + SIDES - solOffs <= toTry.length) // skip current char, go 
            makeDice(toTry, iSol, iTry);            //     on, if enough left
    }

    static boolean fits(int iSol, char ch) {
        // true if ch compatible with previous characters on the latest die
        for (int i = iSol - iSol % SIDES; i < iSol; i++)
            if (sep[sol[i]][ch])
                return false;
        return true;
    }

    ////////// rest for judges' testing ///////////
    static boolean debug = false; 
    static int MAXSOL = 2,  // require unique, check if more
               nSol,        // number of solutions found - shound end as 1
               dataSetsAllowed = 20; //  problem limit 
 
    static void judgeCheck() {
        nSol = 0;
        dataSetsAllowed--;
        if (dataSetsAllowed < 0)
                System.err.println("!!! Extra dataset");
        for (String w: words) {
            if (w.indexOf(unused) >= 0)
                System.err.format("%s  contains 'unused' %s%n", w, unused);
            if(w.length() != nDice)
                System.err.format("%s wrong length%n", w);
        }
        shln("used: " + used);
        int errDiff = SIDES*nDice - nCh;
        if(errDiff != 0)
            System.err.format("number of letters off: %d%n", errDiff);
    }
    
    static void judgeWrapup() {
        if (nSol != 1)  
            System.err.format("!!! %d solutions", nSol);
    }

    static void showParam(char[] toTry, int iSol, int iTry) {
        if (toTry != null)
            shln(String.format("try: %s; iSol: %d; iTry: %d%nsol: %s%n", 
                                new String(toTry), iSol, iTry, 
                                new String(sol).replace((char)0, '-')));
    }

    static void shln(String s) {
        show(s+'\n');
    }

    static void show(String s)
    {
        if (debug)
            System.err.print(s);
    }
}