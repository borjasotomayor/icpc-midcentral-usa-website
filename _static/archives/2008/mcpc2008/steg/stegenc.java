// 2008 ACM Mid-Central USA Regional Programming Contest
// Test Generator for Problem H: "Steganography" [easy/moderate]
// Eric Shade, Missouri State University

// NOTE: This program uses Deque, so it requires Java 1.6.  However,
// it's not a solution to the problem, it's used to generate test
// data.  All Java *solutions* for this year's contest are compatible
// with Java 1.5.


import java.util.*;

class stegenc {
    static int WIDTH;    // max line width, not including newline

    static Deque<Integer> bits;
    static Deque<String> words;
    static ArrayList<String> line = new ArrayList<String>();
    static ArrayList<Integer> spaces = new ArrayList<Integer>();

    static Random rand = new Random();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Syntax: java stegenc width \"message\".");
            System.err.println("Reads text from standard input and writes to standard output.");
            System.exit(1);
        }

        WIDTH = Integer.parseInt(args[0]);
        getBits(args[1].toUpperCase());

        Scanner in = new Scanner(System.in);
        words = new ArrayDeque<String>();

        while (in.hasNext()) {
            String w = in.next();
            if (w.length() > 0) words.offerLast(w);
        }

        in.close();

        while (! bits.isEmpty()) {
            // create a line, then print it out
            line.clear(); spaces.clear();
            String w = nextWord();
            int n = w.length();
            line.add(w); spaces.add(0);
            while (! bits.isEmpty() && n < WIDTH) {
                int b = bits.pollFirst();
                w = nextWord();
                if (n + w.length() + b + 1 <= WIDTH) {
                    spaces.add(b + 1);
                    line.add(w);
                    n += w.length() + b + 1;
                } else {
                    bits.offerFirst(b);
                    words.offerFirst(w);
                    break;
                }
            }
            justifyLine(WIDTH - n);
            for (int i = 0; i < line.size(); i++) {
                int s = spaces.get(i);
                for (int j = 0; j < s; j++) System.out.print(" ");
                System.out.print(line.get(i));
            }
            System.out.println();
        }

        System.out.println("*");
    }


    static void justifyLine(int s) {
        while (s > 1) {
            // add two spaces to zero bits
            for (int i = 1; i < spaces.size() && s > 1; i++) {
                if ((spaces.get(i) & 1) != 0) {
                    spaces.set(i, spaces.get(i) + 2);
                    s -= 2;
                }
            }
            // add two spaces to one bits
            for (int i = 1; i < spaces.size() && s > 1; i++) {
                if ((spaces.get(i) & 1) == 0) {
                    spaces.set(i, spaces.get(i) + 2);
                    s -= 2;
                }
            }
        }

        while (s == 1) {
            // this will fail if all words have length one!!
            int i = rand.nextInt(line.size());
            String w = line.get(i);
            if (w.length() == 1) continue;
            int k = rand.nextInt(w.length() - 1) + 1;
            line.set(i, w.substring(0, k) + "_" + w.substring(k));
            s = 0;
        }
    }


    static String nextWord() {
        if (words.isEmpty()) {
            System.err.println("Not enough words to encode the message!");
            System.exit(1);
        }

        return words.pollFirst();
    }


    static void getBits(String msg) {
        bits = new ArrayDeque<Integer>();

        for (int i = 0; i < msg.length(); i++) {
            int code = charCode(msg.charAt(i));

            if (code < 0) {
                System.err.printf("Invalid character '%c'.\n", msg.charAt(i));
                System.exit(1);
            }

            bits.offerLast(code >> 4);
            bits.offerLast((code >> 3) & 1);
            bits.offerLast((code >> 2) & 1);
            bits.offerLast((code >> 1) & 1);
            bits.offerLast(code & 1);
        }

        while (bits.peekLast() == 0) bits.pollLast();
    }


    static int charCode(char c) {
        switch (c) {
        case ' ' : return  0;
        case '\'': return 27;
        case ',' : return 28;
        case '-' : return 29;
        case '.' : return 30;
        case '?' : return 31;
        }

        return 'A' <= c && c <= 'Z' ? c - 'A' + 1 : -1;
    }
}