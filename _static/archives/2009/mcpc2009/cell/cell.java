/* Cell Towers, MCPC 2009 Problem H, alt solution by Andy Harrington
*/

import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class cell
{
    static int MAX_T = 10, MAX_R = 10, // check against final problem statement!
               R, T;
    static double[] tx = new double[MAX_T],
                    ty = new double[MAX_T],
                    p = new double[MAX_T],
                    rx = new double[MAX_R + 1],
                    ry = new double[MAX_R + 1];
                    
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "cell.in";
        Scanner in = new Scanner(new File(file));
        T  = in.nextInt();
        while (T  > 0) {
            R = in.nextInt();
            for (int i = 0; i < T; i++){  
                tx[i] = in.nextInt();
                ty[i] = in.nextInt();
                p[i] = in.nextInt();
            }
            for (int i = 0; i <= R; i++){  
                rx[i] = in.nextInt();
                ry[i] = in.nextInt();
            }
            if (args.length > 0) judgeCheckData();  // judge integrity check
            char tl, tlOld = getTower(rx[0], ry[0]);
            print("", 0, tlOld);
            double segDist = 1;
            int mk = 1;
            for (int i = 0; i < R; i++) {
                double dist = d(rx[i+1], ry[i+1], rx[i], ry[i]),
                       dx = (rx[i+1] - rx[i])/dist,
                       dy = (ry[i+1] - ry[i])/dist;
                while (segDist < dist) {
                    tl = getTower(rx[i] + segDist*dx, ry[i] + segDist*dy);
                    if (tl != tlOld) {
                        print(" ", mk, tl);
                        tlOld = tl;
                    }
                    mk++;
                    segDist++;
                }
                segDist -= dist;
            }
            tl = getTower(rx[R], ry[R]);
            if (tl != tlOld && abs(segDist - .5) < .001) // judge check
                System.err.println("Bad seg length round: " + segDist);
            if (segDist < .5  && tl != tlOld) // >= .5 to end of last segment
                print(" ", mk, tl);
            System.out.println();
            if (args.length > 0) {  // judge check on endpoint
                for (int i = 0; i < T; i++)
                    System.err.print(" " + i + ": " + powers[i]);
                System.err.println("\n tot: " + (mk-segDist));
            }
            T = in.nextInt();
        }
    }

    static void print(String sp, int mk, char ch) {
        System.out.format("%s(%s,%s)", sp, mk, ch);
    }

    static double d(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1, dy = y2 - y1;
        return sqrt(dx*dx + dy*dy);
    }

    static double[] powers = new double[MAX_T];  // for judge checks
    
    static char getTower(double x, double y) {
        double bestPower= -1;
        double bestBadRound = -1;
        int iBest = -1;
        for (int i = 0; i < T; i++) {
            double dist = d(tx[i], ty[i], x, y);
            if (dist < .01)  //judge
                System.err.println("Tower on marker " + x + " " + y);
            double powerFrac = p[i]/(dist*dist);
            double power = round(powerFrac);
            if (abs(powerFrac - power) > .499)  // judge
                bestBadRound = max(powerFrac, bestBadRound);
            powers[i] = power;  // for judge
            if (power > bestPower) {
                iBest = i;
                bestPower = power;
            }
        }
        if (bestBadRound + .6 > bestPower)  // for judge
            System.err.println("Close round: " + bestBadRound);
        return (char)('A' + iBest);
    }

// only judge's tests follow:
//  See judge's notes for pictures, indicating no self intersections.

    static void judgeCheckData() {
        // check against final problem statement  (also bounds at top)!
        int MIN_P = 1, MAX_P = 1000000, MIN_C = 0, MAX_C = 100;
        for (int i = 0; i < T; i++) {
            if (p[i] < MIN_P || p[i] > MAX_P)
                System.err.println("power out of range " + p[i]);
            for (int j = 0; j < T; j++) 
                if (i != j && tx[i] == tx[j] && ty[i] == ty[j])
                    System.err.println("Dup coord in towers");
        }
        for (int i = 0; i <= R; i++) {
            if (rx[i] < MIN_C || rx[i] > MAX_C)
                System.err.println("coord out of range " + rx[i]);
            if (ry[i] < MIN_C || ry[i] > MAX_C)
                System.err.println("coord out of range " + ry[i]);
            for (int j = 0; j <= R; j++) 
                if (i != j && rx[i] == rx[j] && ry[i] == ry[j])
                    System.err.println("Dup coord ");
            for (int j = 0; j < T; j++) 
                if (rx[i] == tx[j] && ry[i] == ty[j])
                    System.err.println("Dup coord with tower");
        }      
    }
}
