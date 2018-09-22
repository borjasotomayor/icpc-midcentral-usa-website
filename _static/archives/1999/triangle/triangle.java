// triangle.java
import java.io.*;

class triangle {
  static final int MAXV = 15;
  static int[][][] a2 = new int[MAXV][MAXV][MAXV]; // area*2
  static int nv, x[] = new int[MAXV], y[] = new int[MAXV];
  
  static boolean isOK(int i, int j, int k) {
      // triangle with vertices with indices given is
      // OK if no other vertex for any index n is inside --
      //   With areas already calculated this is easiest to test
      //   by checking if the sum of the three triangles formed
      //   with vertex n replacing one of i,j,k = original area
      for (int n = 0; n <nv; n++)
          if (n != i && n != j && n != k && a2[i][j][k] == 
                a2[n][j][k] + a2[i][n][k] + a2[i][j][n])
            return false;
      return true;
  }
  
  public static void main(String[] args) {
    ACMIO in = new ACMIO("triangle.in");
    PrintWriter out = null;
    try {
        out = new PrintWriter(
                new BufferedWriter(
                  new FileWriter("triangle.out"))); 
    }catch(Exception e) {
        System.out.println("can't open output");
    }
    int i, j, k;
    nv = in.intRead();
    while (nv > 0) {
      for (i = 0; i < nv; i++) {
        in.charRead();
        x[i] = in.intRead();
        y[i]= in.intRead();
      }
      for (i = 0; i < nv; i++)
        for (j = 0; j < nv; j++)
          for (k = 0; k < nv; k++)
             a2[i][j][k] =   // twice the area considered consistently
               Math.abs((y[k]-y[i])*(x[j]-x[i]) - ( y[j]-y[i])*( x[k]-x[i]));
      String bestStr ="";
      int big = 0, nbig = 1; // confirm example has only one max!
      for (i = 0; i < nv-2; i++)
        for (j = i+1; j < nv-1; j++)
          for (k = j+1; k < nv; k++)
            if (a2[i][j][k] >= big && isOK(i,j,k)) {
                if (a2[i][j][k] == big) 
                    nbig++;
                else
                    nbig = 1;
                big = a2[i][j][k];
                bestStr = "" + (char)('A'+i) + (char)('A'+j) + (char)('A'+k);
            }
      out.println(bestStr);
      if (nbig >1) out.println(nbig + " copies!");  //check judge input for uniqueness! 
      System.out.println(bestStr);
      nv = in.intRead();
    }
    out.close();
  }
}
