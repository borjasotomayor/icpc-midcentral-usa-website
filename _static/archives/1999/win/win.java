// 1999 ACM Mid-Central Regional Programming Contest
// Problem A: Find the Winning Move
// by Dr. Eric Shade, Computer Science Department, SMSU


import java.io.*;


public class win {
    static final char SPACE = '.';
    
    static char[] board = new char[16];


    public static void main(String args[]) throws IOException {
        ACMIO in = new ACMIO("win.in");
        PrintWriter out = new PrintWriter(new FileWriter("win.out"));

        for (char cmd = in.charRead(); cmd == '?'; cmd = in.charRead()) {
            for (int i = 0; i < 16; ++i) {
                board[i] = in.charRead();
            }

            // show();

            int k = findForcedWin();

            if (k >= 0)
                out.println("(" + k/4 + "," + k%4 + ")");
            else
                out.println("#####");
        }

        out.close();
    }


    public static int findForcedWin() {
        for (int i = 0; i < 16; ++i)
            if (board[i] == SPACE && xForcedWinPlaying(i))
                return i;

        return -1;                
    }

    
    public static boolean xForcedWinPlaying(int i) {
        board[i] = 'x';
        
        if (! isWin(i, 'x')) {
            for (int j = 0; j < 16; ++j) {
                if (board[j] == SPACE && ! oForcedLossPlaying(j)) {
                    board[i] = SPACE;
                    return false;
                }
            }
        }

        board[i] = SPACE;
        return true;
    }

    
    public static boolean oForcedLossPlaying(int j) {
        board[j] = 'o';

        if (! isWin(j, 'o')) {
            for (int i = 0; i < 16; ++i) {
                if (board[i] == SPACE && xForcedWinPlaying(i)) {
                    board[j] = SPACE;
                    return true;
                }
            }
        }

        board[j] = SPACE;
        return false;
    }


    public static boolean isWin(int i, char player) {
        int r = 4 * (i / 4);
        int c = i % 4;
        
        return (board[r    ] == player && board[r + 1] == player &&
                board[r + 2] == player && board[r + 3] == player)
            || (board[c    ] == player && board[c + 4] == player &&
                board[c + 8] == player && board[c +12] == player)
            || (board[ 0] == player && board[ 5] == player &&
                board[10] == player && board[15] == player)
            || (board[ 3] == player && board[ 6] == player &&
                board[ 9] == player && board[12] == player);
    }

    
    public static void show() {
        for (int i = 0; i < 16; ++i) {
            System.out.print(board[i]);
            if (i%4 == 3) System.out.println();
        }
    }
}

