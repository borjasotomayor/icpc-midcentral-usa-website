/*  Judges' Time Calculation, ACM MCPC 2010 Problem A, Java version */

import java.util.*;
import java.io.*;

public class calc {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("calc.in"));
        int N = in.nextInt();
        for (int trial=0; trial < N; trial++) {
            int SH = in.nextInt(), SM = in.nextInt(),
                DH = in.nextInt(), DM = in.nextInt();
            int duration = 60*DH + DM;
            int lasthour = SH + (SM + duration) / 60;
            System.out.println("------+---------");
            System.out.println(" time | elapsed");  // no space after elapsed
            System.out.println("------+---------");
            int offset = -SM;
            for (int hour = SH; hour <= lasthour; hour++, offset+=60) {
                int displayhour = (hour > 12) ? hour - 12 : hour;
                if (displayhour < 10)
                    System.out.print(" ");
                System.out.print(displayhour + ":XX | XX");
                if (offset < 0)
                    System.out.print(" - " + -offset);
                else if (offset > 0)
                    System.out.print(" + " + offset);
                System.out.println();
            }
        }
    }
}
