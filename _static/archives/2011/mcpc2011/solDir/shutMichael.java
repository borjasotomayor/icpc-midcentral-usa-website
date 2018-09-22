/* Shut the Box, MCPC 2011 Problem F, Java solution by Michael Goldwasser */
import java.util.*;
import java.io.File;

class shutMichael {

    static final int MAX_N = 22;
    static final int MAX_VAL = 22;
    static int N = MAX_N;                 // actual N for current trial
    static ArrayList<ArrayList<Integer>> choices;  // choices.get(v) is set of possible turn codes for value v

    //-------------------- utilities for piece codes ------------------------
    // We use int as bitfield to code which pieces are unmarked, with bit for
    // 2^{p-1} being set if piece p is unmarked (i.e., least significant
    // bit is piece 1). We will also use these codes to describe subsets
    // of pieces that can be marked during a single turn.

    // Return true if piece p is unmarked
    public static boolean unmarked(int code, int p) {
        return (code & (1 << (p-1))) > 0;   // bit is set
    }

    // returns true if every piece of turn is unmarked
    public static boolean legal(int code, int turn) {
        return ((code & turn) == turn);  // i.e. turn is subset of code
    }

    // return new code with extra piece marked
    public static int markPiece(int code, int p) {   
        return code | (1 << (p-1));   // clear the bit
    }

    // return new code based on marking pegs from given turn (assuming legal)
    public static int reduce(int code, int turn) {   
        return (code ^ turn);    // exclusive or 
    }

    // Returns number of marked pegs
    public static int numMarked(int code) {
        int total = 0;
        for (int p=1; p <= N; p++)
            if (!unmarked(code,p))
                total++;
        return total;
    }

    // Returns sum of the values of those pieces marked in the code
    public static int pegSum(int code) {
        int total = 0;
        for (int p=1; p <= N; p++)
            if (unmarked(code,p))
                total += p;
        return total;
    }

    // this function only needs to be called once, to precompute list of choices
    public static void initChoices() {
        choices = new ArrayList<ArrayList<Integer>>();
        for (int j=0; j < 1+MAX_VAL; j++)
            choices.add(new ArrayList<Integer>());

        int half = MAX_VAL / 2;
        int halfcode = 1 << half;
        // to save time, we will only cycle through codes for bottom half of pegs.
        // can use at most one other peg.
        for (int code = 0; code < halfcode; code++) {
            int total = pegSum(code);
            if (total <= MAX_VAL) {
                choices.get(total).add(code);
                for (int p = half+1; p <= MAX_VAL; p++) {
                    if (total + p <= MAX_VAL)
                        choices.get(total+p).add(markPiece(code,p));
                    else
                        break;
                }
            }
        }
    }

    public static void main(String[] args) throws java.io.FileNotFoundException {
        Scanner in = new Scanner(new File("shut.in"));
        initChoices();

        int game = 1;
        while (true) {
            ArrayList<Integer> turns = new ArrayList<Integer>();
            N = in.nextInt();
            int T = in.nextInt();
            if (N == 0) break;

            for (int k=0; k < T; k++)
                turns.add(in.nextInt());

            Set<Integer> reachable = new HashSet<Integer>();
            int initial = (1 << N) - 1;   // all N pegs unmarked
            int mostMarked = 0;
            reachable.add(initial);

            for (int t=0; t < T; t++) {
                int turn = turns.get(t);
                Set<Integer> newReach = new HashSet<Integer>();

                for (int curCode : reachable) {
                    for (int k = 0; k < choices.get(turn).size(); k++) {
                        int subset = choices.get(turn).get(k);
                        if (legal(curCode,subset)) {
                            int newCode = reduce(curCode,subset);
                            newReach.add(newCode);
                            int score = numMarked(newCode);
                            if (score > mostMarked)
                                mostMarked = score;
                        }
                    }
                }
                reachable = newReach;
                if (reachable.size() == 0) break; // no reason to continue
            }

            System.out.println("Game " + game++ + ": " + mostMarked);
        }
    }
}
