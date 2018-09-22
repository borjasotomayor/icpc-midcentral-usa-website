/* Refrigerator Magnets, MCPC 2011 Problem E, Java solution by Michael Goldwasser */
import java.io.*;
import java.util.*;

public class magnets {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("magnets.in"));

        while (true) {
            String phrase = in.nextLine();
            if (phrase.equals("END")) break;

            boolean legitimate = true;
            boolean[] used = new boolean[26];  // false by default

            for (char c : phrase.toCharArray()) {
                if (c != ' ')
                    if (used[c-'A']) {
                        legitimate = false;
                        break;
                    } else
                        used[c-'A'] = true;
            }
                        
            if (legitimate)
                System.out.println(phrase);
        }
    }
}
