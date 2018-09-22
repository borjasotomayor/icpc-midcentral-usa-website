// multiplyMichael.java
// (More) Multiplication, MCPC 2014, Problem A
// Java solution by Michael Goldwasser

import java.io.File;
import java.util.Scanner;

public class multiplyMichael {

  static final char[][] grid = {
    {'+','-','-','-','+'},
    {'|',' ',' ','/','|'},
    {'|',' ','/',' ','|'},
    {'|','/',' ',' ','|'},
    {'+','-','-','-','+'}
  };

  public static void main(String[] args) throws Exception {
    String file = (args.length > 0) ? args[0] : "multiply.in";
    Scanner in = new Scanner(new File(file));

    String A,B;
    while (true) {
      A = in.next();
      B = in.next();
      
      int a = Integer.parseInt(A);
      int b = Integer.parseInt(B);

      if (a == 0 && b == 0) break;

      if (a < 1 || a > 9999 || b < 1 || b > 9999)
        System.err.println("ERROR: Illegal operand");
      if (A.charAt(0) == '0' || B.charAt(0) == '0')
        System.err.println("ERROR: leading zero in operand");

      String product = Integer.toString(a*b);
      
      int latC = A.length();          // number of lattice columns
      int latR = B.length();          // number of lattice rows
      int rawC = 5 + 4*latC;          // raw number of displayed columns
      int rawR = 5 + 4*latR;          // raw number of displayed rows

      char[][] text = new char[rawR][rawC];

      for (int r=0; r < rawR; r++)
        for (int c=0; c < rawC; c++)
          text[r][c] = ' ';

      // create outer border
      text[0][0] = text[0][rawC-1] = text[rawR-1][0] = text[rawR-1][rawC-1] = '+';
      for (int r=1; r < rawR-1; r++)
        text[r][0] = text[r][rawC-1] = '|';
      for (int c=1; c < rawC-1; c++)
        text[0][c] = text[rawR-1][c] = '-';

      // copy operands
      for (int c=0; c < A.length(); c++)
        text[1][4+4*c] = A.charAt(c);
      for (int r=0; r < B.length(); r++)
        text[4+4*r][rawC-2] = B.charAt(r);

      // copy blank grid cells
      for (int r=0; r < latR; r++)
        for (int c=0; c < latC; c++)
          for (int i=0; i < 5; i++)
            for (int j=0; j < 5; j++)
              text[2+4*r+i][2+4*c+j] = grid[i][j];

      // fill grid cells
      for (int r=0; r < latR; r++)
        for (int c=0; c < latC; c++) {
          int mini = (A.charAt(c) - '0') * (B.charAt(r) - '0');
          text[3+4*r][3+4*c] = (char) (mini / 10 + '0');
          text[5+4*r][5+4*c] = (char) (mini % 10 + '0');
        }

      // place product
      String left = product.substring(0, product.length() - A.length());
      String bottom = product.substring(product.length() - A.length());
      for (int j=0; j < left.length(); j++) {
        int r = 5 + 4 * (B.length() - left.length() + j);
        text[r][1] = left.charAt(j);
        text[2+r][1] = '/';
      }
      for (int j=0; j < bottom.length(); j++) {
        int c = 3 + 4 * j;
        text[rawR-2][c] = bottom.charAt(j);
        if (j > 0 || left.length() > 0)
          text[rawR-2][c-2] = '/';
      }

      for (int r=0; r < rawR; r++) {
        for (int c=0; c < rawC; c++)
          System.out.print(text[r][c]);
        System.out.println();
      }
    }
  }
}
