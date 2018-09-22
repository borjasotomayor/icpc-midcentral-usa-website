/* hex.java MCPC 2008 Problem C by Andy Harrington
allow only 1 or 2 digit numbers, max 2 *'s so int calculations work
*/

import java.util.*;
import java.io.*;

public class hex
{
    static char[][] hex; // input pattern
    static char[] seq;  // current proposed soluton sequence
    static int nMax;    // total number of tiles
    // offsets to neighbors:
    static int[][] dc = { {1,  0, -1, -1, -1, 0},   // in even row
                          {1,  1,  0, -1,  0, 1} }; // in odd row
    static int[] dr =     {0, -1, -1,  0,  1, 1};   // in each row
    static char DONE = '\0';  // initial array value
    static int nSol;  //judge data
    
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "hex.in";
        Scanner in = new Scanner(new File(file));
        int r  = in.nextInt();
        while (r  > 0) {
            int c = in.nextInt();
            hex = new char[r+2][c+3]; // leave border; no boundary checks
            for (int i = 1; i <= r; i++) 
                for (int j = 1; j <= c + 1 - i % 2; j++)
                    hex[i][j] = in.next().charAt(0);
            nMax = r*c + r/2;
            seq = new char[nMax+1];  // convenient to append final '='
            seq[nMax] = '=';         //   to trigger second evaluation
            nSol = 0; // judge check
            for (int i = 1; i <= r; i++)
                for (int j = 1; j <= c + 1 - i % 2; j++) 
                    if (Character.isDigit(hex[i][j])) 
                        solve(i, j, 0, 0);
            if (nSol == 0) // judge check
                System.out.println("#### NO SOLUTION!");
            r = in.nextInt();
        }
    }

    // use hex[r][c] next if not DONE and number of digits legal.
    //   nDigits before; n is current length of seq without hex[r][c]
    static void solve(int r, int c, int n, int nDigits) {
        char lastCh = hex[r][c];
        nDigits = Character.isDigit(lastCh) ? nDigits+1 : 0;
        if (lastCh == DONE || nDigits > 2 ||   // 2 is max number of digits
                              nDigits == 2 && seq[n-1] == '0') // leading '0'
            return;
        seq[n] = lastCh;
        if (n < nMax - 1) { // add another character
            hex[r][c] = DONE;  // don't let path come back here
            for (int i = 0; i < 6; i++) //
                 solve(r + dr[i], c + dc[r%2][i], n+1, nDigits);
            hex[r][c] = lastCh; // revert to previous state for backtracking
        } 
        else if (nDigits > 0) // full seq; end must be digit
            checkEqual();  
     }
   
    static void checkEqual() { // have seq[0..nMax], seq[nMax] set to '='
        // assume seq: digit(s), op, digit(s), op, ... digit(s), second '='
        int leftExpr = 0;  // set after first '='
        int expr = 0;  // currently accumulating expression
        char lastOp = '+';  // adding to 0 is innocuous initialization
        int digits = 0;  // value of current digit sequence
        for (int i = 0; i <= nMax; i++) {
            char ch = seq[i];
            if (Character.isDigit(ch)) 
                digits = 10*digits + ch - '0';
            else {
                switch(lastOp) {  // evaluate so far
                    case '+': expr += digits; break;
                    case '-': expr -= digits; break;
                    case '*': expr *= digits; break;
                    case '/':  { // no division by 0, exact integer result
                        if (digits == 0 || expr % digits != 0)
                            return;
                        expr /= digits; break;
                    }
                }
                digits = 0;
                if (ch != '=')  // continue expression
                    lastOp = ch;
                else if (i < nMax) { // first =
                    leftExpr = expr;
                    lastOp = '+'; // start expr calc over
                    expr = 0;
                }
                else if (leftExpr == expr) { // second equal at end, and match
                    nSol++; // judge check for 3 lines
                    if (nSol > 1)
                        System.out.print("!!!  ");           
                    for (int j = 0; j < nMax; j++)
                        System.out.print(seq[j]);
                    System.out.println();
                }
            }
        }    
    }
}