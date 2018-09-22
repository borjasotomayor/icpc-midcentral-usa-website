/* Pizza Pricing, MCPC 2011 Problem C, C++ solution by John Cigas */
import java.util.*;
import java.io.*;

public class pizza {
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("pizza.in"));
        int menu = 1;
        while (true) {
            int items = in.nextInt();
            if (items == 0) break; // all done
            int bestinches = 0;
            int bestprice = Integer.MAX_VALUE;
            double bestvalue = Double.MAX_VALUE;
            for (int i=0; i<items; i++) {
                int testinches = in.nextInt();
                int testprice = in.nextInt();
                double testvalue = testprice / (testinches/2.0*testinches/2.0*Math.PI);
                // System.err.println(testvalue);
                if (testvalue < bestvalue) 
                {
                    bestvalue = testvalue;
                    bestinches = testinches;
                }
            }
            System.out.println("Menu " + menu++ + ": " + bestinches);

        }
    }
}
