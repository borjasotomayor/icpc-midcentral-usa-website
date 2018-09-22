// LeapingLizardsGen.java

import java.text.*;

public class LeapingLizardsGenJC {
    private static int NUM_CASES = 100;
    private static int MAX_ROWS = 20;
    private static int MAX_COLS = 20;

    public static void main(String[] args) throws Exception {
	System.out.println(NUM_CASES);
	for (int i = 0; i < NUM_CASES; ++i) {
	    int rows = random(MAX_ROWS - 1) + 1;
	    int cols = random(MAX_COLS - 1) + 1;
	    System.out.println(rows + " " + (random(4)+1));
	    char pillarMap[][] = new char[rows][cols];
	    for (int j = 0; j < rows; ++j) {
		for (int k = 0; k < cols; ++k) {
                    //pillarMap[j][k] = (char)(skewedRandom(80)+'0');
                    pillarMap[j][k] = (char)(random(4)+'0');
		    System.out.print(pillarMap[j][k]);
		}
		System.out.println();
	    }
	    for (int j = 0; j < rows; ++j) {
		for (int k = 0; k < cols; ++k) {
                    if (pillarMap[j][k] == '0') 
		       System.out.print(".");
                    else
		       System.out.print("L.".charAt(random(2)));
		}
		System.out.println();
	    }
	}
    }

    private static int random(int n) { 
        return (int) Math.floor(Math.random() * n);
    }

    // return value in [0-9] one in n times, otherwise return value in [0-2]
    private static int skewedRandom(int n) {
        int y;
        y = random(n);
        if (y > 0) return random(3);
        else return random(10);
    }
   
}
