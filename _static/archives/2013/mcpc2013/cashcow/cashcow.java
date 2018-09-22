/* 
    cashcow.java
    Cash Cow, 2013 Mid-Central Programming Competition, Problem G
    Java Solution by Andy Harrington

    For the collapse, lists are more convenient than arrays.
    I convert the coordinate to usual list indices starting at 0.
    The region clicked on could be marked by changing the character,
    but that requires an extra step if the region is too small.
    I use the alternative of remembering the SET of points in the region.
    They need to be remembered and sorted, and that is not built in for
    ordered pairs in java, so when remembering the region,
    I linearize to integers, like with memory in a 2D array.
    Higher points have larger codes, so it is important to
    remove points in reverse order to keep later lower indices unchanged.
*/

import java.util.*;
import java.io.*;

public class cashcow
{
    static int WIDE = 10, HIGH = 12;
    static ArrayList<ArrayList<Character>> grid;
    static HashSet<Integer> codes;

    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "cashcow.in";
        Scanner in = new Scanner(new File(file));
        int N = in.nextInt();
        while (N != 0) { 
            grid = new ArrayList<ArrayList<Character>>();
            for (int x = 0; x < WIDE; x++)
                grid.add(new ArrayList<Character>());
            for (int y = 0; y < HIGH; y++) {
                String row = in.next();
                for (int x = 0; x < WIDE; x++)
                    grid.get(x).add(0, row.charAt(x));
            }
            for (int i = 0; i < N; i++) 
                click(in.next().charAt(0) - 'a', in.nextInt()-1); // x, y
            int tot = 0;
            for (List<Character> col: grid)
                tot += col.size();
            System.out.println(tot);
            N = in.nextInt();
        }
    }

    static void click(int x0, int y0) { // click at x0,y0; reduce grid
        char c = getCh(x0, y0);
        if (c == ' ') return;            // nothing there
        codes = new HashSet<Integer>();  // remember region, coded as int's
        expand(x0, y0, c);               // find whole region; list in codes
        if (codes.size() < 3) return;
        Integer[] ca = codes.toArray(new Integer[0]); // need array for 
        Arrays.sort(ca);                                  //    built-in sort
        for (int i = ca.length-1; i >= 0; i--)   // reversed so higher out 1st
            grid.get(ca[i] % WIDE).remove(ca[i] / WIDE); // collapse columns
        for (int x = grid.size()-1; x >= 0; x--) // reversed so higher out 1st
            if (grid.get(x).size() == 0)         // collapse rows
                grid.remove(x);        
    }

    static char getCh(int x, int y) {
        if (x < 0 || x >= grid.size() || y < 0 || y >= grid.get(x).size())
            return ' ';
        return grid.get(x).get(y);
    }

    static void expand(int x, int y, char c) {//if c at (x,y): include, spread
        int code = WIDE*y + x;  // standard linearization of 2D
        if (getCh(x, y) != c || codes.contains(code)) return;
        codes.add(code);
        expand(x+1, y, c);
        expand(x-1, y, c);
        expand(x, y+1, c);
        expand(x, y-1, c);
    }
}