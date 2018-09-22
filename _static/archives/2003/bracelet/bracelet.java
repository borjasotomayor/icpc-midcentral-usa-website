// 2003 ACM Mid-Central Regional Programming Contest
// Problem C: Bracelet
// by Andy Harrington, Loyola University Chicago
/*
bracelet.java

Problem:
 Connect n octagons into a ring so the connecting edges are the same color
 and the sum of the brighness of the connection colors in minimal.

Algorithm:  Dynamic programming algorithm recursively generating data
 in an array of best sums for all end-colors pairs and for all combinations,
 not permutations, of pieces.

Time estimate:  The major contribution to the time is filling out the needed
 entries in the best array.  The best array has an entry for the minimum sum
 for each pair of ending colors for each subset of the pieces.
 There are NCOLORS^2 * 2^nPieces entries, and each
 entry involves a loop run through NCOLORS times to match the inner color
 when joining a sequence containing all but one element of the subset with
 the omitted member of the subset.
 The body of this innermost loop is run no more than
 NCOLORS^3 * nPieces * 2^nPieces times.

 With the current parameters that is 8^3*11*2^11, about 12 million.

 */

import java.io.*;
import java.util.*;

class bracelet {

  static final int NCOLORS = 8;
  static final int NSIDES = 8; // assume even
  static final int HALFSIDES = NSIDES/2;
  static final int MAX_PARTS = 11;
  static final int MAXBRIGHT = 255;
  static final int MAX_DATASETS = 25;
  static final int TOO_BRIGHT = MAXBRIGHT*MAX_PARTS*10;
  static final int UNKNOWN = -1;  // used in array best

  static int[] brightness; // brightness[color] is the brightness of color +'A'
  static int nPieces;  // number of pieces in the bracelet
  static int[] singleton; // each piece is represented by an int bit field
                          //   with a single bit set
  static int all;  // set of all the pieces (nPieces bits set in bit field)
  static int nSets; // number of subsets of the pieces = all + 1 = 2^nPieces
                    // each nonempty subset is a bit field with an integer
                    // value in the range 1 <= subset <= all
  static int[][][] best; // best[c1][c2][set] is the darkest connection sum
                         // using pieces in set, with colors c1 and c2 on ends
                         // unless it is so far UNKNOWN.

  public static int getBest(int c1, int c2, int set) {
    if (best[c1][c2][set] != UNKNOWN) // only calculate once
      return best[c1][c2][set];
    int darkest = TOO_BRIGHT;
    // use results for all but one of the pieces, and add the remaining piece
    //  on the end if the colors can match
    for (int piece = 0; piece < nPieces; piece++) {
      int bit = singleton[piece]; // element to remove
      if ((bit & set) != 0) {     // if it is in the set
        int rest = set & ~bit;    // set with the element removed
        for (int color = 0; color < NCOLORS; color++)
          darkest = Math.min(darkest, getBest(c1, color, rest) +
                             getBest(color, c2, bit) + brightness[color]);
      }
    }
    best[c1][c2][set] = best[c2][c1][set] = darkest; // next time only a lookup
    return darkest;
  }

  public static void main(String[] arg) {
    String FILE = "bracelet";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }
    brightness = new int[NCOLORS];
    nPieces = in.intRead();
    while ( nPieces != 0) { // one dataset per loop
      int piece, side, color, c1, c2, bit, set;
      for (color = 0; color < NCOLORS; color++)
        brightness[color] = in.intRead();
      int[][] colors = new int[nPieces][NSIDES];
      for (piece = 0; piece < nPieces; piece++)
        for (side = 0; side < NSIDES; side++)
          colors[piece][side] = in.charRead() - 'A';
      nSets = 1 << nPieces;
      all = nSets - 1;
      singleton = new int[nPieces];
      for (piece = 0; piece < nPieces; piece++)
        singleton[piece] = 1 << piece;
      best = new int[NCOLORS][NCOLORS][nSets];
      // all values in the array best are originally UNKNOWN
      for (set = 1; set < nSets; set++)
        for (c1 = 0; c1 < NCOLORS; c1++)
          for (c2 = 0; c2 < NCOLORS; c2++)
            best[c1][c2][set] = UNKNOWN;
      // induction basis gives possible color pairs for singletons
      for (piece = 0; piece < nPieces; piece++) {
        bit = singleton[piece];
        // First make all entries for singletons TOO_BRIGHT (never the darkest)
        for (c1 = 0; c1 < NCOLORS; c1++)
          for (c2 = 0; c2 < NCOLORS; c2++)
            best[c1][c2][bit] = TOO_BRIGHT;
        // then mark possible color pairs from opposite sides of single pieces
        // since no sides are joined, the value is 0.
        for (side = 0; side < HALFSIDES; side++) {
          c1 = colors[piece][side];
          c2 = colors[piece][side+HALFSIDES];
          best[c1][c2][bit] = best[c2][c1][bit] = 0;
        }
      }

      // Sequences using all pieces, and having the same color on each end,
      //   can have these ends connected to make a closed bracelet.
      //   Find the best color to connect.
      int darkest = TOO_BRIGHT;
      for (color = 0; color < NCOLORS; color++)
        darkest = Math.min(darkest, brightness[color] +
                                    getBest(color, color, all));

      if (darkest >= TOO_BRIGHT)
        out.println("impossible");
      else
        out.println(darkest);
      nPieces = in.intRead();
    } // end of dataset
    out.close();
  }
}
