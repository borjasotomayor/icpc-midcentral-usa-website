//robot.java
import java.io.*;

class robot {
    public static  void main(String[] arg) {
        final int UNUSED = -1, BORDER = -2;
        String dir = "NSEW";
        int[][]  delta = {{0,-1},{0,1}, {1,0},{-1,0}};
        int[][] history = new int[12][12]; // where been, can go, with border
        String[] grid = new String[11];  //space for grid with border
        
        ACMIO in = new ACMIO("robot.in");
        PrintWriter out = null;
        try {
          out = new PrintWriter(
                  new BufferedWriter(
                    new FileWriter("robot.out"))); 
        }catch(Exception e) {
            System.out.println("can't open output");
        }
        int ny = in.intRead();
        while (ny > 0) {
          int nx = in.intRead(), xstart = in.intReadln();
          int x,y,steps;
          for (x = 1; x <= nx; x++){
            for (y = 1; y <= ny; y++)
              history[y][x] = UNUSED;
          }
          for (y = 1; y <= ny; y++){
            history[y][0] = BORDER;
            history[y][nx+1] = BORDER;
          }
          for (x = 1; x <= nx; x++){
            history[0][x] = BORDER;
            history[ny+1][x] = BORDER;
          }
          for (y = 1; y <= ny; y++) 
            grid[y] = " " + in.getLine();
          x = xstart;
          y = 1;
          steps = 0;
          while (history[y][x] == UNUSED) {
            history[y][x] = steps;
            steps++;
            int i = dir.indexOf(grid[y].charAt(x));
            x += delta[i][0];
            y += delta[i][1];
          }
          if (history[y][x] == BORDER) 
            out.println(steps + " step(s) to exit");
          else
            out.println(history[y][x] + " step(s) before a loop of " + 
              (steps - history[y][x])+ " step(s)");
          ny = in.intRead();
        }
        out.close();
    }
}
         
          
