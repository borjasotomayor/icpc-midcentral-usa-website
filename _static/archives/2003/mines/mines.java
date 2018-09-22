// 2003 ACM Mid-Central Regional Programming Contest
// Problem E: Data Mining?
// by John Cigas, Rockhurst University

/*
mines.java

Problem:  
   In the game of minesweeper, determine how many covered cells are left
when a casual player gets stuck. A casual player only applies two rules:

1) if number of flagged neighbors == number of mined neighbors
      Clear all other neighbors
2) if (flagged neighbors + covered neighbors) == number of mined neighbors
      Flag all covered neighbors
*/

import java.io.*;
import java.util.*;

class mines {
 
  public static void main(String[] arg) {
    String FILE = "mines";
    ACMIO in = new ACMIO(FILE + ".in");
    PrintWriter out = null;
    try {
      out = new PrintWriter(
              new BufferedWriter(
                new FileWriter(FILE + ".out")));
    } catch(Exception e) {
        System.out.println("can't open output");
    }

    int numRows = in.intRead(); int numCols = in.intReadln();
    while ( numRows > 0 && numCols > 0) {

      Board b = new Board(numRows, numCols);

      for (int i=0; i<numRows; i++) {
        String s = in.stringReadln();
      
        for (int j = 0; j < s.length(); j++) {
          b.putCell(new Cell(i,j,s.charAt(j)=='M'));
        }      
      }

      // b is now populated, update cell counts
      b.computeNeighborCounts();

      System.out.println(b.showBoard());

      int c = solveMany(b);

      out.println(c);
      System.out.println(c + " covered squares");
      System.out.println("--------------------------------------------");

      numRows = in.intRead(); numCols = in.intReadln();
    }
    out.close();
  }


  public static int solveMany (Board b) {

     Board saveBoard = (Board)b.clone();
     Board minBoard = null;
     Cell  minCell = null;

     int minCovered = b.getRows() * b.getCols();

     Set guessList = b.getUnclearedSet();
     while (!guessList.isEmpty()) {
        Iterator itr = guessList.iterator();
        Cell cc = (Cell)itr.next();
	itr.remove();

	b = (Board)saveBoard.clone();
        int res = b.solveFrom(cc.getRows(),cc.getCols());

        if (res >= 0 && res < minCovered) {
           minCell = cc;
           minCovered = res;
           minBoard = (Board)b.clone();
        }
        // we are ignoring -1, which is initial querry of a mine.
     }	

     System.out.println("Min at ("+minCell.getRows()+","+minCell.getCols()+")");
     System.out.println(minBoard.showCleared());
     return minCovered;

  }

}
class Board implements Cloneable {
   public Board (int rows, int cols)
   { 
       this.rows = rows; this.cols = cols; 
       myBoard = new Cell[rows][cols]; 
   }
   protected Object clone() {
     try {
       Board newB = (Board)super.clone();
       newB.myBoard = new Cell[rows][cols]; //better create a new one, or else!
       for (int i=0; i<rows; i++) {
         for (int j=0; j<cols; j++) {
           newB.myBoard[i][j] = (Cell)myBoard[i][j].clone();
         }
       }

       return newB;
     } catch (CloneNotSupportedException e) {
            // this shouldn't happen 
            throw new InternalError();
     }   
   }

   public void putCell (Cell c) { myBoard[c.getRows()][c.getCols()] = c; }

   public Set getUnclearedSet () {
      Set s = new HashSet();
      for (int i=0; i<rows; i++) {
        for (int j=0; j<cols; j++) {
           if (!myBoard[i][j].isCleared() && !myBoard[i][j].isFlagged()) 
              s.add(myBoard[i][j]);
        }   
      }
      return s;
   }

   public void computeNeighborCounts () {
      for (int i=0; i<rows; i++) {
        for (int j=0; j<cols; j++) {
           int count = 0;
              count += cellCount (i-1,j-1);
              count += cellCount (i-1,j);              
              count += cellCount (i-1,j+1);

              count += cellCount(i,j-1);              
              count += cellCount(i,j+1); 

              count += cellCount(i+1, j-1);
              count += cellCount(i+1, j);
              count += cellCount(i+1, j+1);
         myBoard[i][j].setNeighborMineCount(count);
        }   
       
      }
   }
   
   public List getNeighborList (Cell c) {
      
      int i = c.getRows(); int j = c.getCols();
      List l = new ArrayList();
      if (validCell (i-1,j-1)) l.add(myBoard[i-1][j-1]);
      if (validCell (i-1,j)) l.add(myBoard[i-1][j]);              
      if (validCell (i-1,j+1)) l.add(myBoard[i-1][j+1]);

      if (validCell(i,j-1)) l.add(myBoard[i][j-1]);              
      if (validCell(i,j+1)) l.add(myBoard[i][j+1]); 

      if (validCell(i+1, j-1)) l.add(myBoard[i+1][j-1]);
      if (validCell(i+1, j)) l.add(myBoard[i+1][j]);
      if (validCell(i+1, j+1)) l.add(myBoard[i+1][j+1]);
      return l;
   }


  public int solveFrom(int row, int col) {
  /*
  for each cell in the active list
    if flagged mine
      mark self as processed
      take out of active list (don't put in new active)
    
    else has mines as neighbors
        if # flagged neighbors == number of adjacent mines
           clear all unflagged neighbors
           add all cleared neighbors to active/new active list.
           clear self.
           mark self as processed.
           take out of active list (don't put in new active)
        else if # flagged neighbors > number of adjacent mines 
           ERROR - sanity check
        else if #of uncleared cells + # flagged cells == number of adjacent mines
             AND
             self isCleared
           flag all uncleared cells
           take out of active list (don't put in new active)
         
  if no changes
    return count
  else
    copy new active list to active list
    try again
  */

     if (myBoard[row][col].isMine())
        return -1; // guessed wrong - just stop now

     //ActiveCells are unflagged, uncleared, unmined
     Set activeCells = new HashSet();
     myBoard[row][col].clear();
     activeCells.add(myBoard[row][col]);

     
     boolean changed = false;
     while (!activeCells.isEmpty()) {
        changed = false;
        Iterator itr = activeCells.iterator();
        Set newActive = new HashSet();
        while (itr.hasNext()) {
           Cell c = (Cell)itr.next();

           if (c.isFlagged()) {
	      changed = true;
              c.process();
            
           }

           // Check if all surrounding mines have been flagged
           //   then clear any remaining cells
           else if ((flagCount(c) == c.getNeighborMineCount())) {

              c.clear(); 
	      c.process();
              changed = true;
                
              List l2 = getNeighborList(c);
              Iterator xtr = l2.iterator();

              while (xtr.hasNext()) {
                 Cell cc = (Cell)xtr.next();
                 if (!cc.isProcessed() && !cc.isFlagged() ) {
                    cc.clear();
                    newActive.add(cc); 
                 }
              }
           }

	   // sanity check
           else if ((flagCount(c) > c.getNeighborMineCount())) {
	      System.out.println("OUCH. More flags than mines");
              System.out.println(showCleared());
	      return -1000;
	   }
               
           // Check if all remaining uncleared cells are mines, flag if so
           else if (c.isCleared() && 
	            (unprocessedCount(c) == c.getNeighborMineCount())){
             changed = true;
             List l2 = getNeighborList(c);
             Iterator xtr = l2.iterator();
             while (xtr.hasNext()) {
                Cell cc = (Cell)xtr.next();
                if (!(cc.isCleared() || cc.isFlagged() ))
                   cc.flag();
             }

           }
	   else
	      newActive.add(c);  // put it back
        }

        if (!changed) { // stuck
           return (numCovered());
        }
	else
	   activeCells = newActive;
     }

     return numCovered();

  }

  // numCovered returns the number of covered, but unmined cells 
  public int numCovered () {
     int nc = 0;
     for (int i=0; i<rows; i++) {
        for (int j=0; j<cols; j++) {
           if(myBoard[i][j].isCleared() == myBoard[i][j].isMine()) 
                nc++;

           // Sanity check
           if(myBoard[i][j].isCleared() && myBoard[i][j].isMine())
                System.out.println("OUCH - Cleared a mine");
        }
     }
     return nc;
   }

   public String showBoard (){
      // Create a string representation of the board
      String s = "";
      for (int i=0; i<rows; i++) {
         for (int j=0; j<cols; j++) {
            if (myBoard[i][j].isMine())
               s = s+"M ";
            else
               s = s+myBoard[i][j].getNeighborMineCount()+" ";
         }
         s = s + "\n";
      }
      return s;
   }

   public String showCleared (){
      // Create a string representation of the board
      String s = "";
      for (int i=0; i<rows; i++) {
         for (int j=0; j<cols; j++) {
            if (!myBoard[i][j].isCleared())
	       if (myBoard[i][j].isFlagged())
                  s = s+"F ";
	       else
                  s = s+"? ";
            else
               s = s+myBoard[i][j].getNeighborMineCount()+" ";
         }
         s = s + "\n";
      }
      return s;
   }
   private boolean validCell (int x, int y) {
      return (x >= 0 && x < rows && y >=0 && y < cols );
   }

   private int cellCount(int x, int y) {
      if (validCell (x,y))
         return myBoard[x][y].isMine()?1:0;
      else
         return 0;
   }
    public int unprocessedCount(Cell cin) {
       int count = 0;
       Iterator itr = getNeighborList(cin).iterator();
       while (itr.hasNext()) {
          Cell c = (Cell)itr.next();
	  // can I get away with only !isCleared()???
          if (!c.isProcessed() && !c.isCleared()) count++;
   
       }
       return count;
    }
    public int flagCount(Cell cin) {
       int count = 0;
       Iterator itr = getNeighborList(cin).iterator();
       while (itr.hasNext()) {
          Cell c = (Cell)itr.next();
          if (c.isFlagged()) count++;
       }
       return count;
    }

   private Cell [][] myBoard;
   private int rows, cols;
   public int getRows() { return rows;}
   public int getCols() { return cols;}
}

class Cell implements Cloneable {
  
    protected Object clone () {
       try {
       Cell c = (Cell)super.clone();
       return c;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen 
            throw new InternalError();
        }   
    }
    public Cell(int row, int col, boolean mine) {this.row = row; this.col = col; this.mine = mine;}
    public Cell() { this(0,0,false);} // not exactly correct
    public void setNeighborMineCount (int count) { numNeighborMines = count; }
    public int getNeighborMineCount () { return numNeighborMines; }
    public boolean isMine() {return mine;}
    public boolean isFlagged() { return flagged;}
    public boolean isCleared() { return cleared;}
    public boolean isProcessed() { return processed; }



    public void flag() { 
     if (cleared) System.out.println("OUCH - flagging cleared mine - "+toString());
    flagged = true; }
    public void clear() {
     if (flagged) System.out.println("OUCH - clearing flagged mine - "+toString());
    cleared = true; }
    public void process() { processed = true; }
   

    boolean mine;
    boolean flagged;
    boolean cleared;
    boolean processed;  // true -> never look at again for any reason. All surrounding cells are cleared or flagged.
    int numNeighborMines;
    int row, col;
    public int getRows () {return row;}
    public int getCols() {return col;}
    public String toString() { return "Row = "+row+"  Col = "+col;}
    public boolean equals(Object o) { Cell c= (Cell)o; return (c.row == row && c.col == col);}
    public int hashcode() { return row*1000+col; }

}
