// Sokoban, MCPC 2011 Problem G
//   Alternate Java version by Andy Harrington

import java.util.*;
import java.io.*;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toUpperCase;
import static java.lang.Character.toLowerCase;

public class sokoban {
    static char[][] board; //replaces ./+ with s/S, so uniform case relation
    
    static void place(int r, int c, char ch) { // ch to r,c with right case
        board[r][c] = (isUpperCase(board[r][c]) ? toUpperCase(ch) :
                                                  toLowerCase(ch));
    }

    /**  move char at r0, c0 to r, c, leave 's' behind + all corrected case */
    static void fromTo(int r0, int c0, int r, int c) { //assumes legal
        char fromCh = board[r0][c0], toCh = board[r][c];
        if (toUpperCase(toCh) != 'S') pr("bad move!");
        place(r0, c0, 's');
        place(r, c, fromCh);
    }

    static boolean solved() {
        for (char[] row : board)
            for (char ch : row)
                if (ch =='W' || ch == 'S')
                    return false;
        return true;
    }
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("sokoban.in"));
        int R = in.nextInt(), ds = 1, //data set
            wr=-1, wc=-1; // worker row, col
        int[] dc = {0, 0, -1, 1}; // indexed against UDLR
        int[] dr = {-1, 1, 0, 0};  // lower index up screen
        while (R > 0) {
            int C = in.nextInt();
            board = new char[R][];
            for (int r = 0; r < R; r++) { 
                board[r] =  // use s/S for space so all on target uppercase
                   in.next().replace('.', 's').replace('+', 'S').toCharArray();
                for (int c = 0; c < C; c++)
                    if (toUpperCase(board[r][c]) == 'W') {
                        wr = r; wc = c;
                    }
            }
            String keys = in.next();
//            prp(String.format("Init:%n%s", bStr()));
            for(int i = 0; i < keys.length(); i++) {
                int ki = "UDLR".indexOf(keys.charAt(i)),
                    nextR = wr + dr[ki], nextC = wc + dc[ki];
                char nextCh = board[nextR][nextC];
                if (nextCh == '#') continue;
                if (toUpperCase(nextCh) == 'S') { 
                    fromTo(wr, wc, nextR, nextC);
                    wr = nextR; wc = nextC;
                }
                else if (toUpperCase(nextCh) == 'B') {
                    int step2R = nextR + dr[ki], step2C = nextC + dc[ki];
                    if (toUpperCase(board[step2R][step2C]) == 'S') {
                        fromTo(nextR, nextC, step2R, step2C);
                        fromTo(wr, wc, nextR, nextC);
                        wr = nextR; wc = nextC;
                    }
                }
//                pr(String.format("move: %c%n%s", keys.charAt(i), bStr()));
                if (solved())
                    break;
            }
            String result = (solved() ? "complete" : "incomplete");
            System.out.format("Game %d: %s%n%s", ds, result, bStr());
            ds++;
            R = in.nextInt();
        }
    }

    static String bStr() {
        String s = "", nline = String.format("%n");
        for (char[] row : board)  // back to './+'
            s += (new String(row)).replace('s', '.').replace('S', '+') + nline;
        return s;
    }

    // only judge checks follow

    static boolean DEBUG = true;
    
    static void prp(String msg) {
        if (DEBUG) System.err.print(msg);
    }
    
    static void pr(String msg) {
        prp(msg+"\n");
    }
}
