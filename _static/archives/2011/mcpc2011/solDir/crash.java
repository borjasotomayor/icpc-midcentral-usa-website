/* Crash and Go(relians), MCPC 2011 Problem H, Java solution by Michael Goldwasser */
import java.io.*;
import java.util.*;

public class crash {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("crash.in"));
        while (true) {
            int n = in.nextInt();
            if (n == 0) break;
                        
            LinkedList<Group> groups = new LinkedList<Group>();
            for (int arrival=0; arrival < n; arrival++) {
                Group g = new Group();
                g.x = in.nextInt();
                g.y = in.nextInt();
                g.r = in.nextInt();

                while (true) {     // will repeat until we can no longer merge
                    int numGroups = groups.size();
                    LinkedList<Group> neighbors = new LinkedList<Group>();
                    for (int i=0; i < numGroups; i++) {
                        Group old = groups.removeFirst();
                        double distSq = (g.x-old.x)*(g.x-old.x) + (g.y-old.y)*(g.y-old.y);
                        double maxR = Math.max(g.r, old.r);
                                                
                        if (distSq <= maxR*maxR) {
                            neighbors.addLast(old);
                        } else {
                            groups.addLast(old);  
                        }
                    }
                                        
                    if (neighbors.isEmpty())
                        break;   // done merging

                    neighbors.add(g);
                    g = new Group(); // all zeros
                    for (Group temp : neighbors) {
                        g.x += temp.x;
                        g.y += temp.y;
                        g.r += (temp.r*temp.r);
                    }
                    g.x /= neighbors.size();
                    g.y /= neighbors.size();
                    g.r = Math.sqrt(g.r);
                                        
                    // time to repeat
                }

                groups.addLast(g);
            }
            System.out.println(groups.size());
        }
    }   // end of main
}  // end of class

class Group {
    public double x;
    public double y;
    public double r;
}