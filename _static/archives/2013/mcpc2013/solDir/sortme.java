/*  ACM Mid-Central Programming competition 2013 Problem H: Sort Me
    solution by Andy Harrington

    Sort strings given a collating sequence.  One approach would be to 
    make a specialized comparator for the new sequence. (See the C++ solution.)  
    Instead I transform the strings at the beginning and end, and use a standard
    sort in the middle.
*/

import java.util.*;
import java.io.*;

public class sortme
{
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "sortme.in";
        Scanner in = new Scanner(new File(file));
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int nList = 1;
        int nWords = in.nextInt();
        while (nWords != 0) {
            String s = in.next();
            String[] words = new String[nWords];
            for (int i = 0; i < nWords; i++) 
                words[i] = in.next();
            judgeCheck(words, s);  // just for judges
            transform(s, alpha, words);
            Arrays.sort(words);
            transform(alpha, s, words);
            System.out.println("year " + nList);
            for (String w: words) 
                System.out.println(w);
            nList++;
            nWords = in.nextInt();
        }
    }
   
    static void transform(String inSeq, String outSeq, String[] words) {
        // For each string in words, replace the character in inSeq 
        //   by the corresponding character in outSeq.
        for (int i=0; i < words.length; i++) {
            String newWord = "";
            for (int j=0; j < words[i].length(); j++)
                newWord += outSeq.charAt(inSeq.indexOf(words[i].charAt(j)));
            words[i] = newWord;
        }  
    } 

    ////////// rest for judges' testing ////////////////////////////////
    static int MAXWORDS = 20,  //  problem limits 
               MAXCHAR = 30;  

    static void judgeCheck(String[] words, String s) {
        if (words.length > MAXWORDS)
            System.err.println("Too many words");
        for (String w: words) {
            for (char ch: w.toCharArray())
                if (ch < 'A' || ch > 'Z')
                   System.err.format("%s  contains non-letter%n", w);
            if(w.length() > MAXCHAR)
                System.err.format("%s too long%n", w);
        }
        if (s.length() != 26)
            System.err.println("invalid permutaion length");
        for (char ch = 'A'; ch <= 'Z'; ch++)
            if (s.indexOf(ch) < 0)
                System.err.format("permutaion missing %s", ch);
    }    
}