// 2004 ACM Mid-Central Regional Programming Contest
// Problem A: Primary X-Subfactor Series
// by Dr. Eric Shade, Southwest Missouri State University


import java.io.*;


public class pxs {
    static final boolean VERBOSE = false;

    static PrintWriter out, stdout;

    static int best[] = new int[32];
    static int bestLen;

    static int series[] = new int [32];
    static int fac[] = new int [32];


    public static void main (String[] args)
        throws java.io.IOException
    {
        BufferedReader in = new BufferedReader (new FileReader ("pxs.in"));
        out = new PrintWriter (new FileWriter ("pxs.out"));
        stdout = new PrintWriter (System.out);

        for (;;) {
            String line = in.readLine ();
            if (line == null) break;
            int n = Integer.parseInt (line);
            if (n == 0) break;
            bestLen = 0;
            analyze (n, 1);
            printSeries (out, best, bestLen);
        }

        in.close ();
        out.close ();
    }


    static void analyze (int n, int seriesLen)
    {
        int digit[] = new int [9];
        int d = 0;
        int m = n;

        series[seriesLen - 1] = n;

        while (m >= 10) {
            digit[d++] = m % 10;
            m /= 10;
        }

        digit[d++] = m;

        for (int subset = (1 << d) - 2; subset > 0; subset--) {
            int j = 0;
            int s = subset;
            int pos = d - 1;

            /* find first digit */
            while ((s & 1) == 0) {
                s >>= 1;
                pos--;
            }

            /* if first digit is a 0, move on */
            if (digit[pos] == 0) continue;

            do {
                if ((s & 1) == 1) j = j*10 + digit[pos];
                s >>= 1;
                pos--;
            } while (s > 0);

            if (j > 1 && n % j == 0) {
                int k = 0;
                s = subset ^ ((1 << d) - 1);
                pos = d - 1;

                while (s > 0) {
                    if ((s & 1) == 1) k = k*10 + digit[pos];
                    s >>= 1;
                    pos--;
                }

                fac[seriesLen - 1] = j;

                analyze (k, seriesLen + 1);
            }
        }

        if (false) {
            // this was just to check my logic
            stdout.print (series[0]);
            for (int i = 1; i < seriesLen; i++)
                stdout.print (" = " + fac[i-1] + " * " + series[i-1]/fac[i-1]
                              + "; " + series[i]);
            stdout.println ();
        }

        if (VERBOSE)
            printSeries (stdout, series, seriesLen);

        if (betterSeries (seriesLen)) {
            for (int i = 0; i < seriesLen; i++) best[i] = series[i];
            bestLen = seriesLen;
            if (VERBOSE) stdout.println ("^--- BEST ---^");
        }

    }


    static boolean betterSeries (int seriesLen)
    {
        if (seriesLen > bestLen) return true;
        if (seriesLen < bestLen) return false;
        for (int i = 0; i < seriesLen; i++)
            if (series[i] < best[i])
                return true;
            else if (series[i] > best[i])
                return false;
        return false;
    }


    static void printSeries (PrintWriter out, int S[], int len)
    {
        out.print (S[0]);
        for (int i = 1; i < len; i++)
            out.print (" " + S[i]);
        out.println ();
    }
}
