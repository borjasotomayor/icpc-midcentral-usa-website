/* Top This, Java version by Andy Harrington
Store grid (< 64 locations) as a bit field,
with [0][0] location corresponding to the 
most significant bit used,
allowing shifting, checking for intersection,
adding, and testing order all as single 
binary operations with long operands.
. 
Recursively add shapes, disjoint from what is before.
After all in the first group added,
invert the grid, and the remaining group, following
the same rules as the first three, being disjoint from 
what is set so far.

After the first group of shapes is put together,
the potential final accepted shape is known, and
the result from the first group can be compared to
the best so far, to see if it is worth continuing.
*/
 
import java.util.*;
import java.io.*;

public class top
{
    static int L = 6,  // output is LxL L < 8
               GROUP_MAX = 3,
               groupSize;  //number of pieces in a group
    // pAll[index in all 7 pieces][rotation] = L x L bitfield 
    static long[][] pAll = new long[7][4];
    static int[] pieceIndex = new int[GROUP_MAX*2]; // pieces to use
    static long best, B = 1L << (L*L-1); // bit for [r][c] = [0][0]

    static long shift(long a, int r, int c) {
        return a >> (L*r + c);
    }
    static boolean get(long a, int r, int c) {// get bit [r][c]
        return (a & shift(B, r, c)) > 0;
    }

    static long set(long a, int r, int c) {// set bit [r][c]
        return a | shift(B, r, c);
    }

    //assumes a shifted to upper left; return rotated, shifted up, left
    static long rot90Shift(long a) {
        long b=0;  
        int rMax = 0;
        for (int r = 0; r < L; r++)
            for (int c = 0; c < L; c++)
                if (get(a, r, c))
                    rMax = r;
        for (int r = 0; r <= rMax; r++)
            for (int c = 0; c < L; c++) 
                if (get(a, r, c))
                    b = set(b, c, rMax-r);
        return b;
    }
                             
    static void print(long a) {
        for (int r = 0; r < L; r++) {
            for (int c = 0; c < L; c++) 
                System.out.print(get(a, r, c) ? '#' : '.');
            System.out.println();
        }
    }

    static void findSol(long a, int next, long shape){
        if (next == 2*groupSize) {
            if (shape > best)
                best = shape;
            return;
        }
        if (next == groupSize) {
            if (a <= best)
                return;  // save lots of time to test if best here!
            shape = a;
            a = (B << 1) - 1 - a;  //invert all bits
        }
        int pNum = pieceIndex[next];
        int rows = 2, cols = 3;
        if (pNum == 0) {
            rows = 1;
            cols = 4;
        }
        for (int rot = 0; rot < 4; rot++) {
            for (int dr = 0; dr <= L-rows; dr++)
                for (int dc = 0; dc <= L-cols; dc++) { 
                    long shifted = shift(pAll[pNum][rot], dr, dc);
                    if ((a & shifted)== 0) // if disjoint
                        findSol(a | shifted, next+1, shape); //solve wih union
                }
            int temp = rows;
            rows = cols;
            cols = temp;
        }
    }
        
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "top.in";
        Scanner in = new Scanner(new File(file));
        String[][] ps = {{"####"}, //quick coding - next convert to array
                         {"###",
                          "#"},
                         {"###",
                          ".#"},
                         {"###",
                          "..#"},
                         {"##",
                          ".##"},
                         {"##",
                          "##"},
                         {".##",
                          "##"}};
        for (int i=0; i < ps.length; i++) { // set up all 4x4 boolean arrays
            for (int r = 0; r < ps[i].length; r++)  //translate from String representation
                for (int c = 0; c < ps[i][r].length(); c++)  
                    if (ps[i][r].charAt(c) == '#')
                        pAll[i][0] = set(pAll[i][0], r, c);
            for (int rot=0; rot<3; rot++)  // record rotations, shift up left
                pAll[i][rot+1] = rot90Shift(pAll[i][rot]);
        }
        int dataSets = in.nextInt();
        for (int ds = 1; ds <= dataSets; ds++) {
            System.out.println(ds);
            groupSize = 3;             // hardwired for this problem
            for (int i=0; i<2; i++) {
                String pieces = in.next();
                for (int j=0; j < groupSize; j++)
                    pieceIndex[i*groupSize + j] = pieces.charAt(j) - 'A';
            }
            best = 0;
            findSol(0, 0, 0);
            if (best == 0) // array never replaced
                System.out.println("No solution");
            else
                print(best);
        }
    }
}
