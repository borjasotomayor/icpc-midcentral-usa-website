//autoedit.java
import java.io.*;

class autoedit {
    public static  void main(String[] arg) {
        String[] find = new String[10];
        String[] replace = new String[10];
        ACMIO in = new ACMIO("autoedit.in");
        PrintWriter out = null;
        try {
          out = new PrintWriter(
                  new BufferedWriter(
                    new FileWriter("autoedit.out"))); 
        }catch(Exception e) {
            System.out.println("can't open output");
        }
        int nrep = in.intReadln();
        while (nrep > 0) {
          for (int i = 0; i < nrep; i++) {
            find[i] = in.getLine();
            replace[i] = in.getLine();
          }
          String line = in.getLine();
          for (int i = 0; i < nrep; i++) { 
            int pos = line.indexOf(find[i]);
            while (pos >= 0) {
              line = line.substring(0,pos) + replace[i] + 
                     line.substring(pos + find[i].length());  
              pos = line.indexOf(find[i]);
            }
          }
          out.println(line);
          nrep = in.intReadln();
        }
        out.close();
    }
}
