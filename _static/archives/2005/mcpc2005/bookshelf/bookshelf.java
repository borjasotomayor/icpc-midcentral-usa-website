// 2005 ACM Mid-Central Regional Programming Contest
// Problem B: Bookshelf
// Posed by Kris Rudin, Eastern Washington University
// Coded by Tim Rolfe, Eastern Washington University
// Hacked by MidCentral Judges for input/output consistency

/**
 * Alice C. Mulligan's Bookshelf Problem.  Posed by Kris Rudin.
 *
 * Used as an excuse to exercise the Java API linked list and
 * iterator.
 *
 * @author  Tim Rolfe
 */
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Iterator;

public class bookshelf
{
   static private final boolean DEBUG =  false;

   public static void main ( String[] args ) throws Exception   // lazy handling
   {//Allow for command-line entry of the input file.
      String  filename = args.length > 0 ? args[0] : "bookshelf.in";
      Scanner input = new Scanner( new File(filename) );
      int     nProb, prob = 1;

      int width = input.nextInt();
      while (width > 0)
      {
         LinkedList<Book> shelf = new LinkedList<Book>();
         String wordIn;
         int inuse = 0;

         input.nextLine(); // Move down
         wordIn = input.next();
         while ( wordIn.charAt(0) != 'E' )
         {
            char event = wordIn.charAt(0);
            int  id    = input.nextInt(),
                 bkwd;
            Book item;
            Iterator<Book> iter;

            switch ( event )
            {
               case 'A':  
                          bkwd = input.nextInt();

                          // check for duplicates here
                          for ( Book book : shelf ) {
			     if (book.id == id)
                                System.err.println("OOPS - duplicate id = "+id);
			  }

                          inuse += bkwd;
                          while ( inuse  > width )
                          {
                             item = shelf.removeLast();
                             inuse -= item.width;
                             if ( DEBUG )
                                System.err.println ("Bumping " +
                                   item.id + " width " + inuse);
                          }
                          item = new Book(id, bkwd);
                          shelf.addFirst(item);
                          if ( DEBUG )
                             System.err.println ("Adding " +
                                item.id + " width " + inuse);
                          break;
               case 'R':  
                          iter = shelf.iterator();

                          while (iter.hasNext()) {
                             item = iter.next();
                             if (item.id == id) {
                                iter.remove();
                                inuse -= item.width;
                                if ( DEBUG )
                                   System.err.println ("Removing " +
                                   item.id + " width " + inuse);
                                break;
                             }

			  }
			  break;
            }
            input.nextLine();     // Move down from the nextInt() call
            wordIn = input.next();
         }
         System.out.print ("PROBLEM " + prob + ":");
         for ( Book book : shelf )
            System.out.print (" " + book.id);
         System.out.println();
         width = input.nextInt();
	 prob++;
      }
   }

   // Nested class:  outer class has full access to its private parts.
   private static class Book
   {
      int id, width;

      private Book ( int id, int width )
      {  this.id = id;  this.width = width;  }
   }
}
