// Voting, MCPC 2010 Problem C
//   Andy Harrington

import java.util.*;
import java.io.*;

public class voting {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("voting.in"));
        String s = in.nextLine();
        while (!s.equals("#")) {
            if (s.length() < 2 || s.length()>70)  //judge check 
                 System.out.println("LONG");
            int y = 0, n = 0, p = 0, a = 0;
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (ch == 'Y')
                    y++;
                else if (ch == 'N')
                    n++;
                else if (ch == 'P')
                    p++;
                else if (ch == 'A')
                    a++;
                else 
                    System.out.println("ERROR"); // judge check
            }
            if (a >= y+n+p)
                System.out.println("need quorum"); 
            else if (y > n)
                System.out.println("yes"); 
            else if (y < n)
                System.out.println("no"); 
            else
                System.out.println("tie"); 
            s = in.nextLine();
        }
    }
}
