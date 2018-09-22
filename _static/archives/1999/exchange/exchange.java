// 1999 ACM Mid-Central Regional Programming Contest
// Problem C: Exchange Rates
// by Dr. Eric Shade, Computer Science Department, SMSU


// I used short everywhere to make sure there aren't problems
// for 16-bit machines.


import java.io.*;


public class exchange {    
    public static void main(String args[]) throws IOException {
        Trader T = new Trader(60);        
        ACMIO in = new ACMIO("exchange.in");
        PrintWriter out = new PrintWriter(new FileWriter("exchange.out"));

        for (char cmd = in.charRead(); cmd != '.'; cmd = in.charRead()) {
            if (cmd == '!') {
                short m = (short) in.intRead();
                String thing1 = in.stringRead();
                in.charRead();
                short n = (short) in.intRead();
                String thing2 = in.stringRead();

                T.assert(m, thing1, n, thing2);
            } else {
                String thing1 = in.stringRead();
                in.charRead();
                String thing2 = in.stringRead();

                T.query(thing1, thing2, out);
            }
        }

        out.close();
    }
}



class Trader {
    Tagger tagger;
    Node[] adjList;
    boolean[] visited;
            
        
    public Trader(int capacity) {
        tagger = new Tagger(capacity);
        adjList = new Node[capacity];
        visited = new boolean[capacity];
    }


    public void assert(short m, String thing1, short n, String thing2) {
        short tag1 = tagger.tag(thing1);
        short tag2 = tagger.tag(thing2);

        addEdge(tag1, new Ratio(n, m), tag2);
        addEdge(tag2, new Ratio(m, n), tag1);
    }


    public void query(String thing1, String thing2, PrintWriter out) {
        for (short i = 0; i < tagger.count; ++i)
            visited[i] = false;

        Ratio r = new Ratio((short) 1, (short) 1);
        
        if (dfs(tagger.tag(thing1), r, tagger.tag(thing2)))
            out.println(r.denom + " " + thing1 + " = " + r.numer + " " + thing2);
        else
            out.println("? " + thing1 + " = ? " + thing2);
    }
    

    private boolean dfs(short i, Ratio r, short j) {
        if (i == j)
            return true;
        else {
            visited[i] = true;

            for (Node p = adjList[i]; p != null; p = p.next) {
                if (! visited[p.target]) {
                    if (dfs(p.target, r.multiplyBy(p.rate), j))
                        return true;
                    else
                        r.divideBy(p.rate);
                }
            }

            return false;
        }
    }
                        
                    
    private void addEdge(short i, Ratio r, short j) {
        // no search needed because no redundant rules in input
        
        adjList[i] = new Node(r, j, adjList[i]);
    }
}



class Tagger {
    private String[] name;
    public short count;

    public Tagger(int capacity) {
        name = new String[capacity];
        count = 0;
    }

    public short tag(String s) {
        for (short i = 0; i < count; ++i)
            if (s.equals(name[i]))
                return i;

        name[count] = s;
        return count++;
    }
}


        
class Node {
    public Ratio rate;
    public short target;
    public Node next;

    public Node(Ratio rate, short target, Node next) {
        this.rate = rate;
        this.target = target;
        this.next = next;
    }
}



class Ratio {
    public short numer;
    public short denom;
    
    public Ratio(short n, short d) {
        numer = n;
        denom = d;
        reduce();
    }

    public Ratio divideBy(Ratio r) {
        numer *= r.denom;
        denom *= r.numer;
        reduce();
        return this;
    }
    
    public Ratio multiplyBy(Ratio r) {
        numer *= r.numer;
        denom *= r.denom;
        reduce();
        return this;
    }
    
    private void reduce() {
        short g = gcd(numer, denom);
        numer /= g;
        denom /= g;
    }

    private static short gcd(short a, short b) {
        return b == 0 ? a : gcd(b, (short) (a%b));
    }
}




