// Mirror, Mirror on the Wall, MCPC 2010 Problem E
//   Java version by Andy Harrington

import java.util.*;
import java.io.*;

public class mirror {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("mirror.in"));
        String line = in.nextLine();
        while (!line.equals("#")) {
            System.out.println(getMirror(line));
            line = in.nextLine();
        }
    }

    public static String getMirror(String s) {
        String m  = "bdpqiovwx",
               mb = "dbqpiovwx",
               sMirror = "";
        for (int i=0; i < s.length(); i++) {
            int j = m.indexOf(s.charAt(i));
            if (j == -1)
                return "INVALID";
            sMirror = mb.charAt(j) + sMirror;
        }
        return sMirror;
    }
}
