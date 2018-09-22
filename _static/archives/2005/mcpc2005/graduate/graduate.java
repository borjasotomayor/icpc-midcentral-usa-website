// 2005 ACM Mid-Central Regional Programming Contest
// Problem E: Time to Graduate
// Posed by Wen Hsin, Park University
// Written by John Cigas, Rockhurst University
// Coded by Andy Harrington, Loyola University, Chicago

import java.io.*;
import java.util.*;

public class graduate {
  static List<String> courses;
  static boolean [][] offered; // [courses index][0: fall or 1:spring]
  static boolean [][] prereq; // [course index][course index of possible prereq]
  static int N;  // number of courses in this data set
  static int coursePerSem;  // maximum number of courses per semester

  public static void main(String[] args) {
    Scanner in = null;
    String file = (args.length > 0) ? args[0] : "graduate.in";
    try {
      in = new Scanner(new BufferedReader(  
                       new FileReader(file))); 
    } catch(Exception e) {
      System.out.println("Can't open input " + file);
      System.exit(-1);
    }
    
    N = in.nextInt();
    while (N > 0) {
      coursePerSem = in.nextInt();
      courses = new ArrayList<String>(N);
      offered = new boolean[N][2];
      prereq = new boolean[N][N];
      // list of course names
      for (int i=0; i<N; i++)
         courses.add(in.next());
     
      // individual course info
      for (int i=0; i<N; i++) {
         int thisCourseIndex = courses.indexOf(in.next());
         char semOffered = in.next().charAt(0);
    	 offered[thisCourseIndex][0] = semOffered == 'F' || semOffered == 'B';
         offered[thisCourseIndex][1] = semOffered == 'S' || semOffered == 'B';

         int numPrereq = in.nextInt();
         for (int j=0; j<numPrereq; j++) 
           prereq[thisCourseIndex][courses.indexOf(in.next())] = true;        
      }
      
      System.out.println(
         "The minimum number of semesters required to graduate is " + 
          findClasses(new ArrayList<Integer>(), 0) + ".");
      N = in.nextInt();
    }
  }   
  
  // return the number of semesters needed if numSemesters have elapsed
  //   and the specified courses have already been taken.
  static int findClasses(ArrayList<Integer> taken, int numSemesters) {
    if (taken.size() == N)
      return numSemesters;
    ArrayList<Integer> toAdd = new ArrayList<Integer>();
    for (int i=0; i<courses.size(); i++) 
      if (!taken.contains(i) && offered[i][numSemesters%2] &&
          havePrereqs (i, taken))
        toAdd.add(i);
    int minTime = 99999;
    ArrayList<ArrayList<Integer>> allComb = new ArrayList<ArrayList<Integer>>(); 
    combinations(new ArrayList<Integer>(), Math.min(coursePerSem, toAdd.size()), 
         0, toAdd, allComb); // Put all legal subsets of toAdd into allComb
    for (ArrayList<Integer> c : allComb) { // test branch for each such subset
      ArrayList<Integer> tempTaken = new ArrayList<Integer>(taken);
      tempTaken.addAll(c);
      int time = findClasses(tempTaken, numSemesters+1);
      if (time < minTime) 
         minTime = time;
    }
    return minTime;
  }

  static boolean havePrereqs(int course, ArrayList<Integer> taken) {
     for (int i=0; i<N; i++) 
        if (prereq[course][i] && !taken.contains(i))
           return false;
     return true;
  }

  // When base and allComb are empty and next is 0, this makes allComb become
  // all tot element subsets of allElem.  In intermediate cases, add to allComb 
  // all lists containing base (some elements of allElem with index < next), 
  // plus each tot element subset of allElem with index >= next.
  static void combinations(ArrayList<Integer> subsetBase, int tot, int next, 
              ArrayList<Integer> allElem, ArrayList<ArrayList<Integer>> allComb) {
    if (tot == 0) 
      allComb.add(subsetBase);
    else {
      if (tot < allElem.size() - next) // make combinations without element at 
        combinations(subsetBase, tot, next+1, allElem, allComb); //  next index
      ArrayList<Integer> basePlus = new ArrayList<Integer>(subsetBase);
      basePlus.add(allElem.get(next));   // make combinations with element at
      combinations(basePlus, tot-1, next+1, allElem, allComb);  // next index
    }
  }
} 
